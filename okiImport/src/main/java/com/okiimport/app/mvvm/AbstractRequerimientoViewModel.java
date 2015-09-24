package com.okiimport.app.mvvm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.mail.MailService;
import com.okiimport.app.modelo.Ciudad;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.Estado;

import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.enumerados.EEstatusRequerimiento;
//Constraint
import com.okiimport.app.mvvm.constraint.AnnoConstraint;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
import com.okiimport.app.mvvm.constraint.RegExpressionConstraint;
import com.okiimport.app.mvvm.constraint.CustomConstraint.EConstraint;
import com.okiimport.app.mvvm.constraint.RegExpressionConstraint.RegExpression;
import com.okiimport.app.mvvm.constraint.GeneralConstraint;
import com.okiimport.app.mvvm.constraint.MayorCantidadConstraint;

public abstract class AbstractRequerimientoViewModel extends AbstractViewModel {

	private static final String RUTA_MESSAGEBOX = "/WEB-INF/views/sistema/configuracion/messagebox.zul";

	// Servicios
	@BeanInjector("mailService")
	protected MailService mailService;

	@BeanInjector("sMaestros")
	protected SMaestros sMaestros;

	// Atributos
	protected Calendar calendar = GregorianCalendar.getInstance();

	protected List<Ciudad> listaCiudades;

	protected Estado estado;

	protected int pageSize = 10;

	/** SETTERS Y GETTERS */
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/** COMMADN */
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
	public List<Estado> llenarListaEstados() {
		return (List<Estado>) sMaestros.ConsultarEstado(0, -1).get("estados");
	}

	@Command
	@SuppressWarnings("unchecked")
	@NotifyChange({ "listaCiudades" })
	public void buscarCiudades() {
		System.out.println("this.estado == null");
		if (this.estado != null)
			listaCiudades = (List<Ciudad>) sMaestros.ConsultarCiudad(
					estado.getIdEstado(), 0, -1).get("ciudades");
	}

