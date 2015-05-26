package com.okiimport.app.mvvm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Spinner;

import com.okiimport.app.mail.MailService;

public abstract class AbstractRequerimientoViewModel extends AbstractViewModel {
	
	@Autowired
	@BeanInjector("mailService")
	protected MailService mailService;
	
	private static final String RUTA_MESSAGEBOX = "/WEB-INF/views/sistema/configuracion/messagebox.zul";
	
	/**SETTERS Y GETTERS*/	
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**METODOS SOBREESCRITOS*/
	@Override
	protected void mostrarMensaje(String titulo, String mensaje, String icon, Button[] botones, 
			EventListener<ClickEvent> clickEvent, Map<String, String> params) {
		// TODO Auto-generated method stub
		Messagebox.setTemplate(RUTA_MESSAGEBOX);
		super.mostrarMensaje(titulo, mensaje, icon, botones, clickEvent, params);
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	protected static List <ModeloCombo<Boolean>> llenarListaTraccion(){
		List <ModeloCombo<Boolean>> listaTraccion = new ArrayList<ModeloCombo<Boolean>>();
		listaTraccion.add(new ModeloCombo<Boolean>("4x2", true));
		listaTraccion.add(new ModeloCombo<Boolean>("4x4", false));
		return listaTraccion;
	}
	
	protected static List <ModeloCombo<Boolean>> llenarListaTransmision(){
		List <ModeloCombo<Boolean>> listaTransmision = new ArrayList<ModeloCombo<Boolean>>();
		listaTransmision.add(new ModeloCombo<Boolean>("Automatico", true));
		listaTransmision.add(new ModeloCombo<Boolean>("Sincronico", false));
		return listaTransmision;
	}
	
	protected static List <ModeloCombo<Boolean>> llenarListaTipoPersona(){
		List <ModeloCombo<Boolean>> listaTipoPersona = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoPersona.add(new ModeloCombo<Boolean>("J", true));
		listaTipoPersona.add(new ModeloCombo<Boolean>("V", false));
		return listaTipoPersona;
	}
	
	protected static List<ModeloCombo<String>> llenarListaEstatus(){
		List<ModeloCombo<String>> listaEstatus = new ArrayList<ModeloCombo<String>>();
		listaEstatus.add(new ModeloCombo<String>("Emitido", "CR"));
		listaEstatus.add(new ModeloCombo<String>("Recibido y Editado", "E"));
		listaEstatus.add(new ModeloCombo<String>("Enviado a Proveedores", "EP"));
		listaEstatus.add(new ModeloCombo<String>("Con Cotizaciones Asignadas", "CT"));
		listaEstatus.add(new ModeloCombo<String>("Ofertado", "O"));
		listaEstatus.add(new ModeloCombo<String>("Concretado", "CC"));
		return listaEstatus;
	}
	
	protected static List<ModeloCombo<Boolean>> llenarTiposFleteNacional(){
		List<ModeloCombo<Boolean>> listaTiposFlete = new ArrayList<ModeloCombo<Boolean>>();
		listaTiposFlete.add(new ModeloCombo<Boolean>("Incluido en el Precio de Venta", false));
		listaTiposFlete.add(new ModeloCombo<Boolean>("No Incluido en el Precio de Venta", true));
		return listaTiposFlete;
	}
	
	public int getYearDay(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public AbstractValidator getValidatorCantidad(){
		return new AbstractValidator(){

			@Override
			public void validate(ValidationContext ctx) {
				// TODO Auto-generated method stub
				Integer cantidadOfrecida = (Integer) ctx.getProperty().getValue();
				Spinner spnCantidad = (Spinner) ctx.getBindContext().getValidatorArg("spnCantidad");
				Long cantidadRequerida = (Long) ctx.getBindContext().getValidatorArg("cantidad");
				
				if(spnCantidad==null)
					System.out.println("***Error en la Validacion***");
				else if(cantidadOfrecida!=null && cantidadRequerida!=null){
					if(cantidadOfrecida > cantidadRequerida){
						String mensaje = "La cantidad ofrecida no puede ser mayor que "+cantidadRequerida+" !";
						mostrarNotification(mensaje, "error", 5000, true, spnCantidad);
						addInvalidMessage(ctx, mensaje);
					}
				}
			}
			
		};
	}

	public AbstractValidator getValidatorAnno(){
		return new AbstractValidator() {
			
			@Override
			public void validate(ValidationContext ctx) {
				// TODO Auto-generated method stub
				Integer anno = (Integer) ctx.getProperty().getValue();
				Integer minYear = (Integer) ctx.getBindContext().getValidatorArg("minValue");
				Integer maxYear = (Integer) ctx.getBindContext().getValidatorArg("maxValue");
				Intbox intbAnno = (Intbox) ctx.getBindContext().getValidatorArg("intbAnno");
				
				if(intbAnno==null)
					System.out.println("***Error en la Validacion***");
				else if(minYear!=null && maxYear!=null){
					if(minYear > anno || anno > maxYear){
						String mensaje = "El Año ingresado es Invalido !";
						mostrarNotification(mensaje, "error", 5000, true, intbAnno);
						addInvalidMessage(ctx, mensaje);
					}
				}
				else if(minYear!=null){
					if(minYear > anno){
						String mensaje = "El Año ingresado "+anno+" debe ser mayor que "+maxYear+"!";
						mostrarNotification(mensaje, "error", 5000, true, intbAnno);
						addInvalidMessage(ctx, mensaje);
					}
				}
				else if(maxYear!=null){
					if(anno > maxYear){
						String mensaje = "El Año ingresado "+anno+" debe ser menor que "+maxYear+"!";
						mostrarNotification(mensaje, "error", 5000, true, intbAnno);
						addInvalidMessage(ctx, mensaje);
					}
				}
			}
		};
	}
}
