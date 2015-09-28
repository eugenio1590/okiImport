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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Ciudad;
import com.okiimport.app.model.ClasificacionRepuesto;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Estado;
import com.okiimport.app.model.Motor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;
import com.okiimport.app.service.transaccion.STransaccion;

public class EditarRequerimientoViewModel extends AbstractRequerimientoViewModel implements EventListener<Event>  {
	
	//Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	//GUI
	@Wire("#winERequerimiento")
	private Window winERequerimiento;
	
	@Wire("#grpDatosVehiculo")
	private Groupbox grpDatosVehiculo;
	
	@Wire("#aDatosVehiculo")
	private A aDatosVehiculo;
	
	@Wire("#cmbTransmision")
	private Combobox cmbTransmision;
	
	@Wire("#cmbTraccion")
	private Combobox cmbTraccion;
	
	@Wire("#txtTipoRepuesto")
	private Textbox txtTipoRepuesto;
	
	@Wire("#gridMotores")
	private Listbox gridMotores;
	
	@Wire("#pagMotores")
	private Paging pagMotores;
	
	//Atributos
	private List<ClasificacionRepuesto> listaClasificacionRepuesto;
	private List <Motor> listaMotor;
	private Requerimiento requerimiento;
	private Estado estado;
	private Ciudad ciudad;
	private Motor motor;
	
	private List <ModeloCombo<Boolean>> listaTraccion;
	private List <ModeloCombo<Boolean>> listaTransmision;
	private List <ModeloCombo<Boolean>> listaTipoRepuesto;
	private ModeloCombo<Boolean> traccion;
	private ModeloCombo<Boolean> transmision;
	private ModeloCombo<Boolean> tipoRepuesto;
	
	private Boolean editar;
	
