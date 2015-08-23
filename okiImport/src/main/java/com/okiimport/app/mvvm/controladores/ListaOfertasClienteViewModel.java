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
import com.okiimport.app.modelo.Oferta;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class ListaOfertasClienteViewModel extends
		AbstractRequerimientoViewModel implements EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;

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

	private Oferta ofertaFiltro;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento) {
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		
		this.titulo = this.titulo + requerimiento.getIdRequerimiento();
		ofertaFiltro = new Oferta();
		agregarGridSort(gridOfertasCliente);
		pagOfertasCliente.setPageSize(pageSize);
		cambiarOfertas(0, null, null);
	}

	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaOfertas")
	public void cambiarOfertas(@Default("0") @BindingParam("page") int page,
			@BindingParam("fieldSort") String fieldSort,
			@BindingParam("sortDirection") Boolean sortDirection) {
		Map<String, Object> parametros = sTransaccion
				.consultarOfertasPorRequerimiento(
						requerimiento.getIdRequerimiento(), fieldSort,
						sortDirection, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaOfertas = (List<Oferta>) parametros.get("ofertas");
		pagOfertasCliente.setActivePage(page);
		pagOfertasCliente.setTotalSize(total);
	}
	
	@Command
	public void enviarCliente()
			{
				for(Oferta oferta : listaOfertas ){
				oferta.setEstatus("enviada");
				sTransaccion.actualizarOferta(oferta);
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
				mostrarMensaje("Informaci�n", "Ofertas Enviadas al Cliente", null, null, null, null);

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

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public Oferta getOfertaFiltro() {
		return ofertaFiltro;
	}

	public void setOfertaFiltro(Oferta ofertaFiltro) {
		this.ofertaFiltro = ofertaFiltro;
	}

	public List<Oferta> getListaOfertas() {
		return listaOfertas;
	}

	public void setListaOfertas(List<Oferta> listaOfertas) {
		this.listaOfertas = listaOfertas;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	

}