package com.okiimport.app.mvvm.controladores;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Ciudad;
import com.okiimport.app.model.ClasificacionRepuesto;
import com.okiimport.app.model.Estado;
import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.model.Pais;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.mail.MailProveedor;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarProveedorViewModel extends AbstractRequerimientoViewModel {
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("mailProveedor")
	private MailProveedor mailProveedor;
	
	//GUI
	@Wire("#winProveedor")
	private Window winProveedor;
	
	@Wire("#gridMarcas")
	private Listbox gridMarcas;
	
	@Wire("#gridClasificacionRepuesto")
	private Listbox gridClasificacionRepuesto;
	
	@Wire("#gridMarcasVender")
	private Listbox gridMarcasVender;
	
	@Wire("#gridTipoRepuestosVender")
	private Listbox gridTipoRepuestosVender;
	
	@Wire("#pagMarcas")
	private Paging pagMarcas;
	
	@Wire("#pagTipoRepuestos")
	private Paging pagTipoRepuestos;
	
	@Wire("#cmbEstado")
	private Combobox cmbEstado;
	
	@Wire("#cmbCiudad")
	private Combobox cmbCiudad;
	
	@Wire("#btnLimpiar")
	private Button btnLimpiar;
	
	//Atributos
	private static final Comparator<MarcaVehiculo> COMPR_MARCA_VEHICULO = MarcaVehiculo.getComparator();
	private static final Comparator<ClasificacionRepuesto> COMPR_CLASIFICACION_REPUESTO = ClasificacionRepuesto.getComparator();
	
	private CustomConstraint constrEstado = null;
	private CustomConstraint constrCiudad = null;
	
	private List<MarcaVehiculo> listaMarcaVehiculos;
	private List<ClasificacionRepuesto> listaClasificacionRepuestos;
	private List<MarcaVehiculo> marcaSeleccionadas;
	private List<ClasificacionRepuesto> tipoRepuestoSeleccionados;
	private List<Pais> listaPaises;
	private List<ModeloCombo<Boolean>> listaTipoPersona;
	private ModeloCombo<Boolean> tipoPersona;
	private ModeloCombo<Boolean> tipoPersonaCed;
	private List<Estado> listaEstados;
	private List<Ciudad> listaCiudad;
	private Estado estadoSeleccionado;
	private boolean makeAsReadOnly;
	private Boolean cerrar;
	private String recordMode;
	private Proveedor proveedor;
	
	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("proveedor") Proveedor proveedor,
			@ExecutionArgParam("recordMode") String recordMode,
			@ExecutionArgParam("cerrar") Boolean cerrar) {
		super.doAfterCompose(view);
		this.recordMode = (recordMode == null) ? "EDIT" : recordMode;
		this.proveedor = (proveedor==null) ? new Proveedor() :  proveedor;
		this.cerrar = (cerrar==null) ? true : cerrar;
		makeAsReadOnly = (recordMode != null && recordMode.equalsIgnoreCase("READ"))? true : false; 
		listaPaises = llenarListaPaises();
		listaEstados = llenarListaEstados();
		pagMarcas.setPageSize(pageSize);
		pagTipoRepuestos.setPageSize(pageSize);
		gridMarcasVender.setPageSize(pageSize);
		gridTipoRepuestosVender.setPageSize(pageSize);
		consultarMarcas(0);
		consultarTipoRepuesto(0);
		listaTipoPersona = llenarListaTipoPersona();
		tipoPersona = listaTipoPersona.get(1);
		tipoPersona=consultarTipoPersona(this.proveedor.getCedula(),listaTipoPersona);
		String cedula = this.proveedor.getCedula();
		if(cedula!=null)
			this.proveedor.setCedula(this.proveedor.getCedula().substring(1));
	}

	/**COMMAND*/
	
	/**
	 * Descripcion: Permite Habilitar el boton limpiar segun el evento que se solicite
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Habilitacion del boton limpiar 
	 * Nota: Ninguna
	 * */
	@Command
	public void habilitarBtnLimpiar(@BindingParam("id") String id) {
		if (id.equalsIgnoreCase("tabDatosFiscales"))
			btnLimpiar.setVisible(true);
		else
			btnLimpiar.setVisible(false);
	}

	/**
	 * Descripcion: Permite limpiar los campos del formulario registrar Proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Campos Vacios 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "proveedor",  "estado", "constrEstado", "constrCiudad" })
	public void limpiar() {
		proveedor = new Proveedor();
		limpiarEstadoYCiudad();
	}
	
	 /**
	 * Descripcion: Permite Registrar el proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: proveedor registrado
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "proveedor" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar,
			@BindingParam("recordMode") String recordMode) {
		if (checkIsFormValid()) {
			if(!verificarExistencia()){
				if (proveedor.getMarcaVehiculos().size() > 0
						&& proveedor.getClasificacionRepuestos().size() > 0) {

					btnEnviar.setDisabled(true);
					btnLimpiar.setDisabled(true);

					registrarProveedor(cerrar);
				}

				else
					mostrarMensaje("Informaci\u00F3n", "Agregue al Menos una Marca y Una Clasificaci\u00F3n de Repuesto",
							null, null, null, null);
			}
			else
				mostrarMensaje("Informaci\u00F3n", "Ya se encuentra registrado en el sistema",
						null, null, null, null);
		}
	}

	
	/**
	 * Descripcion: Permite Seleccionar una marca a vender por el proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: marca seleccionada
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void agregarMarcas() {
		this.moveSelection(listaMarcaVehiculos, proveedor.getMarcaVehiculos(), marcaSeleccionadas, 
				COMPR_MARCA_VEHICULO, false, "No se puede agregar Marca");
	}

	/**
	 * Descripcion: Permite eliminar de la seleccion una marca a vender por el proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: marca eliminada
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void eliminarMarcas() {
		if(marcaSeleccionadas!=null && !marcaSeleccionadas.isEmpty())
			proveedor.getMarcaVehiculos().removeAll(marcaSeleccionadas);
	}

	/**
	 * Descripcion: Permite Seleccionar un tipo de repuesto a vender por el proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: tipo de repuesto seleccionada
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void agregarTipoRepuesto() {
		this.moveSelection(listaClasificacionRepuestos, proveedor.getClasificacionRepuestos(), tipoRepuestoSeleccionados,
				COMPR_CLASIFICACION_REPUESTO, false, "No se puede agregar el Tipo de Repuesto");
	}

	/**
	 * Descripcion: Permite eliminar de la seleccion un tipo de repuesto a vender por el proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: tipo de repuesto eliminado
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void eliminarTipoRepuesto() {
		if(tipoRepuestoSeleccionados!=null && !tipoRepuestoSeleccionados.isEmpty())
			proveedor.getClasificacionRepuestos().removeAll(tipoRepuestoSeleccionados);
	}

	/**
	 * Descripcion: Cambia la paginacion de acuerdo a la pagina activa
	 * de Paging pagMarcas
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Posicionamiento en otra pagina activa del paging 
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void paginarLista(@BindingParam("tipo") int tipo) {
		switch (tipo) {
		case 1:
			consultarMarcas(pagMarcas.getActivePage());
			break;
		}
	}

	/**
	 * Descripcion: Cambia la paginacion de acuerdo a la pagina activa
	 * de Paging pagTipoRepuestos
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Posicionamiento en otra pagina activa del paging 
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void paginarListaTipoRepuesto(@BindingParam("tipo") int tipo) {
		switch (tipo) {
		case 1:
			consultarTipoRepuesto(pagTipoRepuestos.getActivePage());
			break;
		}
	}
	
	/**
	 * Descripcion: Permite consultar las marcas
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Marcas consultadas y llenado de la lista de marcas a seleccionar
	 * Nota: Ninguna
	 * */
	@SuppressWarnings("unchecked")
	@NotifyChange({ "listaMarcaVehiculos" })
	private void consultarMarcas(int page) {
		Map<String, Object> Parametros = sMaestros.consultarMarcas(page,
				pageSize);
		listaMarcaVehiculos = (List<MarcaVehiculo>) Parametros.get("marcas");
		Integer total = (Integer) Parametros.get("total");
		gridMarcas.setMultiple(true);
		gridMarcas.setCheckmark(true);
		pagMarcas.setActivePage(page);
		pagMarcas.setTotalSize(total);
	}

	/**
	 * Descripcion: Permite Consultar Tipo de Repuestos
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Tipo de repuestos consultados y llenado de la lista tipo de repuestos a seleccionar
	 * Nota: Ninguna
	 * */
	@SuppressWarnings("unchecked")
	@NotifyChange({ "listaClasificacionRepuestos" })
	private void consultarTipoRepuesto(int page) {
		Map<String, Object> Parametros = sMaestros.consultarClasificacionRepuesto(page, pageSize);
		listaClasificacionRepuestos = (List<ClasificacionRepuesto>) Parametros.get("clasificacionRepuesto");
		Integer total = (Integer) Parametros.get("total");
		gridClasificacionRepuesto.setMultiple(true);
		gridClasificacionRepuesto.setCheckmark(true);
		pagTipoRepuestos.setActivePage(page);
		pagTipoRepuestos.setTotalSize(total);
	}
	
	/**
	 * Descripcion: Permite eliminar la referencia de ciudad y estado en el caso de ser un proveedor internacional.
	 * 		Ademas actualizara los constraint de los combos de ciudad y estado.
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "estado", "proveedor", "constrEstado", "constrCiudad" })
	public void actualizarLocalidad(){
		limpiarEstadoYCiudad();
		if(proveedor.isNacional()){
			constrEstado = super.getNotEmptyValidator();
			constrCiudad = super.getNotEmptyValidator();
		}
	}
	
	/**METODOS OVERRIDE*/
	@Command
	@Override
	@NotifyChange({ "listaCiudades", "proveedor" })
	public void buscarCiudades(){
		super.buscarCiudades();
		if(listaCiudades.size()>0)
			proveedor.setCiudad(listaCiudades.get(0));
	}

	/**METODOS PROPIOS DE LA CLASE*/
	
	/**
	 * Descripcion: Permite Consultar el tipo de persona
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Tipo de Persona
	 * Nota: Ninguna
	 * */
	private ModeloCombo<Boolean> consultarTipoPersona(String cedula, List <ModeloCombo<Boolean>> listaTipoPersona){
		if (cedula!=null){
			String tipoPersona = cedula.substring(0, 1);
			for(ModeloCombo<Boolean> tipoPersonal: listaTipoPersona )
				if (tipoPersonal.getNombre().equalsIgnoreCase(tipoPersona))
					return tipoPersonal;
		}
		return this.tipoPersona;
	}
	
	/**
	 * Descripcion: Permite registrar un proveedor en el sistema
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Proveedor registrado, correo enviado al proveedor 
	 * Nota: Ninguna
	 * */
	private Proveedor registrarProveedor(boolean enviarEmail){
		proveedor.setCedula(getCedulaComleta());
		proveedor.setEstatus("solicitante");

		if (proveedor.isNacional())
			proveedor.setTipoProveedor(true);
		else
			proveedor.setTipoProveedor(false);

		proveedor = sMaestros.registrarProveedor(proveedor);
		
		String str = null;
		if(recordMode.equalsIgnoreCase("EDIT"))
			str = "Su Solicitud Ha sido Registrada Exitosamente, Se Respondera en 48 Horas ";
		else
			str = "Proveedor Actualizado Exitosamente";

		if(enviarEmail){
			this.mailProveedor.registrarSolicitudProveedor(proveedor, mailService);


			mostrarMensaje("Informacion", str, null, null,
					new EventListener<Event>() {

						public void onEvent(Event event) throws Exception {
							redireccionar("/");
						}
					}, null);
		}
		else {
			mostrarMensaje("Informacion", str, null, null,
					new EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							winProveedor.onClose();
							ejecutarGlobalCommand("consultarProveedores", null);
						}
					}, null);
		}
		return proveedor;
	}
	
	/**
	 * Descripcion: Permite limpiar las variables que se encargan de las variables de ciudad y estado
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void limpiarEstadoYCiudad(){
		if(constrEstado!=null)
			constrEstado.hideComponentError();
		if(constrCiudad!=null)
			constrCiudad.hideComponentError();
		constrEstado = null;
		constrCiudad = null;
		estado = null;
		proveedor.setCiudad(null);
	}
	
	/**
	 * Descripcion: Permitira obtener la cedula completa del proveedor
	 * Parametros: Ninguno
	 * Retorno Ninguno
	 * Nota: Ninguna
	 * */
	private String getCedulaComleta(){
		//String tipo = (this.tipoPersona.getValor()) ? "J" : "V";
		return this.tipoPersona.getNombre() + proveedor.getCedula();
	}
	
	private boolean verificarExistencia(){
		return (sMaestros.consultarProveedor(new Proveedor(getCedulaComleta()))!=null);
	}
	
	/**GETTERS Y SETTERS*/	
	public CustomConstraint getConstrEstado() {
		return constrEstado;
	}

	public void setConstrEstado(CustomConstraint constrEstado) {
		this.constrEstado = constrEstado;
	}

	public CustomConstraint getConstrCiudad() {
		return constrCiudad;
	}

	public void setConstrCiudad(CustomConstraint constrCiudad) {
		this.constrCiudad = constrCiudad;
	}
	
	public List<MarcaVehiculo> getListaMarcaVehiculos() {
		return listaMarcaVehiculos;
	}

	public void setListaMarcaVehiculos(List<MarcaVehiculo> listaMarcaVehiculos) {
		this.listaMarcaVehiculos = listaMarcaVehiculos;
	}

	public List<MarcaVehiculo> getMarcaSeleccionadas() {
		return marcaSeleccionadas;
	}

	public void setMarcaSeleccionadas(List<MarcaVehiculo> marcaSeleccionadas) {
		this.marcaSeleccionadas = marcaSeleccionadas;
	}

	public List<ClasificacionRepuesto> getListaClasificacionRepuesto() {
		return listaClasificacionRepuestos;
	}

	public void setListaClasificacionRepuesto(
			List<ClasificacionRepuesto> listaClasificacionRepuesto) {
		this.listaClasificacionRepuestos = listaClasificacionRepuesto;
	}

	public List<ClasificacionRepuesto> getTipoRepuestoSeleccionados() {
		return tipoRepuestoSeleccionados;
	}

	public void setTipoRepuestoSeleccionados(
			List<ClasificacionRepuesto> tipoRepuestoSeleccionados) {
		this.tipoRepuestoSeleccionados = tipoRepuestoSeleccionados;
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

	public List<Estado> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Estado> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public boolean isMakeAsReadOnly() {
		return makeAsReadOnly;
	}

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}
	
	public Boolean getCerrar() {
		return cerrar;
	}

	public void setCerrar(Boolean cerrar) {
		this.cerrar = cerrar;
	}

	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public Estado getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(Estado estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public ModeloCombo<Boolean> getTipoPersonaCed() {
		return tipoPersonaCed;
	}

	public void setTipoPersonaCed(ModeloCombo<Boolean> tipoPersonaCed) {
		this.tipoPersonaCed = tipoPersonaCed;
	}

	public MailProveedor getMailProveedor() {
		return mailProveedor;
	}

	public void setMailProveedor(MailProveedor mailProveedor) {
		this.mailProveedor = mailProveedor;
	}

	public List<Pais> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<Pais> listaPaises) {
		this.listaPaises = listaPaises;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}
	
}