	@AfterCompose
	@SuppressWarnings("unchecked")
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("estado") Estado estado,
			@ExecutionArgParam("editar") boolean editar)
	{
		super.doAfterCompose(view);
		this.editar = editar;
		this.requerimiento = requerimiento;
		this.motor = requerimiento.getMotor();
		this.estado = requerimiento.getCliente().getCiudad().getEstado();
		this.ciudad = requerimiento.getCliente().getCiudad();
		
		agregarGridSort(gridMotores);
		
		Map<String, Object> parametros = sMaestros.consultarClasificacionRepuesto(0, -1);
		listaClasificacionRepuesto = (List<ClasificacionRepuesto>) parametros.get("clasificacionRepuesto");
				
		listaTraccion = llenarListaTraccion();
		listaTransmision = llenarListaTransmision();
		listaTipoRepuesto = llenarListaTipoRepuesto();
		
		String transmision = this.requerimiento.determinarTransmision();
		if (transmision!=null)
			cmbTransmision.setValue(transmision);
		
		String traccion = this.requerimiento.determinarTraccion();
		if (traccion!=null)
			cmbTraccion.setValue(traccion);
		
		String tipoRepuesto = this.requerimiento.determinarTipoRepuesto();
		if (tipoRepuesto!=null)
			txtTipoRepuesto.setValue(tipoRepuesto);
		
		cambiarMotores(0, null, null);
	}
	
	/**INTERFACE*/
	//1.EventListener<Event>
	@Override
	public void onEvent(Event event) throws Exception {
		if(event instanceof ClickEvent){
			winERequerimiento.detach();
			ejecutarGlobalCommand("cambiarRequerimientos", null);
		}
		else if(event instanceof SortEvent){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", ((SortEvent) event).isAscending());
			ejecutarGlobalCommand("cambiarMotores", parametros );
		}
	}
	
	/**GLOBAL COMMAND*/
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaMotor")
	public void cambiarMotores(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sMaestros.consultarMotores(motor, fieldSort, sortDirection, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaMotor = (List<Motor>) parametros.get("motores");
		pagMotores.setActivePage(page);
		pagMotores.setTotalSize(total);
	}
	
	/**COMMAND*/
	/*
	 * Descripcion: Permitira abrir o cerrar la seccion del vehiculo del formulario de acuerdo parametro que se le indique
	 * @param justIcon: indicara si debe cambiarse solo el icono o tambien incluira abrir o no la seccion de vehiculo
	 * Retorno: Ninguno
	 * */
	@Command
	public void abrirDatosVehiculo(@Default("false") @BindingParam("justIcon") boolean justIcon){
		boolean open = grpDatosVehiculo.isOpen();
		if(!justIcon){
			grpDatosVehiculo.setOpen(!grpDatosVehiculo.isOpen());
			aDatosVehiculo.setIconSclass((open) ? "z-icon-plus" : "z-icon-minus");
		}
		else
			aDatosVehiculo.setIconSclass((!open) ? "z-icon-plus" : "z-icon-minus");
	}
	
	/*
	 * Descripcion: Permitira limpiar los campos de la vista del formulario
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange({"requerimiento", "traccion", "transmision"})
	public void limpiar(){
		this.requerimiento.setSerialCarroceriaV(null);
		for(DetalleRequerimiento detalle:this.requerimiento.getDetalleRequerimientos()){
			detalle.setCodigoOem("");
			detalle.setCantidad(new Long(0));
			detalle.setDescripcion("");
			detalle.setClasificacionRepuesto(null);
			detalle.setFoto(null);
		}
	}
	
	/*
	 * Descripcion: Permitira actualizar la informacion del requerimiento
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	public void actualizar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar){
		if(checkIsFormValid()){
			btnEnviar.setDisabled(true);
			btnLimpiar.setDisabled(true);
			if(traccion!=null)
				requerimiento.setTraccionV(traccion.getValor());
			if(transmision!=null)
				requerimiento.setTransmisionV(transmision.getValor());
			if(tipoRepuesto!=null)
				requerimiento.setTipoRepuesto(tipoRepuesto.getValor());
			requerimiento.setEstatus("E");
			sTransaccion.actualizarRequerimiento(requerimiento);
			mostrarMensaje("Informacion", "Requerimiento Actualizado Exitosamente", null, null, this, null);
		}
	}
	
	@Command
	@NotifyChange("listaMotor")
	public void paginarListaMotores(){
		int page=pagMotores.getActivePage();
		cambiarMotores(page, null, null);
	}

	@Command
	@NotifyChange("listaMotor")
	public void aplicarFiltroMotor(){
		cambiarMotores(0, null, null);
	}
	
	/**SETTERS Y GETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}
	
	public List<ClasificacionRepuesto> getListaClasificacionRepuesto() {
		return listaClasificacionRepuesto;
	}

	public void setListaClasificacionRepuesto(
			List<ClasificacionRepuesto> listaClasificacionRepuesto) {
		this.listaClasificacionRepuesto = listaClasificacionRepuesto;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}
	
	public List<ModeloCombo<Boolean>> getListaTraccion() {
		return listaTraccion;
	}

	public void setListaTraccion(List<ModeloCombo<Boolean>> listaTraccion) {
		this.listaTraccion = listaTraccion;
	}

	public List<ModeloCombo<Boolean>> getListaTransmision() {
		return listaTransmision;
	}

	public void setListaTransmision(List<ModeloCombo<Boolean>> listaTransmision) {
		this.listaTransmision = listaTransmision;
	}

	public ModeloCombo<Boolean> getTraccion() {
		return traccion;
	}

	public void setTraccion(ModeloCombo<Boolean> traccion) {
		this.traccion = traccion;
	}

	public ModeloCombo<Boolean> getTransmision() {
		return transmision;
	}

	public void setTransmision(ModeloCombo<Boolean> transmision) {
		this.transmision = transmision;
	}

	public List<Motor> getListaMotor() {
		return listaMotor;
	}

	public void setListaMotor(List<Motor> listaMotor) {
		this.listaMotor = listaMotor;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<ModeloCombo<Boolean>> getListaTipoRepuesto() {
		return listaTipoRepuesto;
	}

	public void setListaTipoRepuesto(List<ModeloCombo<Boolean>> listaTipoRepuesto) {
		this.listaTipoRepuesto = listaTipoRepuesto;
	}

	public ModeloCombo<Boolean> getTipoRepuesto() {
		return tipoRepuesto;
	}

	public void setTipoRepuesto(ModeloCombo<Boolean> tipoRepuesto) {
		this.tipoRepuesto = tipoRepuesto;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Motor getMotor() {
		return motor;
	}

	public void setMotor(Motor motor) {
		this.motor = motor;
	}
	
}
