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
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.service.configuracion.SControlConfiguracion;
import com.okiimport.app.service.configuracion.SControlUsuario;
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
	private String titulo = "Ofertas del Requerimiento N° ";

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
	/*
	 * Descripcion: permitira cambiar la paginacion de acuerdo a la pagina
	 * activa del Paging
	 * 
	 * @param Ninguno Retorno: Ninguno
	 */
	@Command
	@NotifyChange("*")
	public void paginarLista() {
		int page = pagOfertasCliente.getActivePage();
		cambiarOfertas(page, null, null);
	}
	
	@Command
	@NotifyChange("*")
	public void verOferta(@BindingParam("oferta") Oferta oferta){
		Map<String, Object> parametros = new HashMap<String, Object>();
		oferta.setDetalleOfertas(this.sTransaccion.consultarDetallesOferta(oferta.getIdOferta()));
		parametros.put("oferta", oferta);
		parametros.put("requerimiento", this.requerimiento);
		
		crearModal("/WEB-INF/views/sistema/funcionalidades/verDetalleOferta.zul", parametros);
	}

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
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nroSolicitud", requerimiento.getIdRequerimiento());
		model.put("cliente", requerimiento.getCliente().getNombre());
		model.put("cedula", requerimiento.getCliente().getCedula());

		mailService.send(requerimiento.getCliente().getCorreo(), "Registro de Requerimiento",
				"registrarRequerimiento.html", model);
		winListaOfertas.detach();
		
		mostrarMensaje("Información", "Ofertas Enviadas al Cliente", null, null, null, null);

	}
	
	@Command
	public void cambiarRequerimientos(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}

	/**METODOS PROPIOS DE LA CLASE*/
	public boolean enviarACliente(){
		boolean enviar = false;
		for(Oferta oferta : listaOfertas)
			if(oferta.enviar()){
				enviar = true;
				break;
			}
		return enviar;
	}
	
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
