package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;

import com.okiimport.app.model.Cliente;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Estado;
import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.model.Motor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.carga_masiva.PDDetalleRequerimientoEstrategy;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.mail.MailCliente;
import com.okiimport.app.service.transaccion.STransaccion;
import com.okiimport.app.service.web.SLocalizacion;

public class RegistrarRequerimientoViewModel extends AbstractCargaMasivaViewModel implements EventListener<SortEvent> {

	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	@BeanInjector("mailCliente")
	private MailCliente mailCliente;
	@BeanInjector("sLocalizacion")
	private SLocalizacion sLocalizacion;
	
	// GUI
	@Wire("#cedulaRif")
	public Intbox cedulaRif;
	
	@Wire("#annoV")
	private Datebox annoV;
	
	@Wire("#comboTipoPersona")
	private Combobox comboTipoPersona;
	
	@Wire("#gridMotores")
	private Listbox gridMotores;
	
	@Wire("#pagMotores")
	private Paging pagMotores;

	//Atributos
	private List<DetalleRequerimiento> eliminarDetalle;
	private List<MarcaVehiculo> listaMarcasVehiculo;
	private List<Motor> listaMotor;
	private List<Estado> listaEstados;
	
	private List<ModeloCombo<Boolean>> listaTraccion;
	private List<ModeloCombo<Boolean>> listaTransmision;
	private List<ModeloCombo<Boolean>> listaTipoPersona;
	private List<ModeloCombo<Boolean>> listaTipoRepuesto;
	private ModeloCombo<Boolean> traccion;
	private ModeloCombo<Boolean> transmision;
	private ModeloCombo<Boolean> tipoPersona;
	private ModeloCombo<Boolean> tipoRepuesto;
	private Requerimiento requerimiento;
	private Cliente cliente;
	private Motor motor;
	protected static final String BaseURL = "/WEB-INF/views/portal/";

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: formularioRequerimiento.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		super.doAfterCompose(view);
		limpiar();
		agregarGridSort(gridMotores);
		pagMotores.setPageSize(pageSize=3);
		listaMarcasVehiculo = (List<MarcaVehiculo>) sMaestros.consultarMarcas(0, -1).get("marcas");
		listaEstados = llenarListaEstados();
		listaTraccion = llenarListaTraccion();
		listaTransmision = llenarListaTransmision();
		listaTipoPersona = llenarListaTipoPersona();
		tipoPersona = listaTipoPersona.get(1);
		listaTipoRepuesto = llenarListaTipoRepuesto();
		cambiarMotores(0, null, null);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	public void onEvent(SortEvent event) throws Exception {
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarMotores", parametros );
		}
	}
	
	/**GLOBAL COMMAND*/
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaMotor")
	public void cambiarMotores(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sMaestros.consultarMotores(motor, fieldSort, sortDirection, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaMotor = (List<Motor>) parametros.get("motores");
		pagMotores.setActivePage(page);
		pagMotores.setTotalSize(total);
	}
	
	@GlobalCommand
	@NotifyChange("limpiarCamposReq")
	public void limpiarCamposRequerimiento(){
		//System.out.println("entre al LIMPIAR");
		recargar();
//		motor = new Motor();
//		requerimiento = new Requerimiento();
//		cliente = new Cliente();
//		requerimiento.setCliente(cliente);
//		super.cleanConstraintForm();
		
		
		//limpiar();
	}
	
	
	/**COMMAND*/
	/**
	 * Descripcion: Permite limpiar los campos del formulario registrar Requerimiento
	 * Parametros: @param view: formularioRequerimiento.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "requerimiento", "cliente", "estado", "transmision", "traccion", "tipoRepuesto" })
	public void limpiar() {
		try{
			motor = new Motor();
			requerimiento = new Requerimiento();
			cliente = new Cliente();
			requerimiento.setCliente(cliente);
			this.estado=new Estado();
			this.transmision=new ModeloCombo<Boolean>();
			this.traccion=new ModeloCombo<Boolean>();
			tipoRepuesto=new ModeloCombo<Boolean>();
			super.cleanConstraintForm();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

	 /**
		 * Descripcion: Permite Registrar el requerimiento
		 * Parametros: @param view: formularioRequerimiento.zul  
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
	@Command
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar) {
		try{
		if (checkIsFormValid()) {
			if (requerimiento.getDetalleRequerimientos().size() > 0) {
				btnEnviar.setDisabled(true);
				btnLimpiar.setDisabled(true);
				String tipo = (this.tipoPersona.getValor()) ? "J" : "V";
				cliente.setCedula(tipo + cliente.getCedula());
				cliente = sMaestros.registrarOActualizarCliente(cliente);
				requerimiento.setCliente(cliente);
				if (traccion != null)
					requerimiento.setTraccionV(traccion.getValor());
				if (transmision != null)
					requerimiento.setTransmisionV(transmision.getValor());
				if (tipoRepuesto != null)
					requerimiento.setTipoRepuesto(tipoRepuesto.getValor());

				sTransaccion.registrarRequerimiento(requerimiento, true, sMaestros);

				// El Objecto que se envia debe declararse final, esto quiere
				// decir que no puede instanciarse sino solo una vez
				
				mailCliente.registrarRequerimiento(requerimiento, mailService);
				
				Map<String, Object> parametros = new HashMap<String, Object>();
				crearModal(BaseURL+"avisoRequerimientoRegistrado.zul", parametros);
//				mostrarMensaje("Informaci\u00F3n",
//						"El Requerimiento ha sido registrado exitosamente ",
//						null, null, new EventListener() {
//							public void onEvent(Event event) throws Exception {
//								recargar();
//							}
//						}, null);
				
			} else
				mostrarMensaje("Informaci\u00F3n",
						"Agregue al Menos un Requerimiento", null, null, null,
						null);
		}
		
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	 /**
	 * Descripcion: Permite poder agregar un nuevo repuesto al requerimiento
	 * Parametros: @param view: formularioRequerimiento.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "requerimiento", "cliente" })
	public void agregarRepuesto() {
		if (requerimiento.getDetalleRequerimientos().size() < 10)
			requerimiento.addDetalleRequerimiento(new DetalleRequerimiento());
	}

	 /**
		 * Descripcion: Permite poder eliminar un repuesto del requerimiento
		 * Parametros: @param view: formularioRequerimiento.zul  
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
	@Command
	@NotifyChange({ "requerimiento", "cliente" })
	public void eliminarRepuesto() {
		if (eliminarDetalle != null) {
			for (DetalleRequerimiento detalle : eliminarDetalle)
				requerimiento.removeDetalleRequerimiento(detalle);
		}

	}

	/**
	 * Descripcion: Permite consultar si el cliente ya existe en la Base de datos
	 * Parametros: @param view: formularioRequerimiento.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "requerimiento", "cliente" })
	public void buscarCliente() {
		String tipo = (this.tipoPersona.getValor()) ? "J" : "V";
		String cedula = cliente.getCedula();
		String cedulaBuscar = tipo + cedula;
		if (cedula != null && !cedula.equalsIgnoreCase("")) {
			Cliente cliente = sMaestros.consultarCliente(new Cliente(
					cedulaBuscar));
			if (cliente != null) {
				this.cliente = cliente;
				this.cliente.setCedula(cedulaBuscar.substring(1,
						cedulaBuscar.length()));
				this.comboTipoPersona.setValue(cedulaBuscar.substring(0, 1));
			} else
				this.cliente = new Cliente(cedulaBuscar.substring(1,
						cedulaBuscar.length()));
			this.requerimiento.setCliente(this.cliente);
			if(this.cliente!=null && this.cliente.getCiudad()!=null){
				System.out.println("Paso");
				sLocalizacion.calcularFleteZoomConPesoYDistancia(null, this.cliente.getCiudad());
			}
		} else {
			this.cliente.setCedula(null);
			cedulaRif.getValue();
		}
	}
	
	@Command
	@NotifyChange("listaMotor")
	public void paginarListaMotores(){
		int page=pagMotores.getActivePage();
		cambiarMotores(page, null, null);
	}
	
	@Command
	@NotifyChange("listaMotor")
	public void aplicarFiltroMotor(){
		cambiarMotores(0, null, null);
	}
	
	@Command
	@NotifyChange("requerimiento")
	public void cargarArchivoRepuesto(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
		super.onUpload(new PDDetalleRequerimientoEstrategy(), event, "requerimiento");
	}
	
	@Command
	public void descargarArchivoRepuesto(){
		super.download(BaseResources+"documentos/Formato Cargar Requerimientos.rar");
	}
	
	/**METODOS OVERRIDE*/
	/**CARGA MASIVA*/
	@Override
	protected void prepararCarga() {
		requerimiento.removeAllDetalleRequerimiento();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void archivosProcesados(List<?> registros, Media media, Component component) {
		List<DetalleRequerimiento> detalles = (List<DetalleRequerimiento>) registros;
		for(DetalleRequerimiento detalle : detalles){
			requerimiento.addDetalleRequerimiento(detalle);
		}
	}

	@Override
	protected void archivoVacio(Component component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void archivoNoValido(Component component) {
		// TODO Auto-generated method stub
		
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	
	/**GETTERS Y SETTERS*/
	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<MarcaVehiculo> getListaMarcasVehiculo() {
		return listaMarcasVehiculo;
	}

	public List<Motor> getListaMotor() {
		return listaMotor;
	}

	public void setListaMarcasVehiculo(List<MarcaVehiculo> listaMarcasVehiculo) {
		this.listaMarcasVehiculo = listaMarcasVehiculo;
	}

	public void setListaMotor(List<Motor> listaMotor) {
		this.listaMotor = listaMotor;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<ModeloCombo<Boolean>> getListaTraccion() {
		return listaTraccion;
	}

	public void setListaTraccion(List<ModeloCombo<Boolean>> listaTraccion) {
		this.listaTraccion = listaTraccion;
	}

	public List<ModeloCombo<Boolean>> getListaTransmision() {
		return listaTransmision;
	}

	public void setListaTransmision(List<ModeloCombo<Boolean>> listaTransmision) {
		this.listaTransmision = listaTransmision;
	}

	public ModeloCombo<Boolean> getTraccion() {
		return traccion;
	}

	public void setTraccion(ModeloCombo<Boolean> traccion) {
		this.traccion = traccion;
	}

	public ModeloCombo<Boolean> getTransmision() {
		return transmision;
	}

	public void setTransmision(ModeloCombo<Boolean> transmision) {
		this.transmision = transmision;
	}

	public void recargar() {
		redireccionar("/");
	}

	public List<DetalleRequerimiento> getEliminarDetalle() {
		return eliminarDetalle;
	}

	public void setEliminarDetalle(List<DetalleRequerimiento> eliminarDetalle) {
		this.eliminarDetalle = eliminarDetalle;
	}

	public List<ModeloCombo<Boolean>> getListaTipoPersona() {
		return listaTipoPersona;
	}

	public void setListaTipoPersona(List<ModeloCombo<Boolean>> listaTipoPersona) {
		this.listaTipoPersona = listaTipoPersona;
	}

	public ModeloCombo<Boolean> getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(ModeloCombo<Boolean> tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public List<ModeloCombo<Boolean>> getListaTipoRepuesto() {
		return listaTipoRepuesto;
	}

	public void setListaTipoRepuesto(
			List<ModeloCombo<Boolean>> listaTipoRepuesto) {
		this.listaTipoRepuesto = listaTipoRepuesto;
	}

	public ModeloCombo<Boolean> getTipoRepuesto() {
		return tipoRepuesto;
	}

	public void setTipoRepuesto(ModeloCombo<Boolean> tipoRepuesto) {
		this.tipoRepuesto = tipoRepuesto;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Estado> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public MailCliente getMailCliente() {
		return mailCliente;
	}

	public void setMailCliente(MailCliente mailCliente) {
		this.mailCliente = mailCliente;
	}

	public Motor getMotor() {
		return motor;
	}

	public void setMotor(Motor motor) {
		this.motor = motor;
	}

	public SLocalizacion getsLocalizacion() {
		return sLocalizacion;
	}

	public void setsLocalizacion(SLocalizacion sLocalizacion) {
		this.sLocalizacion = sLocalizacion;
	}
	
}
