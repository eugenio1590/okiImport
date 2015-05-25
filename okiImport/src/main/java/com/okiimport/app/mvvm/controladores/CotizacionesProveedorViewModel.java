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
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class CotizacionesProveedorViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
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
	private static final int PAGE_SIZE = 3;
	private static final String TITULO_EAST = "Cotizacion ";
	private String titulo = "Solicitudes de Cotizacion del Requerimiento N° ";
	
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

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("usuario") Usuario usuario, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento){
		super.doAfterCompose(view);
		
		this.persona = usuario.getPersona();
		this.requerimiento = requerimiento;
		this.requerimiento.especificarInformacionVehiculo();
		cotizacionFiltro = new Cotizacion();
		titulo = titulo + requerimiento.getIdRequerimiento();
		cambiarCotizaciones(0, null, null);
		cambiarMonedas(0);
		agregarGridSort(gridCotizaciones);
		pagCotizaciones.setPageSize(PAGE_SIZE);
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
	/*
	 * Descripcion: permitira cambiar las cotizaciones de la grid de acuerdo a la pagina dada como parametro
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaCotizacion")
	public void cambiarCotizaciones(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.consultarSolicitudCotizaciones(cotizacionFiltro, fieldSort, 
				sortDirection, requerimiento.getIdRequerimiento(), persona.getId(), page, PAGE_SIZE);
		Integer total = (Integer) parametros.get("total");
		listaCotizacion = (List<Cotizacion>) parametros.get("cotizaciones");
		pagCotizaciones.setActivePage(page);
		pagCotizaciones.setTotalSize(total);
	}
	
	/**COMMAND*/
	/*
	 * Descripcion: permitira cambiar la paginacion de acuerdo a la pagina activa del Paging
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagCotizaciones.getActivePage();
		cambiarCotizaciones(page, null, null);
	}
	
	/*
	 * Descripcion: permitira filtrar los datos de la grid de acuerdo al campo establecido en el evento
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("listaCotizacion")
	public void aplicarFiltro(){
		cambiarCotizaciones(0, null, null);
	}
	
	/*
	 * Descripcion: permitira cargar la lista de detalles de la cotizacion seleccionada
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 * */
	@Command
	@SuppressWarnings("unchecked")
	@NotifyChange({"listaDetalleCotizacion","cotizacionSelecionada"})
	public void cotizar(@BindingParam("cotizacion") Cotizacion cotizacion){
		eastCotizacion.setTitle(TITULO_EAST+"N° "+cotizacion.getIdCotizacion());
		cotizacionSelecionada = cotizacion;
		Map<String, Object> parametros = sTransaccion.consultarDetallesCotizacion(null, cotizacion.getIdCotizacion(), 
				null, null, 0, -1);
		listaDetalleCotizacion = (List<DetalleCotizacion>) parametros.get("detallesCotizacion");
		limpiarCotizacionSeleccionada();
		mostrarBotones();
		configurarAtributosCotizacion(false);
	}
	
	/*
	 * Descripcion: permitira limpiar los campos editable de la grid de detalles de la cotizacion seleccionada
	 * @param Ninguno
	 * Retorno: Ninguno
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
			}
	}
	
	/*
	 * Descripcion: Permitira enviar los datos de la cotizacion seleccionada para su registro
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("*")
	public void enviar(){
		if(cotizacionSelecionada!=null){
			if(checkIsFormValid()){
				cotizacionSelecionada.setDetalleCotizacions(listaDetalleCotizacion);
				sTransaccion.registrarCotizacion(cotizacionSelecionada);
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
	
	/*
	 * Descripcion: Permitira asginar el historial mas actual de la moneda seleccionada
	 * @param: Ninguno
	 * Retorno: Ninguno
	 */
	@Command
	@NotifyChange("cotizacionSelecionada")
	public void seleccionMoneda(){
		bandbMoneda.close();
		if(this.cotizacionSelecionada!=null){
			HistoricoMoneda historico = this.sControlConfiguracion.consultarActualConversion(monedaSeleccionada);
			if(historico!=null)
				this.cotizacionSelecionada.setHistoricoMoneda(historico);
		}
	}
	
	/*
	 * Descripcion: permitira cambiar la paginacion de acuerdo a la pagina activa del Paging
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("*")
	public void paginarListaMonedas(){
		int page=pagMonedas.getActivePage();
		cambiarMonedas(page);
	}
	
	/*
	 * Descripcion: Permitira cargar nuevamente la lista de requerimientos del proveedor
	 * @param: Ninguno
	 * Retorno: Ninguno
	 */
	@Command
	public void cargarRequerimientos(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}
	
	@Command
	@NotifyChange("listaDetalleCotizacion")
	public void seleccionarTipoFlete(){
		if(!this.tipoFlete.getValor()){
			System.out.println("CAMBIO FLETE");
			for(DetalleCotizacion detalle : this.listaDetalleCotizacion)
				detalle.setPrecioFlete(null);
		}
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	/*
	 * Descripcion permitira cargar la lista de monedas de acuerdo a la pagina dada como parametro
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * Retorno: Ninguno
	 */
	@SuppressWarnings("unchecked")
	@NotifyChange("monedas")
	public void cambiarMonedas(@Default("0") @BindingParam("page") int page){
		Map<String, Object> parametros = this.sControlConfiguracion.consultarMonedas(page, PAGE_SIZE);
		Integer total = (Integer) parametros.get("total");
		monedas = (List<Moneda>) parametros.get("monedas");
		//if(pagMonedas!=null){
			pagMonedas.setActivePage(page);
			pagMonedas.setTotalSize(total);
			pagMonedas.setPageSize(PAGE_SIZE);
		//}
	}
	
	/*
	 * Descripcion: Permitira mostrar los botones limpiar y enviar si la lista de detalles contiene datos
	 * @param: Ninguno
	 * Retorno: Ninguno
	 * */
	private void mostrarBotones(){
		if(listaDetalleCotizacion!=null)
			if(listaDetalleCotizacion.size()>0){
				btnBotones.setVisible(true);
				return;
			}
		btnBotones.setVisible(false);
	}
	
	private void limpiarCotizacionSeleccionada(){
		if(cotizacionSelecionada!=null){
			cotizacionSelecionada.setFechaVencimiento(AbstractServiceImpl.sumarORestarFDia(new Date(), 1));
			cotizacionSelecionada.setCondiciones(null);
		}
	}
	
	private void configurarAtributosCotizacion(boolean readOnly){
		txtCondicion.setReadonly(readOnly);
		dtbFecha.setButtonVisible(!readOnly);
		bandbMoneda.setButtonVisible(!readOnly);
		cmbFlete.setButtonVisible(!readOnly);
	}
	
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