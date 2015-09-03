package com.okiimport.app.mvvm.controladores;

import java.sql.Timestamp;
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
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;

import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.modelo.Oferta;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class VerificarRequerimientosViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent> {


	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	private List <Requerimiento> listaRequerimientos;

	//GUI
	@Wire("#gridRequerimientosCliente")
	private Listbox gridRequerimientosCliente;

	@Wire("#pagRequerimientosCliente")
	private Paging pagRequerimientosCliente;

	@Wire("#misolicitudes")
	private Div misolicitudes;
	
	//Atributos

	private Date fechaCreacion;

	private Cliente cliente;

	private Requerimiento requerimientoFiltro;

	private List <ModeloCombo<Boolean>> listaTipoPersona;

	private ModeloCombo<Boolean> tipoPersona;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		super.doAfterCompose(view);
		cliente = new Cliente();
		requerimientoFiltro = new Requerimiento();
		pagRequerimientosCliente.setPageSize(pageSize);
		agregarGridSort(gridRequerimientosCliente);
		listaTipoPersona = llenarListaTipoPersona();
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
	@GlobalCommand
	@NotifyChange("listaRequerimientos")
	public void cambiarRequerimientos(@Default("0") @BindingParam("page") int page, 
			@BindingParam("cedula") String cedula,
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.ConsultarRequerimientosCliente(requerimientoFiltro,fieldSort, sortDirection, cedula, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaRequerimientos = (List<Requerimiento>) parametros.get("requerimientos");
		gridRequerimientosCliente.setMultiple(true);
		gridRequerimientosCliente.setCheckmark(true);
		pagRequerimientosCliente.setActivePage(page);
		pagRequerimientosCliente.setTotalSize(total);
	}
	
	@GlobalCommand
	public void verOferta(@BindingParam("requerimiento") Requerimiento requerimiento,
			@BindingParam("detallesOfertas") List<DetalleOferta> detallesOfertas){

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		parametros.put("detallesOfertas", detallesOfertas);
		llamarFormulario("formularioOferta.zul", parametros);

	}
	
	

	// Comand 

	@Command
	@NotifyChange("listaRequerimientos")
	public void buscarCliente(){
		String cedula = obtenerCedulaConTipoPersona();
		if(cedula!=null){
			cambiarRequerimientos(0, cedula, null, null);
			if (listaRequerimientos.size() > 0 )
				misolicitudes.setVisible(true);
			else
			{
				misolicitudes.setVisible(false);
				mostrarMensaje("Informacion Importante","No posee Solicitudes", null, null, null, null);
			}
		}
	}

	@Command
	public void verDetalleRequerimiento(@BindingParam("requerimiento") Requerimiento requerimiento){

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		llamarFormulario("verDetalleRequerimiento.zul", parametros);

	}
	
	
	
	

	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagRequerimientosCliente.getActivePage();
		String cedula = obtenerCedulaConTipoPersona();
		if(cedula!=null)
			cambiarRequerimientos(page, cedula, null, null);
	} 

	@Command
	@NotifyChange("*")
	public void aplicarFiltro()
	{
		if(fechaCreacion!=null)
			this.requerimientoFiltro.setFechaCreacion(new Timestamp(fechaCreacion.getTime()));
		else
			this.requerimientoFiltro.setFechaCreacion(null);
		String cedula = obtenerCedulaConTipoPersona();
		if(cedula!=null)
			cambiarRequerimientos(0, cedula, null, null);
	}

	/**METODOS PROPIOS DE LA CLASE*/
	private String obtenerCedulaConTipoPersona(){
		String cedula = null;
		if(checkIsFormValid()){
			String tipo = (this.tipoPersona.getValor())?"J":"V";
			cedula = tipo+cliente.getCedula();
		}
		return cedula;
	}
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/"+ruta, parametros);
	}


	/**GETTERS Y SETTERS*/
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
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Requerimiento getRequerimientoFiltro() {
		return requerimientoFiltro;
	}

	public void setRequerimientoFiltro(Requerimiento requerimientoFiltro) {
		this.requerimientoFiltro = requerimientoFiltro;
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}