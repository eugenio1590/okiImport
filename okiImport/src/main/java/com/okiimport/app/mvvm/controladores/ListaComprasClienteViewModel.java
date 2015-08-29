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

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Compra;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class ListaComprasClienteViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;

	// GUI
	@Wire("#gridComprasCliente")
	private Listbox gridComprasCliente;

	@Wire("#pagComprasCliente")
	private Paging pagComprasCliente;
	
	@Wire("#winListaCompras")
	private Window winListaCompras;

	// Atributos
	private List<Compra> listaCompras;
	private Requerimiento requerimiento;
	private Compra compra;
	private String titulo = "Solicitudes de Compra del Requerimiento N� ";

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento) {
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.compra = new Compra();
		this.titulo = this.titulo + requerimiento.getIdRequerimiento();
		agregarGridSort(gridComprasCliente);
		pagComprasCliente.setPageSize(pageSize);
		cambiarCompras(0, null, null);
	}
	
	@Override
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarCompras", parametros );
		}
	}

	/**GLOBAL COMMAND*/
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaCompras")
	public void cambiarCompras(@Default("0") @BindingParam("page") int page,
			@BindingParam("fieldSort") String fieldSort,
			@BindingParam("sortDirection") Boolean sortDirection) {
		Map<String, Object> parametros = sTransaccion.consultarComprasPorRequerimiento(compra, requerimiento.getIdRequerimiento(), fieldSort, sortDirection, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaCompras = (List<Compra>) parametros.get("compras");
		pagComprasCliente.setActivePage(page);
		pagComprasCliente.setTotalSize(total);
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
		int page = pagComprasCliente.getActivePage();
		cambiarCompras(page, null, null);
	}
	
	@Command
	@NotifyChange("*")
	public void verCompra(@BindingParam("compra") Compra compra){
		
	}
	
	@Command
	public void registrarCompra(@BindingParam("compra") Compra compra){
		
	}
	
	@Command
	public void cambiarRequerimientos(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}

	@Command
	public void enviarCliente(){
//		for(Oferta oferta : listaCompras ){
//			if(oferta.getEstatus().equalsIgnoreCase("solicitado")){
//				oferta.setEstatus("enviada");
//				sTransaccion.actualizarOferta(oferta);
//			}
//		}
//		requerimiento.setEstatus("O");
//		sTransaccion.actualizarRequerimiento(requerimiento);
//		
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("nroSolicitud", requerimiento.getIdRequerimiento());
//		model.put("cliente", requerimiento.getCliente().getNombre());
//		model.put("cedula", requerimiento.getCliente().getCedula());
//
//		mailService.send(requerimiento.getCliente().getCorreo(), "Registro de Requerimiento",
//				"registrarRequerimiento.html", model);
//		winListaOfertas.detach();
//		
//		mostrarMensaje("Informaci�n", "Ofertas Enviadas al Cliente", null, null, null, null);

	}

	/**METODOS PROPIOS DE LA CLASE*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<Compra> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<Compra> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
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
