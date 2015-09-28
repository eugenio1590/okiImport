package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.East;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.configuracion.servicios.SControlConfiguracion;
import com.okiimport.app.modelo.Configuracion;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleCotizacionInternacional;
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
	
	@Wire("#txtPrecioFlete")
	private Decimalbox txtPrecioFlete;
	
	//Atributos
	private static final String TITULO_EAST = "Cotizacion ";
	private static final String CONTRAINT_PRECIO_FLETE = "no empty, no zero, no negative";
	private static final String TITULO_BASE = "Solicitudes de Cotizacion del Requerimiento N� ";
	private List<DetalleCotizacionInternacional> listaDetalleCotizacion;
	private List<Moneda> monedas;
	private Requerimiento requerimiento;
	private Cotizacion cotizacionSelecionada=null;
	private Moneda monedaSeleccionada;
	private List<ModeloCombo<Boolean>> tiposFlete;
	private List<ModeloCombo<Boolean>> formasEnvio;
	private ModeloCombo<Boolean> tipoFlete;
	private ModeloCombo<Boolean> formaEnvio;
	private String titulo;
	private String constraintCampoObligatorio;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("cotizacion") Cotizacion cotizacion,
			@ExecutionArgParam("obligatorioTodosCampos") Boolean obligatorio){
		super.doAfterCompose(view);
		
		this.constraintCampoObligatorio = (obligatorio) ? "no empty" : null;
				
		this.requerimiento = requerimiento;
		this.cotizacionSelecionada = cotizacion;
		titulo = TITULO_BASE + requerimiento.getIdRequerimiento();
		cambiarMonedas(0);
		eastCotizacion.setTitle(TITULO_EAST+"N� "+cotizacionSelecionada.getIdCotizacion());	
		
		Map<String, Object> parametros = sTransaccion.consultarDetallesCotizacion(null, (int) cotizacion.getIdCotizacion(), 
				null, null, 0, -1);
		listaDetalleCotizacion = (List<DetalleCotizacionInternacional>) parametros.get("detallesCotizacion");
		prepararListaDetalleCotizacion();
		limpiarCotizacionSeleccionada();
		
		formasEnvio = llenarFormasDeEnvio();
		formaEnvio = formasEnvio.get(0);
		
		tiposFlete = llenarTiposFleteInternacional();
		tipoFlete = tiposFlete.get(0);
		
		seleccionarTipoFlete();
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
	/**
	 * Descripcion: Limpia los campos editable de la grid de detalles de la cotizacion seleccionada 
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Campos Limpios
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion", "cotizacionSelecionada"})
	public void limpiar(){
		limpiarCotizacionSeleccionada();
		if(listaDetalleCotizacion!=null)
			for(DetalleCotizacionInternacional detalle : listaDetalleCotizacion){
				detalle.setMarcaRepuesto(null);
				detalle.setCantidad(null);
				detalle.setPrecioVenta(null);
				detalle.setPrecioFlete(null);
				detalle.setAlto(null);
				detalle.setAncho(null);
				detalle.setLargo(null);
				detalle.setPeso(null);
			}
	}
	
	/**
	 * Descripcion: Envia los datos de la cotizacion seleccionada para su registro 
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: datos de la cotizacion enviada
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void enviar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar){
		if(checkIsFormValid()){
			Boolean tipoFlete = this.tipoFlete.getValor();
			boolean incompleto = false;
			List<DetalleCotizacion> detallesCotizacion = new ArrayList<DetalleCotizacion>();
			for(DetalleCotizacionInternacional detalle : listaDetalleCotizacion){
				detalle.setFormaEnvio(this.formaEnvio.getValor());
				detalle.setTipoFlete(tipoFlete);
				detallesCotizacion.add(detalle);
				boolean cond = (detalle.verificarCondFlete() && detalle.verificarCondPeso());
				if(!cond)
					incompleto = !cond;
			}
			if(tipoFlete!=null && !tipoFlete)
				cotizacionSelecionada.setEstatus((incompleto) ? "EC" : "C");
			else
				cotizacionSelecionada.setEstatus("C");
			cotizacionSelecionada.setDetalleCotizacions(detallesCotizacion);
			sTransaccion.registrarCotizacion(cotizacionSelecionada, requerimiento);
			this.mostrarMensaje("Informacion", "Registro Exitoso de Cotizacion", null, null, this, null);
		}
	}
	
	/**
	 * Descripcion: Asigna el historial mas actual de la moneda seleccionada
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"cotizacionSelecionada", "listaDetalleCotizacion"})
	public void seleccionMoneda(){
		bandbMoneda.close();
		if(this.cotizacionSelecionada!=null){
			HistoricoMoneda historico = this.sControlConfiguracion.consultarActualConversion(monedaSeleccionada);
			this.cotizacionSelecionada.setHistoricoMoneda(historico);
			
			actualizarListaDetalleCotizacion();
		}
	}
	
	/**
	 * Descripcion: Cambia la paginacion de acuerdo a la pagina activa
	 * de Paging 
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Posicionamiento en otra pagina activa del paging 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarListaMonedas(){
		int page=pagMonedas.getActivePage();
		cambiarMonedas(page);
	}
	
	/**
	 * Descripcion: Especifica el tipo de flete que se se ha seleccionado y agregar el constraint correspondiente 
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Tipo de flete especificado 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion", "cotizacionSelecionada", "constraint_precio_flete"})
	public void seleccionarTipoFlete(){
		this.txtPrecioFlete.clearErrorMessage();
		if(this.tipoFlete.getValor()){
			for(DetalleCotizacionInternacional detalle : this.listaDetalleCotizacion){
				detalle.setPrecioFlete(null);
				detalle.setAlto(null);
				detalle.setAncho(null);
				detalle.setLargo(null);
				detalle.setPeso(null);
			}
			this.txtPrecioFlete.setConstraint(CONTRAINT_PRECIO_FLETE);
		}
		else {
			this.txtPrecioFlete.setConstraint("");
			actualizarListaDetalleCotizacion();
		}
	}
	
	/**
	 * Descripcion: Calcula el precio de la columna especificado como parametro
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Precio Calculado
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void calcularPrecio(@BindingParam("tipo") int tipo){
		float total = 0;
		for(DetalleCotizacionInternacional detalle : this.listaDetalleCotizacion){
			switch(tipo){
			case 1: total+=(detalle.getPrecioVenta()!=null)?detalle.getPrecioVenta():0; break;
			case 2: total+=(detalle.getPrecioFlete()!=null)?detalle.getPrecioFlete():0; break;
			case 3: total+=(detalle.getPrecioTotal()!=null)?detalle.getPrecioTotal():0; break;
			default: break;
			}
		}
		
		switch(tipo){
		case 1: this.cotizacionSelecionada.setTotalPrecioVenta(total); break;
		case 2: this.cotizacionSelecionada.setTotalFlete(total); break;
		case 3: this.cotizacionSelecionada.setTotalFleteCalculado(total); break;
		default: break;
		}
	}
	
	/**
	 * Descripcion: Calcula el total del flete por medio de las dimensiones del producto
	 * Parametros: detalleCotizacion objeto seleccionado @param view: cotizarProveedorInternacional.zul 
	 * Retorno: total del flete Calculado
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion", "cotizacionSelecionada"})
	public void calcularTotalFlete(@BindingParam("detalleCotizacion") DetalleCotizacionInternacional detalleCotizacion){
		if(detalleCotizacion!=null){
			detalleCotizacion.setCotizacion(cotizacionSelecionada);
			detalleCotizacion.setTipoFlete(tipoFlete.getValor());
			detalleCotizacion.setFormaEnvio(formaEnvio.getValor());
			detalleCotizacion.calcularTotal();
			calcularPrecio(3);
		}
	}
	
	/**
	 * Descripcion: Carga nuevamente las listas al cerrar la pantalla
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: listas cargadas
	 * Nota: Ninguna
	 * */
	@Command
	public void onCloseWindow(){
		ejecutarGlobalCommand("cambiarCotizaciones", null);
	}
	
	
	/**
	 * Descripcion: Carga la lista de monedas de acuerdo a la pagina dada como parametro
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * Retorno: listas de monedas cargadas
	 * Nota: Ninguna
	 * */
	@SuppressWarnings("unchecked")
	@NotifyChange("monedas")
	private void cambiarMonedas(@Default("0") @BindingParam("page") int page){
		Map<String, Object> parametros = this.sControlConfiguracion.consultarMonedasConHistorico(page, pageSize);
		Integer total = (Integer) parametros.get("total");
		monedas = (List<Moneda>) parametros.get("monedas");
		pagMonedas.setActivePage(page);
		pagMonedas.setTotalSize(total);
		pagMonedas.setPageSize(pageSize);
	}
	
	/**
	 * Descripcion: Limpia la informacion de la cotizacion seleccionada
	 * Parametros: Cotizacion Seleccionada @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Campos Limpiados
	 * Nota: Ninguna
	 * */
	@NotifyChange("cotizacionSelecionada")
	private void limpiarCotizacionSeleccionada(){
		if(cotizacionSelecionada!=null){
			cotizacionSelecionada.setFechaVencimiento(AbstractServiceImpl.sumarORestarFDia(new Date(), 1));
			cotizacionSelecionada.setCondiciones(null);
		}
	}
	
	/**
	 * Descripcion: Actualiza la lista de detalles de cotizacion calculando su precio de flete
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Lista Actualizada
	 * Nota: Ninguna
	 * */
	@NotifyChange({"listaDetalleCotizacion", "cotizacionSelecionada"})
	private void actualizarListaDetalleCotizacion(){
		for(DetalleCotizacionInternacional detalle : this.listaDetalleCotizacion)
			calcularTotalFlete(detalle);
	}
	
	/**
	 * Descripcion: Asigna la configuracion actual de la libra a los detalles de cotizacion
	 * Parametros: @param view: cotizarProveedorInternacional.zul 
	 * Retorno: Configuracion asignada
	 * Nota: Ninguna
	 * */
	private void prepararListaDetalleCotizacion(){
		Configuracion configuracion = sControlConfiguracion.consultarConfiguracionActual();
		Float valorLibra = configuracion.getValorLibra();
		for(DetalleCotizacionInternacional detalle : listaDetalleCotizacion)
			detalle.setValorLibra(valorLibra);
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
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

	public List<DetalleCotizacionInternacional> getListaDetalleCotizacion() {
		return listaDetalleCotizacion;
	}

	public void setListaDetalleCotizacion(
			List<DetalleCotizacionInternacional> listaDetalleCotizacion) {
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

	public List<ModeloCombo<Boolean>> getTiposFlete() {
		return tiposFlete;
	}

	public void setTiposFlete(List<ModeloCombo<Boolean>> tiposFlete) {
		this.tiposFlete = tiposFlete;
	}

	public List<ModeloCombo<Boolean>> getFormasEnvio() {
		return formasEnvio;
	}

	public void setFormasEnvio(List<ModeloCombo<Boolean>> formasEnvio) {
		this.formasEnvio = formasEnvio;
	}

	public ModeloCombo<Boolean> getTipoFlete() {
		return tipoFlete;
	}

	public void setTipoFlete(ModeloCombo<Boolean> tipoFlete) {
		this.tipoFlete = tipoFlete;
	}

	public ModeloCombo<Boolean> getFormaEnvio() {
		return formaEnvio;
	}

	public void setFormaEnvio(ModeloCombo<Boolean> formaEnvio) {
		this.formaEnvio = formaEnvio;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConstraintCampoObligatorio() {
		return constraintCampoObligatorio;
	}

	public void setConstraintCampoObligatorio(String constraintCampoObligatorio) {
		this.constraintCampoObligatorio = constraintCampoObligatorio;
	}
}