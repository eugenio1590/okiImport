package com.okiimport.app.mvvm.controladores;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.East;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.configuracion.servicios.SControlConfiguracion;
import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.HistoricoMoneda;
import com.okiimport.app.modelo.Moneda;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class CotizacionesProveedorNacionalViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;
	
	@BeanInjector("sControlConfiguracion")
	private SControlConfiguracion sControlConfiguracion;
	
	//GUI
	@Wire("#winCotizaciones")
	private Window winCotizaciones;
	
	@Wire("#gridCotizaciones")
	private Listbox gridCotizaciones;
	
	@Wire("#pagCotizaciones")
	private Paging pagCotizaciones;
	
	@Wire("#eastCotizacion")
	private East eastCotizacion;
	
	@Wire("#btnBotones")
	private Hbox btnBotones;
	
	@Wire("#dtbFecha")
	private Datebox dtbFecha;
	
	@Wire("#txtCondicion")
	private Textbox txtCondicion;
	
	@Wire("#bandbMoneda")
	private Bandbox bandbMoneda;
	
	@Wire("#pagMonedas")
	private Paging pagMonedas;
	
	@Wire("#cmbFlete")
	private Combobox cmbFlete;
	
	//Atributos
	private static final String TITULO_EAST = "Cotizacion ";
	private static final String CONTRAINT_PRECIO_FLETE = "no empty, no zero, no negative";
	private static String titulo = "Solicitudes de Cotizacion del Requerimiento N° ";
	private String constraint_precio_flete;
	private List<Cotizacion> listaCotizacion;
	private List<DetalleCotizacion> listaDetalleCotizacion;
	private List<Moneda> monedas;
	private Persona persona;
	private Requerimiento requerimiento;
	private Cotizacion cotizacionFiltro;
	private Cotizacion cotizacionSelecionada=null;
	private Moneda monedaSeleccionada;
	private List<ModeloCombo<Boolean>> tiposFlete;
	private ModeloCombo<Boolean> tipoFlete;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("persona") Persona persona, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento){
		super.doAfterCompose(view);
		
		this.persona = persona;
		this.requerimiento = requerimiento;
		this.requerimiento.especificarInformacionVehiculo();
		cotizacionFiltro = new Cotizacion();
		titulo = titulo + requerimiento.getIdRequerimiento();
		cambiarCotizaciones(0, null, null);
		cambiarMonedas(0);
		agregarGridSort(gridCotizaciones);
		pagCotizaciones.setPageSize(pageSize);
		eastCotizacion.setTitle(TITULO_EAST);	
		
		tiposFlete = llenarTiposFleteNacional();
		tipoFlete = tiposFlete.get(0);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaCotizacion")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarCotizaciones", parametros );
		}
	}
	
	/**GLOBAL COMMAND*/
	 /**
	 * Descripcion: permitira cambiar las cotizaciones de la grid de acuerdo a la pagina dada como parametro
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaCotizacion")
	public void cambiarCotizaciones(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.consultarSolicitudCotizaciones(cotizacionFiltro, fieldSort, 
				sortDirection, requerimiento.getIdRequerimiento(), persona.getId(), page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaCotizacion = (List<Cotizacion>) parametros.get("cotizaciones");
		pagCotizaciones.setActivePage(page);
		pagCotizaciones.setTotalSize(total);
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: permite cambiar la paginacion de acuerdo a la pagina activa
	 * de Paging 
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: posicionamiento en otra pagina activa del paging 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagCotizaciones.getActivePage();
		cambiarCotizaciones(page, null, null);
	}
	
	/**
	 * Descripcion: permite filtrar los datos de la grid de acuerdo al campo
	 * establecido en el evento 
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: filtro de acuerdo al campo establecido en el evento 
	 * Nota:Ninguna
	 * */
	@Command
	@NotifyChange("listaCotizacion")
	public void aplicarFiltro(){
		cambiarCotizaciones(0, null, null);
	}
	
	/**
	 * Descripcion: permite cargar la lista de detalles de la cotizacion seleccionada
	 * Parametros: requerimiento seleccionado @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: lista de detalles de la cotizacion llena 
	 * Nota: Ninguna
	 * */
	@Command
	@SuppressWarnings("unchecked")
	@NotifyChange({"listaDetalleCotizacion","cotizacionSelecionada"})
	public void cotizar(@BindingParam("cotizacion") Cotizacion cotizacion){
		eastCotizacion.setTitle(TITULO_EAST+"N° "+cotizacion.getIdCotizacion());
		cotizacionSelecionada = cotizacion;
		Map<String, Object> parametros = sTransaccion.consultarDetallesCotizacion(new DetalleCotizacion(), (int) cotizacion.getIdCotizacion(), 
				null, null, 0, -1);
		listaDetalleCotizacion = (List<DetalleCotizacion>) parametros.get("detallesCotizacion");
		limpiarCotizacionSeleccionada();
		mostrarBotones();
		configurarAtributosCotizacion(false);
	}
	
	/**
	 * Descripcion: permitira limpiar los campos editable de la grid de detalles de la cotizacion seleccionada
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: campos vacios 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion", "cotizacionSelecionada"})
	public void limpiar(){
		limpiarCotizacionSeleccionada();
		if(listaDetalleCotizacion!=null)
			for(DetalleCotizacion detalle : listaDetalleCotizacion){
				detalle.setMarcaRepuesto(null);
				detalle.setCantidad(null);
				detalle.setPrecioVenta(null);
				detalle.setPrecioFlete(null);
			}
	}
	
	/**
	 * Descripcion: Permitira enviar los datos de la cotizacion seleccionada para su registro
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: filtro de acuerdo al campo establecido en el evento 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void enviar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar){
		if(cotizacionSelecionada!=null){
			if(checkIsFormValid()){
				cotizacionSelecionada.setEstatus("C");
				cotizacionSelecionada.setDetalleCotizacions(listaDetalleCotizacion);
				sTransaccion.registrarCotizacion(cotizacionSelecionada, requerimiento);
				this.mostrarMensaje("Informacion", "Registro Exitoso de Cotizacion", null, null, null, null);
				cotizacionSelecionada = null;
				listaDetalleCotizacion = null;
				mostrarBotones();
				configurarAtributosCotizacion(true);
				cambiarCotizaciones(0, null, null);
				eastCotizacion.setTitle(TITULO_EAST);
			}
		}
		else if(cotizacionSelecionada==null)
			mostrarMensaje("Informacion", "Debe Seleccionar una Cotizacion", null, null, null, null);
	}
	
	/**
	 * Descripcion: Permitira asginar el historial mas actual de la moneda seleccionada
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: historial asignado
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("cotizacionSelecionada")
	public void seleccionMoneda(){
		bandbMoneda.close();
		if(this.cotizacionSelecionada!=null){
			HistoricoMoneda historico = this.sControlConfiguracion.consultarActualConversion(monedaSeleccionada);
			this.cotizacionSelecionada.setHistoricoMoneda(historico);
		}
	}
	
	/*
	 * Descripcion: 
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	
	/**
	 * Descripcion: Permitira cambiar la paginacion de la listaMonedas de acuerdo a la pagina activa del Paging
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Cambio en la pagina activa del paging
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarListaMonedas(){
		int page=pagMonedas.getActivePage();
		cambiarMonedas(page);
	}
	
	/**
	 * Descripcion: Permitira cargar nuevamente las listas al cerrar la pantalla
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: listas cargadas 
	 * Nota: Ninguna
	 * */
	@Command
	public void onCloseWindow(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
		ejecutarGlobalCommand("cambiarCotizaciones", null);
	}
	
	/**
	 * Descripcion: Permitira especificar el tipo de flete que se se ha seleccionado y agregar el constraint correspondiente
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: tipo de flete especificado
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion", "constraint_precio_flete"})
	public void seleccionarTipoFlete(){
		if(!this.tipoFlete.getValor()){
			this.constraint_precio_flete = null;
			for(DetalleCotizacion detalle : this.listaDetalleCotizacion)
				detalle.setPrecioFlete(null);
		}
		else
			this.constraint_precio_flete = CONTRAINT_PRECIO_FLETE;
	}
	
	/**
	 * Descripcion: Permitira calcular el precio de la columna especificado como parametro
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * @param column: nro. de columna
	 * Retorno: precio calculado
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("cotizacionSelecionada")
	public void calcularPrecio(@BindingParam("column") int column){
		float total = 0;
		for(DetalleCotizacion detalle : this.listaDetalleCotizacion){
			switch(column){
			case 1: total+=(detalle.getPrecioVenta()!=null)?detalle.getPrecioVenta():0; break;
			case 2: total+=(detalle.getPrecioFlete()!=null)?detalle.getPrecioFlete():0; break;
			default: break;
			}
		}
		
		switch(column){
		case 1: this.cotizacionSelecionada.setTotalPrecioVenta(total); break;
		case 2: this.cotizacionSelecionada.setTotalFlete(total); break;
		default: break;
		}
	}
	
	/**
	 * Descripcion: Permitira cargar la lista de monedas de acuerdo a la pagina dada como parametro
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * Retorno: lista de monedas cargadas
	 * Nota: Ninguna
	 * */
	@SuppressWarnings("unchecked")
	@NotifyChange("monedas")
	public void cambiarMonedas(@Default("0") @BindingParam("page") int page){
		Map<String, Object> parametros = this.sControlConfiguracion.consultarMonedasConHistorico(page, pageSize);
		Integer total = (Integer) parametros.get("total");
		monedas = (List<Moneda>) parametros.get("monedas");
		pagMonedas.setActivePage(page);
		pagMonedas.setTotalSize(total);
		pagMonedas.setPageSize(pageSize);
	}
	
	/*
	 * Descripcion: 
	 * @param: Ninguno
	 * Retorno: Ninguno
	 * */
	/**
	 * Descripcion: Permitira mostrar los botones limpiar y enviar si la lista de detalles contiene datos
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: botones Mostrados
	 * Nota: Ninguna
	 * */
	private void mostrarBotones(){
		if(listaDetalleCotizacion!=null)
			if(listaDetalleCotizacion.size()>0){
				btnBotones.setVisible(true);
				return;
			}
		btnBotones.setVisible(false);
	}
	
	/**
	 * Descripcion: Permitira limpiar la informacion de la cotizacion seleccionada
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: campos vacios
	 * Nota: Ninguna
	 * */
	private void limpiarCotizacionSeleccionada(){
		if(cotizacionSelecionada!=null){
			cotizacionSelecionada.setFechaVencimiento(AbstractServiceImpl.sumarORestarFDia(new Date(), 1));
			cotizacionSelecionada.setCondiciones(null);
		}
	}
	
	/**
	 * Descripcion: Permitira preparar los componentes graficos para cotizar 
	 * de acuerdo al atributo pasado como parametro
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * @param readOnly: indicara si el campo es de solo lectura o no
	 * Retorno: atributos Configurados
	 * Nota: Ninguna
	 * */
	private void configurarAtributosCotizacion(boolean readOnly){
		txtCondicion.setReadonly(readOnly);
		dtbFecha.setButtonVisible(!readOnly);
		bandbMoneda.setButtonVisible(!readOnly);
		cmbFlete.setButtonVisible(!readOnly);
	}
	
	/**METODOS PROPIOS Y DE LA CLASE*/
	/**SETTERS Y GETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}
	
	public SControlConfiguracion getsControlConfiguracion() {
		return sControlConfiguracion;
	}

	public void setsControlConfiguracion(SControlConfiguracion sControlConfiguracion) {
		this.sControlConfiguracion = sControlConfiguracion;
	}

	public List<Cotizacion> getListaCotizacion() {
		return listaCotizacion;
	}

	public void setListaCotizacion(List<Cotizacion> listaCotizacion) {
		this.listaCotizacion = listaCotizacion;
	}

	public List<DetalleCotizacion> getListaDetalleCotizacion() {
		return listaDetalleCotizacion;
	}

	public void setListaDetalleCotizacion(
			List<DetalleCotizacion> listaDetalleCotizacion) {
		this.listaDetalleCotizacion = listaDetalleCotizacion;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Cotizacion getCotizacionSelecionada() {
		return cotizacionSelecionada;
	}

	public void setCotizacionSelecionada(Cotizacion cotizacionSelecionada) {
		this.cotizacionSelecionada = cotizacionSelecionada;
	}

	public Cotizacion getCotizacionFiltro() {
		return cotizacionFiltro;
	}

	public void setCotizacionFiltro(Cotizacion cotizacionFiltro) {
		this.cotizacionFiltro = cotizacionFiltro;
	}

	public Moneda getMonedaSeleccionada() {
		return monedaSeleccionada;
	}

	public void setMonedaSeleccionada(Moneda monedaSeleccionada) {
		this.monedaSeleccionada = monedaSeleccionada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConstraint_precio_flete() {
		return constraint_precio_flete;
	}

	public void setConstraint_precio_flete(String constraint_precio_flete) {
		this.constraint_precio_flete = constraint_precio_flete;
	}

	public List<ModeloCombo<Boolean>> getTiposFlete() {
		return tiposFlete;
	}

	public void setTiposFlete(List<ModeloCombo<Boolean>> tiposFlete) {
		this.tiposFlete = tiposFlete;
	}

	public ModeloCombo<Boolean> getTipoFlete() {
		return tipoFlete;
	}

	public void setTipoFlete(ModeloCombo<Boolean> tipoFlete) {
		this.tipoFlete = tipoFlete;
	}
}