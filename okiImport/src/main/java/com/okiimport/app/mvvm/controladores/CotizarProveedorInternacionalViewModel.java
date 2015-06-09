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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.East;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.configuracion.servicios.SControlConfiguracion;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.HistoricoMoneda;
import com.okiimport.app.modelo.Moneda;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class CotizarProveedorInternacionalViewModel extends AbstractRequerimientoViewModel implements EventListener<Event>{
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("sControlConfiguracion")
	private SControlConfiguracion sControlConfiguracion;
	
	//GUI
	@Wire("#winCotizar")
	private Window winCotizar;
	
	@Wire("#eastCotizacion")
	private East eastCotizacion;
	
	@Wire("#bandbMoneda")
	private Bandbox bandbMoneda;
	
	@Wire("#pagMonedas")
	private Paging pagMonedas;
	
	//Atributos
	private static final int PAGE_SIZE = 3;
	private static final String TITULO_EAST = "Cotizacion ";
	private static final String CONTRAINT_PRECIO_FLETE = "no empty, no zero, no negative";
	private static String titulo = "Solicitudes de Cotizacion del Requerimiento N° ";
	
	private String constraint_precio_flete;
	
	private List<DetalleCotizacion> listaDetalleCotizacion;
	private List<Moneda> monedas;
	
	
	private Requerimiento requerimiento;
	private Cotizacion cotizacionSelecionada=null;
	private Moneda monedaSeleccionada;
	
	private List<ModeloCombo<Boolean>> tiposFlete;
	private List<ModeloCombo<Boolean>> formasFlete;
	private ModeloCombo<Boolean> tipoFlete;
	private ModeloCombo<Boolean> formaFlete;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("cotizacion") Cotizacion cotizacion){
		super.doAfterCompose(view);
		
		this.requerimiento = requerimiento;
		this.cotizacionSelecionada = cotizacion;
		titulo = titulo + requerimiento.getIdRequerimiento();
		cambiarMonedas(0);
		eastCotizacion.setTitle(TITULO_EAST+"N° "+cotizacionSelecionada.getIdCotizacion());	
		
		Map<String, Object> parametros = sTransaccion.consultarDetallesCotizacion(null, (int) cotizacion.getIdCotizacion(), 
				null, null, 0, -1);
		listaDetalleCotizacion = (List<DetalleCotizacion>) parametros.get("detallesCotizacion");
		limpiarCotizacionSeleccionada();
		
		formasFlete = llenarFormasDeFleteInternacional();
		formaFlete = formasFlete.get(0);
		
		tiposFlete = llenarTiposFleteInternacional();
		tipoFlete = tiposFlete.get(0);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaCotizacion")
	public void onEvent(Event event) throws Exception {
		// TODO Auto-generated method stub	
		if(event instanceof SortEvent && event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", ((SortEvent) event).isAscending());
			ejecutarGlobalCommand("cambiarCotizaciones", parametros );
		}
		else if(event instanceof Messagebox.ClickEvent){
			winCotizar.onClose(); //Falta Mensaje
			ejecutarGlobalCommand("cambiarCotizaciones", null);
		}
	}
	
	/**COMMAND*/
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
				detalle.setPrecioFlete(null);
			}
	}
	
	/*
	 * Descripcion: Permitira enviar los datos de la cotizacion seleccionada para su registro
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("*")
	public void enviar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar){
		if(checkIsFormValid()){
			cotizacionSelecionada.setDetalleCotizacions(listaDetalleCotizacion);
			sTransaccion.registrarCotizacion(cotizacionSelecionada);
			this.mostrarMensaje("Informacion", "Registro Exitoso de Cotizacion", null, null, this, null);
		}
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
	 * Descripcion: Permitira especificar el tipo de flete que se se ha seleccionado y agregar el constraint correspondiente
	 * @param Ninguno
	 * Retorno: Ninguno 
	 */
	@Command
	@NotifyChange({"listaDetalleCotizacion", "constraint_precio_flete"})
	public void seleccionarTipoFlete(){
		if(!this.tipoFlete.getValor()){
			this.constraint_precio_flete = null;
			System.out.println("CAMBIO FLETE");
			for(DetalleCotizacion detalle : this.listaDetalleCotizacion){
				detalle.setPrecioFlete(null);
				detalle.setAlto(null);
				detalle.setAncho(null);
				detalle.setLargo(null);
				detalle.setPeso(null);
			}
		}
		else
			this.constraint_precio_flete = CONTRAINT_PRECIO_FLETE;
	}
	
	/*
	 * Descripcion: Permitira calcular el precio de la columna especificado como parametro
	 * @param column: nro. de columna
	 * Retorno: Ninguno
	 */
	@Command
	@NotifyChange("cotizacionSelecionada")
	public void calcularPrecio(@BindingParam("tipo") int tipo){
		float total = 0;
		for(DetalleCotizacion detalle : this.listaDetalleCotizacion){
			switch(tipo){
			case 1: total+=(detalle.getPrecioVenta()!=null)?detalle.getPrecioVenta():0; break;
			case 2: total+=(detalle.getPrecioFlete()!=null)?detalle.getPrecioFlete():0; break;
			default: break;
			}
		}
		
		switch(tipo){
		case 1: this.cotizacionSelecionada.setTotalPrecioVenta(total); break;
		case 2: this.cotizacionSelecionada.setTotalFlete(total); break;
		default: break;
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
		Map<String, Object> parametros = this.sControlConfiguracion.consultarMonedasConHistorico(page, PAGE_SIZE);
		Integer total = (Integer) parametros.get("total");
		monedas = (List<Moneda>) parametros.get("monedas");
		pagMonedas.setActivePage(page);
		pagMonedas.setTotalSize(total);
		pagMonedas.setPageSize(PAGE_SIZE);
	}
	
	/*
	 * Descripcion: Permitira limpiar la informacion de la cotizacion seleccionada
	 * @param: Ninguno
	 * Retorno: Ninguno 
	 */
	private void limpiarCotizacionSeleccionada(){
		if(cotizacionSelecionada!=null){
			cotizacionSelecionada.setFechaVencimiento(AbstractServiceImpl.sumarORestarFDia(new Date(), 1));
			cotizacionSelecionada.setCondiciones(null);
		}
	}
	
	/**SETTERS Y GETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
	public SControlConfiguracion getsControlConfiguracion() {
		return sControlConfiguracion;
	}

	public void setsControlConfiguracion(SControlConfiguracion sControlConfiguracion) {
		this.sControlConfiguracion = sControlConfiguracion;
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

	public List<ModeloCombo<Boolean>> getFormasFlete() {
		return formasFlete;
	}

	public void setFormasFlete(List<ModeloCombo<Boolean>> formasFlete) {
		this.formasFlete = formasFlete;
	}

	public ModeloCombo<Boolean> getTipoFlete() {
		return tipoFlete;
	}

	public void setTipoFlete(ModeloCombo<Boolean> tipoFlete) {
		this.tipoFlete = tipoFlete;
	}

	public ModeloCombo<Boolean> getFormaFlete() {
		return formaFlete;
	}

	public void setFormaFlete(ModeloCombo<Boolean> formaFlete) {
		this.formaFlete = formaFlete;
	}
}