	@Command
	public void ampliarImagen(
			@Default("Titulo") @BindingParam("titulo") String titulo,
			@BindingParam("imagen") String imagen) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("title", titulo);
		parametros.put("image", imagen);
		crearModal("/WEB-INF/views/sistema/configuracion/ampliarImagen.zul",
				parametros);
	}

	/** METODOS SOBREESCRITOS */
	@Override
	@SuppressWarnings("rawtypes")
	protected void mostrarMensaje(String titulo, String mensaje, String icon,
			Button[] botones, EventListener clickEvent,
			Map<String, String> params) {
		Messagebox.setTemplate(RUTA_MESSAGEBOX);
		super.mostrarMensaje(titulo, mensaje, icon, botones, clickEvent, params);
	}

	/** METODOS PROPIOS DE LA CLASE */

	protected static List<ModeloCombo<String>> llenarListaBancoPago() {
		List<ModeloCombo<String>> listaBancoPago = new ArrayList<ModeloCombo<String>>();
		listaBancoPago
				.add(new ModeloCombo<String>("Banco Exterior", "EXTERIOR"));
		listaBancoPago.add(new ModeloCombo<String>("Banco Provincial",
				"PROVINCIAL"));
		listaBancoPago.add(new ModeloCombo<String>("Banco Banesco", "BANESCO"));
		listaBancoPago.add(new ModeloCombo<String>("Banco de Venezuela",
				"BDVENEZUELA"));
		listaBancoPago.add(new ModeloCombo<String>("Banco Mercantil",
				"MERCANTIL"));
		listaBancoPago.add(new ModeloCombo<String>("Banco BOD", "BOD"));
		return listaBancoPago;
	}

	protected static List<ModeloCombo<String>> llenarListaEmpresaEncomiendas() {
		List<ModeloCombo<String>> listaEmpresaEncomiendas = new ArrayList<ModeloCombo<String>>();
		listaEmpresaEncomiendas.add(new ModeloCombo<String>("Zoom", "ZOOM"));
		listaEmpresaEncomiendas
				.add(new ModeloCombo<String>("Domesa", "DOMESA"));
		listaEmpresaEncomiendas.add(new ModeloCombo<String>("Mrw", "MRW"));
		return listaEmpresaEncomiendas;
	}

	protected static List<ModeloCombo<Boolean>> llenarListaOficinaDireccion() {
		List<ModeloCombo<Boolean>> listaOficinaDireccion = new ArrayList<ModeloCombo<Boolean>>();
		listaOficinaDireccion.add(new ModeloCombo<Boolean>(
				"Oficina Empresa Encomiendas", true));
		listaOficinaDireccion.add(new ModeloCombo<Boolean>(
				"Direccion Particular", false));
		return listaOficinaDireccion;
	}

	protected static List<ModeloCombo<Boolean>> llenarListaTraccion() {
		List<ModeloCombo<Boolean>> listaTraccion = new ArrayList<ModeloCombo<Boolean>>();
		listaTraccion.add(new ModeloCombo<Boolean>("4x2", true));
		listaTraccion.add(new ModeloCombo<Boolean>("4x4", false));
		return listaTraccion;
	}

	protected static List<ModeloCombo<Boolean>> llenarListaTransmision() {
		List<ModeloCombo<Boolean>> listaTransmision = new ArrayList<ModeloCombo<Boolean>>();
		listaTransmision.add(new ModeloCombo<Boolean>("Automatico", true));
		listaTransmision.add(new ModeloCombo<Boolean>("Sincronico", false));
		return listaTransmision;
	}

	protected static List<ModeloCombo<Boolean>> llenarListaTipoPersona() {
		List<ModeloCombo<Boolean>> listaTipoPersona = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoPersona.add(new ModeloCombo<Boolean>("J", true));
		listaTipoPersona.add(new ModeloCombo<Boolean>("V", false));
		return listaTipoPersona;
	}

	protected static List<ModeloCombo<Boolean>> llenarListaTipoRepuesto() {
		List<ModeloCombo<Boolean>> listaTipoRepuesto = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoRepuesto.add(new ModeloCombo<Boolean>("Indistinto", null));
		listaTipoRepuesto.add(new ModeloCombo<Boolean>("Reemplazo", true));
		listaTipoRepuesto.add(new ModeloCombo<Boolean>("Original", false));
		return listaTipoRepuesto;
	}

	protected static List<ModeloCombo<Boolean>> llenarListaTipoProveedor() {
		List<ModeloCombo<Boolean>> listaTipoProveedor = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoProveedor.add(new ModeloCombo<Boolean>("Internacional", false));
		listaTipoProveedor
				.add(new ModeloCombo<Boolean>("Nacional", true ));
		return listaTipoProveedor;
	}

	private static List<ModeloCombo<String>> llenarListaEstatus(
			List<EEstatusRequerimiento> listEstatus) {
		List<ModeloCombo<String>> listaEstatus = new ArrayList<ModeloCombo<String>>();
		for (EEstatusRequerimiento estatus : listEstatus)
			listaEstatus.add(new ModeloCombo<String>(estatus.getNombre(),
					estatus.getValue()));
		return listaEstatus;
	}

	protected static List<ModeloCombo<String>> llenarListaEstatusEmitidos() {
		return llenarListaEstatus(EEstatusRequerimiento.getEstatusEmitidos());
	}

	protected static List<ModeloCombo<String>> llenarListaEstatusProcesados() {
		return llenarListaEstatus(EEstatusRequerimiento.getEstatusProcesados());
	}

	protected static List<ModeloCombo<String>> llenarListaEstatusOfertados() {
		return llenarListaEstatus(EEstatusRequerimiento.getEstatusOfertados());
	}

	protected static List<ModeloCombo<String>> llenarListaEstatusGeneral() {
		return llenarListaEstatus(EEstatusRequerimiento.getEstatusGeneral());
	}

	protected static List<ModeloCombo<Boolean>> llenarTiposFleteNacional() {
		List<ModeloCombo<Boolean>> listaTiposFlete = new ArrayList<ModeloCombo<Boolean>>();
		listaTiposFlete.add(new ModeloCombo<Boolean>(
				"Incluido en el Precio de Venta", false));
		listaTiposFlete.add(new ModeloCombo<Boolean>(
				"No Incluido en el Precio de Venta", true));
		return listaTiposFlete;
	}

	protected static List<ModeloCombo<Boolean>> llenarTiposFleteInternacional() {
		List<ModeloCombo<Boolean>> listaTiposFlete = new ArrayList<ModeloCombo<Boolean>>();
		listaTiposFlete.add(new ModeloCombo<Boolean>("CIF", true));
		listaTiposFlete.add(new ModeloCombo<Boolean>("FOB", false));
		return listaTiposFlete;
	}

	protected static List<ModeloCombo<Boolean>> llenarFormasDeEnvio() {
		List<ModeloCombo<Boolean>> listaFormasEnvio = new ArrayList<ModeloCombo<Boolean>>();
		listaFormasEnvio.add(new ModeloCombo<Boolean>("Aéreo", true));
		listaFormasEnvio.add(new ModeloCombo<Boolean>("Maritimo", false));
		return listaFormasEnvio;
	}

	protected String buscarUsername(Persona persona,
			SControlUsuario sControlUsuario) {
		boolean noValido = true;
		String usuario = persona.getNombre().split(" ")[0].toLowerCase();
		String username = usuario;
		while (noValido) {
			noValido = sControlUsuario.verificarUsername(username);
			if (noValido)
				username = usuario
						+ PasswordGenerator.getPassword(
								PasswordGenerator.NUMEROS
										+ PasswordGenerator.MAYUSCULAS, 3);
		}
		return username;
	}

	public int getYearDay() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/*public int getFecha() {
		return Calendar.getInstance().getTime();
	}
	*/
	public CustomConstraint getNotEmptyValidator() {
		return new GeneralConstraint(EConstraint.NO_EMPTY);
	}

	public CustomConstraint getEmailValidator() {
		RegExpression[] constrains = new RegExpression[] { new RegExpression(
				"/.+@.+\\.[a-z]+/",
				"Debe contener un correo valido Ej. fusa@gmail.com") };
		return new RegExpressionConstraint(constrains, EConstraint.NO_EMPTY,
				EConstraint.CUSTOM);
	}

	public CustomConstraint getValidatorCantidad(
			@BindingParam("cantidadRequerida") Long cantidadRequerida) {
		return new MayorCantidadConstraint(cantidadRequerida);
	}

	public CustomConstraint getValidatorAnno(
			@BindingParam("minYear") Integer minYear,
			@BindingParam("maxYear") Integer maxYear) {
		return new AnnoConstraint(minYear, maxYear);
	}

	public CustomConstraint getTelefonoValidator() {
		RegExpression[] constrains = new RegExpression[] { new RegExpression(
				"/.[0-9]+/",
				"Debe Contener Un Numero Telefonico Valido Ej. 025141785289") };
		return new RegExpressionConstraint(constrains, EConstraint.NO_EMPTY,
				EConstraint.CUSTOM);

	}

	public CustomConstraint getValidatorClienteCedulaRif() {
		return new GeneralConstraint(EConstraint.NO_EMPTY,
				EConstraint.NO_NEGATIVE, EConstraint.NO_ZERO);
	}

	public CustomConstraint getCantValidator() {
		RegExpression[] constrains = new RegExpression[] { new RegExpression(
				"/.[0-9]+/", "Debe Contener Un Numero Valido") };
		return new RegExpressionConstraint(constrains, EConstraint.NO_EMPTY,
				EConstraint.NO_NEGATIVE, EConstraint.NO_ZERO,
				EConstraint.CUSTOM);

	}
	
	
	
	public CustomConstraint getValidatorClienteCedulaRif2() {
        
        RegExpression[] constrains = new RegExpression[] { new RegExpression(
		"/.[0-9]+/",
		"Introduzca RIF o Cedula solo Números sin guiones Ej.: 402405374") };
        return new RegExpressionConstraint(constrains, EConstraint.NO_EMPTY, EConstraint.NO_NEGATIVE,EConstraint.NO_ZERO);
     }
	
	public CustomConstraint getValidatorPrecio() {
		
		RegExpression[] constrains = new RegExpression[] { new RegExpression(
				"/.[0-9]+/", "Debe Contener Valores Numericos Validos") };
		return new RegExpressionConstraint(constrains, EConstraint.NO_EMPTY,
				EConstraint.NO_NEGATIVE, EConstraint.NO_ZERO);
	}
	
	
    public CustomConstraint getValidatorFechaVencimiento() {
		
    	return new GeneralConstraint(EConstraint.NO_EMPTY,
				EConstraint.NO_PAST );
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
