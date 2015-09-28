package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
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

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Ciudad;
import com.okiimport.app.modelo.ClasificacionRepuesto;
import com.okiimport.app.modelo.DetalleCotizacionInternacional;
import com.okiimport.app.modelo.Estado;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
import com.okiimport.app.mvvm.constraint.CustomConstraint.EConstraint;
import com.okiimport.app.mvvm.constraint.RegExpressionConstraint;
import com.okiimport.app.mvvm.constraint.RegExpressionConstraint.RegExpression;
import com.okiimport.app.transaccion.servicios.STransaccion;


public class RegistrarProveedorViewModel extends AbstractRequerimientoViewModel {

	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
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
	@Wire("#btnLimpiar")
	private Button btnLimpiar;
	@Wire("#cmbEstado")
	private Combobox cmbEstado;
	@Wire("#cmbCiudad")
	private Combobox cmbCiudad;
	
	//Atributos
	protected Proveedor proveedor;
	private List<MarcaVehiculo> listaMarcaVehiculos;
	private List<ClasificacionRepuesto> listaClasificacionRepuestos;
	private List<MarcaVehiculo> marcaSeleccionadas;
	private List<ClasificacionRepuesto> tipoRepuestoSeleccionados;
	private List<ModeloCombo<Boolean>> listaTipoPersona;
	private ModeloCombo<Boolean> tipoPersona;
	private ModeloCombo<Boolean> tipoPersonaCed;
	private List<ModeloCombo<Boolean>> listaTipoProveedor;
	private ModeloCombo<Boolean> tipoProveedor;
	private List<Estado> listaEstados;
	private List<Ciudad> listaCiudad;
	private Estado estadoSeleccionado;
	private boolean makeAsReadOnly;
	private Boolean cerrar;
	private String recordMode;
	
	
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
	    this.makeAsReadOnly = (recordMode != null && recordMode.equalsIgnoreCase("READ"))? true : false; 
		this.proveedor = (proveedor==null) ? new Proveedor() :  proveedor;
		this.cerrar = (cerrar==null) ? true : cerrar;
		listaEstados = llenarListaEstados();
		pagMarcas.setPageSize(pageSize=9);
		pagTipoRepuestos.setPageSize(pageSize=9);
		gridMarcasVender.setPageSize(pageSize);
		gridTipoRepuestosVender.setPageSize(pageSize);
		consultarMarcas(0);
		consultarTipoRepuesto(0);
		listaTipoPersona = llenarListaTipoPersona();
		this.tipoPersona = listaTipoPersona.get(1);
		listaTipoProveedor = llenarListaTipoProveedor();
		tipoProveedor=consultarTipoProveedor(this.proveedor.getTipoProveedor(),listaTipoProveedor);
		tipoPersona=consultarTipoPersona(this.proveedor.getCedula(),listaTipoPersona);
		String cedula = this.proveedor.getCedula();
		if(cedula!=null)
			this.proveedor.setCedula(this.proveedor.getCedula().substring(1));
	}

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
	@NotifyChange({ "proveedor" })
	public void limpiar() {
		proveedor = new Proveedor();
	}

	/**
	 * Descripcion: Permite Consultar el tipo de proveedor
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Tipo de Proveedor 
	 * Nota: Ninguna
	 * */
	public ModeloCombo<Boolean> consultarTipoProveedor(Boolean tipoProveedor, List <ModeloCombo<Boolean>> listaTipoProveedor){
		if(tipoProveedor!=null)
			for(ModeloCombo<Boolean> tipoProveedorl: listaTipoProveedor )
				if (tipoProveedorl.getValor() == tipoProveedor)
					return tipoProveedorl;
			
		return null;
		
	}
	
	/**
	 * Descripcion: Permite Consultar el tipo de persona
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Tipo de Persona
	 * Nota: Ninguna
	 * */
	public ModeloCombo<Boolean> consultarTipoPersona(String cedula, List <ModeloCombo<Boolean>> listaTipoPersona){
		if (cedula!=null){
			String tipoPersona = cedula.substring(0, 1);
			for(ModeloCombo<Boolean> tipoPersonal: listaTipoPersona )
				if (tipoPersonal.getNombre().equalsIgnoreCase(tipoPersona))
					return tipoPersonal;
		}
		return this.tipoPersona;
		
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

			if (proveedor.getMarcaVehiculos().size() > 0
					&& proveedor.getClasificacionRepuestos().size() > 0) {
				
				btnEnviar.setDisabled(true);
				btnLimpiar.setDisabled(true);
				
				registrarProveedor(cerrar);
			}

			else
				mostrarMensaje("Información", "Agregue al Menos una Marca y Una Clasificacion de Repuesto",
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
		this.moveSelection(listaMarcaVehiculos, proveedor.getMarcaVehiculos(),
				marcaSeleccionadas, "No se puede agregar Marca");
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
		this.moveSelection(proveedor.getMarcaVehiculos(), listaMarcaVehiculos,
				marcaSeleccionadas, "No se puede eliminar la Marca");
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
		this.moveSelection(listaClasificacionRepuestos,
				proveedor.getClasificacionRepuestos(),
				tipoRepuestoSeleccionados,
				"No se puede agregar el Tipo de Repuesto");
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
		this.moveSelection(proveedor.getClasificacionRepuestos(),
				listaClasificacionRepuestos, tipoRepuestoSeleccionados,
				"No se puede eliminar el Tipo de Repuesto");
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
	 * Descripcion: Permite registrar un proveedor en el sistema
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Proveedor registrado, correo enviado al proveedor 
	 * Nota: Ninguna
	 * */
	protected Proveedor registrarProveedor(boolean enviarEmail){
		String tipo = (this.tipoPersona.getValor()) ? "J" : "V";
		proveedor.setCedula(tipo + proveedor.getCedula());
		proveedor.setEstatus("solicitante");

		if (tipoProveedor != null)

		proveedor.setTipoProveedor(this.tipoProveedor.getValor());

		proveedor = sMaestros.registrarProveedor(proveedor);
		
		String str = null;
		if(recordMode.equalsIgnoreCase("EDIT"))
			str = "Su Solicitud Ha sido Registrada Exitosamente, Se Respondera en 48 Horas ";
		else
			str = "Proveedor Actualizado Exitosamente";

		if(enviarEmail){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("nombreSolicitante", proveedor.getNombre());
			model.put("cedula", proveedor.getCedula());

			mailService.send(proveedor.getCorreo(), "Solicitud Proveedor",
					"registrarProveedor.html", model);

			mostrarMensaje("Informacion", str, null, null,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							redireccionar("/");
						}
					}, null);
		}
		else {
			mostrarMensaje("Informacion", str, null, null,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							winProveedor.onClose();
							ejecutarGlobalCommand("consultarProveedores", null);
						}
					}, null);
		}
		return proveedor;
	}

	/**
	 * Descripcion: Permite consultar las marcas
	 * Parametros: @param view: formularioProveedor.zul 
	 * Retorno: Marcas consultadas y llenado de la lista de marcas a seleccionar
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "listaMarcaVehiculos" })
	private void consultarMarcas(int page) {
		Map<String, Object> Parametros = sMaestros.ConsultarMarca(page,
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
	@NotifyChange({ "listaClasificacionRepuestos" })
	private void consultarTipoRepuesto(int page) {
		Map<String, Object> Parametros = sMaestros
				.ConsultarClasificacionRepuesto(page, pageSize);
		listaClasificacionRepuestos = (List<ClasificacionRepuesto>) Parametros
				.get("clasificacionRepuesto");
		Integer total = (Integer) Parametros.get("total");
		gridClasificacionRepuesto.setMultiple(true);
		gridClasificacionRepuesto.setCheckmark(true);
		pagTipoRepuestos.setActivePage(page);
		pagTipoRepuestos.setTotalSize(total);
	}
	
	
	/**METODOS PROPIOS DE LA CLASE*/
	
	/**GETTERS Y SETTERS*/

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
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

	public List<ModeloCombo<Boolean>> getListaTipoProveedor() {
		return listaTipoProveedor;
	}

	public void setListaTipoProveedor(List<ModeloCombo<Boolean>> listaTipoProveedor) {
		this.listaTipoProveedor = listaTipoProveedor;
	}

	public ModeloCombo<Boolean> getTipoProveedor() {
		return tipoProveedor;
	}

	public void setTipoProveedor(ModeloCombo<Boolean> tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
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
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}
	
}
