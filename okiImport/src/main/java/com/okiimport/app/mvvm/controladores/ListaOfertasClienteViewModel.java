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
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Configuracion;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.configuracion.SControlConfiguracion;
import com.okiimport.app.service.configuracion.SControlUsuario;
import com.okiimport.app.service.mail.MailCliente;
import com.okiimport.app.service.transaccion.STransaccion;

public class ListaOfertasClienteViewModel extends
		AbstractRequerimientoViewModel implements EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;
	
	@BeanInjector("sControlConfiguracion")
	private SControlConfiguracion sControlConfiguracion;
	
	@BeanInjector("mailCliente")
	private MailCliente mailCliente;

	// GUI
	@Wire("#gridOfertasCliente")
	private Listbox gridOfertasCliente;

	@Wire("#pagOfertasCliente")
	private Paging pagOfertasCliente;
	
	@Wire("#winListaOfertas")
	private Window winListaOfertas;

	// Atributos
	private List<Oferta> listaOfertas;
	private Requerimiento requerimiento;
	private String titulo = "Ofertas del Requerimiento N� ";

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaOfertasCliente.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento) {
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		
		this.titulo = this.titulo + requerimiento.getIdRequerimiento();
		agregarGridSort(gridOfertasCliente);
		pagOfertasCliente.setPageSize(pageSize);
		cambiarOfertas(0, null, null);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarOfertas", parametros );
		}
	}

	/**GLOBAL COMMAND*/
	/**
	 * Descripcion: Llama a consultar las ofertas por requerimiento  
	 * Parametros: @param view: listaOfertasCliente.zul 
	 * Retorno: ofertas por requerimiento consultados
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaOfertas")
	public void cambiarOfertas(@Default("0") @BindingParam("page") int page,
			@BindingParam("fieldSort") String fieldSort,
			@BindingParam("sortDirection") Boolean sortDirection) {
		Map<String, Object> parametros = sTransaccion.consultarOfertasPorRequerimiento(requerimiento.getIdRequerimiento(), fieldSort, sortDirection, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaOfertas = (List<Oferta>) parametros.get("ofertas");
		pagOfertasCliente.setActivePage(page);
		pagOfertasCliente.setTotalSize(total);
	}

	/** COMMAND */
	/**
	 * Descripcion: Permitira cambiar la paginacion de acuerdo a la pagina activa del Paging 
	 * Parametros: @param view: listaOfertasCliente.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista() {
		int page = pagOfertasCliente.getActivePage();
		cambiarOfertas(page, null, null);
	}
	
	/**
	 * Descripcion: Llama a un modal para ver los datos de la oferta
	 * Parametros: Oferta @param view: listaOfertasCliente.zul 
	 * Retorno: Modal cargado con los datos de la oferta
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void verOferta(@BindingParam("oferta") Oferta oferta){
		Map<String, Object> parametros = new HashMap<String, Object>();
		oferta.setDetalleOfertas(this.sTransaccion.consultarDetallesOferta(oferta.getIdOferta()));
		parametros.put("oferta", oferta);
		parametros.put("requerimiento", this.requerimiento);
		
		crearModal(BasePackageSistemaFunc+"ofertados/verDetalleOferta.zul", parametros);
	}

	/**
	 * Descripcion: Llama a enviar las ofertas al cliente
	 * Parametros: @param view: listaOfertasCliente.zul 
	 * Retorno: ofertas actualizadas y enviadas al cliente
	 * Nota: Ninguna
	 * */
	@Command
	public void enviarCliente(){
		Configuracion configuracion = sControlConfiguracion.consultarConfiguracionActual();
		for(Oferta oferta : listaOfertas ){
			if(oferta.getEstatus().equalsIgnoreCase("solicitado")){
				oferta.setPorctIva(configuracion.getPorctIva());
				oferta.setPorctGanancia(configuracion.getPorctGanancia());
				oferta.setEstatus("enviada");
				sTransaccion.actualizarOferta(oferta);
			}
		}
		requerimiento.setEstatus("O");
		sTransaccion.actualizarRequerimiento(requerimiento);

		//No es el servicio que se usara
		mailCliente.registrarRequerimiento(requerimiento, mailService);
		
		winListaOfertas.detach();
		
		mostrarMensaje("Informaci�n", "Ofertas Enviadas al Cliente", null, null, null, null);

	}
	
	/**
	 * Descripcion: Llama a ejecutar globalCommand
	 * Parametros: @param view: listaOfertasCliente.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void cambiarRequerimientos(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}

	
	/**
	 * Descripcion: Llama a enviar a Cliente
	 * Parametros: @param view: listaOfertasCliente.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	public boolean enviarACliente(){
		boolean enviar = false;
		for(Oferta oferta : listaOfertas)
			if(oferta.enviar()){
				enviar = true;
				break;
			}
		return enviar;
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	
	/**METODOS GETTERS AND SETTERS*/
	
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

	public MailCliente getMailCliente() {
		return mailCliente;
	}

	public void setMailCliente(MailCliente mailCliente) {
		this.mailCliente = mailCliente;
	}

	public List<Oferta> getListaOfertas() {
		return listaOfertas;
	}

	public void setListaOfertas(List<Oferta> listaOfertas) {
		this.listaOfertas = listaOfertas;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
