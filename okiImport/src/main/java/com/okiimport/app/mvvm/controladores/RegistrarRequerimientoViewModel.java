package com.okiimport.app.mvvm.controladores;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Motor;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class RegistrarRequerimientoViewModel extends AbstractRequerimientoViewModel {

	
	private Requerimiento requerimiento;
	private Cliente cliente;
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@Wire("#annoV")
	private Datebox annoV;
	@Wire("#comboTipoPersona")
	private Combobox comboTipoPersona;
	

	private List <MarcaVehiculo> listaMarcasVehiculo;
	private List <Motor> listaMotor;
	private List <ModeloCombo<Boolean>> listaTraccion;
	private List <ModeloCombo<Boolean>> listaTransmision;
	private List <ModeloCombo<Boolean>> listaTipoPersona;
	
	private ModeloCombo<Boolean> traccion;
	private ModeloCombo<Boolean> transmision;
	private List <DetalleRequerimiento> eliminarDetalle;
	private ModeloCombo<Boolean> tipoPersona;
	

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		limpiar();
		listaMarcasVehiculo = (List<MarcaVehiculo>) sMaestros.ConsultarMarca(0, -1).get("marcas");
		listaMotor = (List<Motor>) sMaestros.ConsultarMotor(0, -1).get("motor");
		listaTraccion = llenarListaTraccion();
		listaTransmision = llenarListaTransmision();
		listaTipoPersona = llenarListaTipoPersona();
		this.tipoPersona = listaTipoPersona.get(1);
	}

	@Command
	@NotifyChange({"requerimiento","cliente"})
	public void limpiar(){
		requerimiento = new Requerimiento();
		cliente = new Cliente();
		requerimiento.setCliente(cliente);
		annoV.setValue(null);
		
	}
	
	@Command
	@NotifyChange({"requerimiento","cliente"})
	public void registrar(){
		if(checkIsFormValid()){
			String tipo = (this.tipoPersona.getValor())?"J":"V";
			cliente.setCedula(tipo+"-"+cliente.getCedula());
			cliente = sMaestros.registrarOActualizarCliente(cliente);
			requerimiento.setCliente(cliente);
			if(traccion!=null)
				requerimiento.setTraccionV(traccion.getValor());
			if(transmision!=null)
				requerimiento.setTransmisionV(transmision.getValor());
			if (requerimiento.getDetalleRequerimientos().size() > 0){
				sTransaccion.registrarRequerimiento(requerimiento, sMaestros);

				String str = "El Requerimiento ha sido registrado existosamente ";

				Messagebox.show(str, "Informacion", Messagebox.OK,
						Messagebox.INFORMATION, new EventListener() {
					public void onEvent(Event event) throws Exception {
						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							recargar();
						}
					}
				});
			}
			else mostrarMensaje("Información", "Agregue al Menos un Requerimiento", null, null, null, null);
		}
		
	}
	
	@Command
	@NotifyChange({"requerimiento","cliente"})
	public void agregarRepuesto(){
		if (requerimiento.getDetalleRequerimientos().size() < 10)
		requerimiento.addDetalleRequerimiento(new DetalleRequerimiento());
	}
	
	@Command
	@NotifyChange({"requerimiento","cliente"})
	public void eliminarRepuesto(){
		if (eliminarDetalle != null){
			for (DetalleRequerimiento detalle:eliminarDetalle)
				requerimiento.removeDetalleRequerimiento(detalle);
		}
		
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}
	
	public List<MarcaVehiculo> getListaMarcasVehiculo() {
		return listaMarcasVehiculo;
	}
	
	public List<Motor> getListaMotor() {
		return listaMotor;
	}


	public void setListaMarcasVehiculo(List<MarcaVehiculo> listaMarcasVehiculo) {
		this.listaMarcasVehiculo = listaMarcasVehiculo;
	}
	
	public void setListaMotor(List<Motor> listaMotor) {
		this.listaMotor = listaMotor;
	}
	
	
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
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
	
	public void recargar() {
		redireccionar("/");
	}
	
	public List<DetalleRequerimiento> getEliminarDetalle() {
		return eliminarDetalle;
	}

	public void setEliminarDetalle(List<DetalleRequerimiento> eliminarDetalle) {
		this.eliminarDetalle = eliminarDetalle;
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
	
	
	@Command
	@NotifyChange("*")
	public void cambiarFoto(@BindingParam("media") Media media, @BindingParam("detalle") DetalleRequerimiento detalle){
		if (media instanceof org.zkoss.image.Image)
			detalle.setFoto(((org.zkoss.image.Image) media).getByteData());			
		else if (media != null)
			mostrarMensaje("Error", "No es una imagen: " + media, null, null, null, null);
	}
	

}
