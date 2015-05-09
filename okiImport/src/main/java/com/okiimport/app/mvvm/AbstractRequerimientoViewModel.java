package com.okiimport.app.mvvm;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Spinner;

public abstract class AbstractRequerimientoViewModel extends AbstractViewModel {

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

}
