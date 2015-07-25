package com.okiimport.app.mvvm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.mail.MailService;
import com.okiimport.app.modelo.Ciudad;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.Estado;
import com.okiimport.app.mvvm.constraint.AnnoConstraint;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
import com.okiimport.app.mvvm.constraint.RegExpressionConstraint;
import com.okiimport.app.mvvm.constraint.CustomConstraint.EConstraint;
import com.okiimport.app.mvvm.constraint.RegExpressionConstraint.RegExpression;
import com.okiimport.app.mvvm.constraint.GeneralConstraint;
import com.okiimport.app.mvvm.constraint.MayorCantidadConstraint;

public abstract class AbstractRequerimientoViewModel extends AbstractViewModel {
	
	private static final String RUTA_MESSAGEBOX = "/WEB-INF/views/sistema/configuracion/messagebox.zul";
	
	//Servicios
	@BeanInjector("mailService")
	protected MailService mailService;
	
	@BeanInjector("sMaestros")
	protected SMaestros sMaestros;
	
	protected List<Ciudad> listaCiudades;

	protected Estado estado;
	
	protected int pageSize = 10;
	
	/**SETTERS Y GETTERS*/	
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	/**COMMADN*/
	@Command
	@NotifyChange("*")
	public void cambiarFoto(@BindingParam("media") Media media,
			@BindingParam("detalle") DetalleRequerimiento detalle) {
		if (media instanceof org.zkoss.image.Image)
			detalle.setFoto(((org.zkoss.image.Image) media).getByteData());
		else if (media != null)
			mostrarMensaje("Error", "No es una imagen: " + media, null, null,
					null, null);
	}
	
	@Command
	@SuppressWarnings("unchecked")
	public List<Estado> llenarListaEstados(){
		return  (List<Estado>) sMaestros.ConsultarEstado(0, -1).get("estados");
	}
	
	@Command
	@SuppressWarnings("unchecked")
	@NotifyChange({ "listaCiudades" })
	public void buscarCiudades(){
		if (this.estado != null)
			listaCiudades = (List<Ciudad>) sMaestros.ConsultarCiudad(estado.getIdEstado(), 0, -1).get("ciudades");
	}
	
	@Command
	public void ampliarImagen(@Default("Titulo") @BindingParam("titulo") String titulo,
			@BindingParam("imagen") String imagen){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("title", titulo);
		parametros.put("image", imagen);
		crearModal("/WEB-INF/views/sistema/configuracion/ampliarImagen.zul", parametros);
	}

	/**METODOS SOBREESCRITOS*/
	@Override
	@SuppressWarnings("rawtypes")
	protected void mostrarMensaje(String titulo, String mensaje, String icon, Button[] botones, 
			EventListener clickEvent, Map<String, String> params) {
		Messagebox.setTemplate(RUTA_MESSAGEBOX);
		super.mostrarMensaje(titulo, mensaje, icon, botones, clickEvent, params);
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	
	protected static List<ModeloCombo<String>> llenarListaBancoPago(){
		List<ModeloCombo<String>> listaBancoPago = new ArrayList<ModeloCombo<String>>();
		listaBancoPago .add(new ModeloCombo<String>("Banco Exterior", "EXTERIOR"));
		listaBancoPago .add(new ModeloCombo<String>("Banco Provincial", "PROVINCIAL"));
		listaBancoPago .add(new ModeloCombo<String>("Banco Banesco", "BANESCO"));
		listaBancoPago .add(new ModeloCombo<String>("Banco de Venezuela", "BDVENEZUELA"));
		listaBancoPago .add(new ModeloCombo<String>("Banco Mercantil", "MERCANTIL"));
		listaBancoPago .add(new ModeloCombo<String>("Banco BOD", "BOD"));
		return listaBancoPago;
	}
	
	protected static List<ModeloCombo<String>> llenarListaEmpresaEncomiendas(){
		List<ModeloCombo<String>> listaEmpresaEncomiendas = new ArrayList<ModeloCombo<String>>();
		listaEmpresaEncomiendas .add(new ModeloCombo<String>("Zoom", "ZOOM"));
		listaEmpresaEncomiendas .add(new ModeloCombo<String>("Domesa", "DOMESA"));
		listaEmpresaEncomiendas .add(new ModeloCombo<String>("Mrw", "MRW"));
		return listaEmpresaEncomiendas;
	}
	
	protected static List <ModeloCombo<Boolean>> llenarListaOficinaDireccion(){
		List <ModeloCombo<Boolean>> listaOficinaDireccion = new ArrayList<ModeloCombo<Boolean>>();
		listaOficinaDireccion.add(new ModeloCombo<Boolean>("Oficina Empresa Encomiendas", true));
		listaOficinaDireccion.add(new ModeloCombo<Boolean>("Direccion Particular", false));
		return listaOficinaDireccion;
	}
	
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
	
	protected static List <ModeloCombo<Boolean>> llenarListaTipoRepuesto(){
		List <ModeloCombo<Boolean>> listaTipoRepuesto = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoRepuesto.add(new ModeloCombo<Boolean>("Indistinto", null));
		listaTipoRepuesto.add(new ModeloCombo<Boolean>("Reemplazo", true));
		listaTipoRepuesto.add(new ModeloCombo<Boolean>("Original", false));
		return listaTipoRepuesto;
	}
	
	protected static List <ModeloCombo<Boolean>> llenarListaTipoProveedor(){
		List <ModeloCombo<Boolean>> listaTipoProveedor = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoProveedor.add(new ModeloCombo<Boolean>("Nacional", true));
		listaTipoProveedor.add(new ModeloCombo<Boolean>("Internacional", false));
		return listaTipoProveedor;
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
	
	protected static List<ModeloCombo<Boolean>> llenarTiposFleteInternacional(){
		List<ModeloCombo<Boolean>> listaTiposFlete = new ArrayList<ModeloCombo<Boolean>>();
		listaTiposFlete.add(new ModeloCombo<Boolean>("CIF", true));
		listaTiposFlete.add(new ModeloCombo<Boolean>("FOB", false));
		return listaTiposFlete;
	}
	
	protected static List<ModeloCombo<Boolean>> llenarFormasDeEnvio(){
		List<ModeloCombo<Boolean>> listaFormasEnvio = new ArrayList<ModeloCombo<Boolean>>();
		listaFormasEnvio.add(new ModeloCombo<Boolean>("Aéreo", true));
		listaFormasEnvio.add(new ModeloCombo<Boolean>("Maritimo", false));
		return listaFormasEnvio;
	}
	
	public int getYearDay(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public CustomConstraint getNotEmptyValidator(){
		return new GeneralConstraint(EConstraint.NO_EMPTY);
	}
	
	public CustomConstraint getEmailValidator(){
		RegExpression[] constrains = new RegExpression[]{
				new RegExpression("/.+@.+\\.[a-z]+/", "Debe contener un correo valido Ej. fusa@gmail.com")
		};
		return new RegExpressionConstraint(constrains, EConstraint.NO_EMPTY);
	}
	
	public CustomConstraint getValidatorCantidad(@BindingParam("cantidadRequerida") Long cantidadRequerida){
		return new MayorCantidadConstraint(cantidadRequerida);
	}

	public CustomConstraint getValidatorAnno(@BindingParam("minYear") Integer minYear, 
			@BindingParam("maxYear") Integer maxYear){
		return new AnnoConstraint(minYear, maxYear);
	}

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
