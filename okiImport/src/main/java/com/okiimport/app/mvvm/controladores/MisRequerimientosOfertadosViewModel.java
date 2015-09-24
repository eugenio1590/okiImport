package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;

import com.okiimport.app.model.Cliente;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.model.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.configuracion.SControlUsuario;
import com.okiimport.app.service.transaccion.STransaccion;

public class MisRequerimientosOfertadosViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent> {
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;

	//GUI
	@Wire("#gridMisRequerimientos")
	private Listbox gridMisRequerimientos;

	@Wire("#pagMisRequerimientos")
	private Paging pagMisRequerimientos;
	//Atributos	
	private List <Requerimiento> listaRequerimientos;

	private Usuario usuario;
	private Requerimiento requerimientoFiltro;

	private List<ModeloCombo<String>> listaEstatus;
	private ModeloCombo<String> estatusFiltro;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		UserDetails user = this.getUser();
		requerimientoFiltro = new Requerimiento(new Cliente());
		usuario = sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
		cambiarRequerimientos(0, null, null);
		agregarGridSort(gridMisRequerimientos);
		pagMisRequerimientos.setPageSize(pageSize);
		estatusFiltro=new ModeloCombo<String>("No Filtrar", "");
		listaEstatus = llenarListaEstatusOfertados();
		listaEstatus.add(estatusFiltro);
	}

	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaRequerimientos")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarRequerimientos", parametros );
		}

	}

	/**GLOBAL COMMAND*/
	/*
	 * Descripcion: permitira cambiar los requerimientos de la grid de acuerdo a la pagina dada como parametro
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaRequerimientos")
	public void cambiarRequerimientos(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.consultarMisRequerimientosOfertados(requerimientoFiltro, 
				fieldSort, sortDirection,usuario.getPersona().getId(), page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaRequerimientos = (List<Requerimiento>) parametros.get("requerimientos");
		pagMisRequerimientos.setActivePage(page);
		pagMisRequerimientos.setTotalSize(total);
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
		int page=pagMisRequerimientos.getActivePage();
		cambiarRequerimientos(page, null, null);
	}

	/*
	 * Descripcion: permitira filtrar los datos de la grid de acuerdo al campo establecido en el evento
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("listaRequerimientos")
	public void aplicarFiltro(){
		requerimientoFiltro.setEstatus(null);
		if(estatusFiltro!=null)
			if(!estatusFiltro.getNombre().equalsIgnoreCase("No Filtrar"))
				requerimientoFiltro.setEstatus(estatusFiltro.getValor());
		cambiarRequerimientos(0, null, null);
	}

	/*
	 * Descripcion: permitira crear el emergente (modal) necesario para consultar la informacion del requerimiento seleccionado
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 * */
	@Command
	public void verRequerimiento(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		parametros.put("editar", false);
		crearModal(BasePackageSistemaFunc+"emitidos/editarRequerimiento.zul", parametros);
	}

	/*
	 * Descripcion: permitira crear el emergente (modal) necesario para aprobar las cotizaciones del requerimiento seleccionado
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 * */
	@Command
	public void seleccionarCotizaciones(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		crearModal(BasePackageSistemaFunc+"en_proceso/aprobarCotizaciones.zul", parametros);
	}

	/*
	 * Descripcion: permitira visualizar los proveedores para luego registrar la cotizacion respectiva
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 */
	@Command
	public void cotizar(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		parametros.put("size", "90%");
		crearModal(BasePackageSistemaFunc+"en_proceso/listaProveedoresCotizar.zul", parametros );
	}

	/*
	 * Descripcion: permitira visualizar las Ofertas para luego enviar al cliente respectivo
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 */
	@Command
	public void mostrarOfertas(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		parametros.put("size", "90%");
		crearModal(BasePackageSistemaFunc+"ofertados/listaOfertasCliente.zul", parametros );
	}

	/*
	 * Descripcion: permitira visualizar la lista de compras del requerimiento seleccionado
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno ninguno
	 */
	@Command
	public void verCompras(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		crearModal(BasePackageSistemaFunc+"ofertados/listaComprasCliente.zul", parametros);
	}

	/**SETTERS Y GETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<Requerimiento> getListaRequerimientos() {
		return listaRequerimientos;
	}

	public void setListaRequerimientos(List<Requerimiento> listaRequerimientos) {
		this.listaRequerimientos = listaRequerimientos;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

	public Requerimiento getRequerimientoFiltro() {
		return requerimientoFiltro;
	}

	public void setRequerimientoFiltro(Requerimiento requerimientoFiltro) {
		this.requerimientoFiltro = requerimientoFiltro;
	}

	public List<ModeloCombo<String>> getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(List<ModeloCombo<String>> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	public ModeloCombo<String> getEstatusFiltro() {
		return estatusFiltro;
	}

	public void setEstatusFiltro(ModeloCombo<String> estatusFiltro) {
		this.estatusFiltro = estatusFiltro;
	}
}

