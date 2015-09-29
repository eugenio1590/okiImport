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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Ciudad;
import com.okiimport.app.model.ClasificacionRepuesto;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;
import com.okiimport.app.service.transaccion.STransaccion;

public class EnviarRequerimientoProvViewModel extends AbstractRequerimientoViewModel implements EventListener<ClickEvent>  {
	
	//Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	//GUI
	@Wire("#winEnviarReqProv")
	private Window winEnviarReqProv;
	
	@Wire("#grpDatosVehiculo")
	private Groupbox grpDatosVehiculo;
	
	@Wire("#aDatosVehiculo")
	private A aDatosVehiculo; 
	
	@Wire("#txtTipoRepuesto")
	private Textbox txtTipoRepuesto;
	
	
	
	//Atributos
	private List<ClasificacionRepuesto> listaClasificacionRepuesto;
	private List <DetalleRequerimiento> listaDetalleRequerimientoSeleccionados;
	private Requerimiento requerimiento;
	private List <ModeloCombo<Boolean>> listaTraccion;
	private List <ModeloCombo<Boolean>> listaTransmision;
	private List <ModeloCombo<Boolean>> listaTipoRepuesto;
	private ModeloCombo<Boolean> traccion;
	private ModeloCombo<Boolean> transmision;
	private Ciudad ciudad;
	
	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: enviarRequerimientoProv.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	@SuppressWarnings("unchecked")
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento)
	{
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.ciudad = requerimiento.getCliente().getCiudad();
		Map<String, Object> parametros = sMaestros.consultarClasificacionRepuesto(0, -1);
		listaClasificacionRepuesto = (List<ClasificacionRepuesto>) parametros.get("clasificacionRepuesto");
		listaTraccion = llenarListaTraccion();
		listaTransmision = llenarListaTransmision();
		listaTipoRepuesto = llenarListaTipoRepuesto();
		
		String tipoRepuesto = this.requerimiento.determinarTipoRepuesto();
		if (tipoRepuesto!=null)
			txtTipoRepuesto.setValue(tipoRepuesto);
		
	}
	
	/**INTERFACE*/
	
	@Override
	public void onEvent(ClickEvent event) throws Exception {
		winEnviarReqProv.detach();
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}
	
	/**GLOBAL COMMAND*/
	/**
	 * Descripcion: Llama a remover los items seleccionados
	 * Parametros: @param view: enviarRequerimientoProv.zul 
	 * Retorno: Items seleccionados removidos 
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@NotifyChange("listaDetalleRequerimientoSeleccionados")
	public void removerSeleccionados(){
		if(listaDetalleRequerimientoSeleccionados!=null)
			listaDetalleRequerimientoSeleccionados.clear();
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: Permitira abrir o cerrar la seccion del vehiculo del formulario de acuerdo parametro que se le indique 
	 * Parametros: @param view: enviarRequerimientoProv.zul 
	 * @param justIcon: indicara si debe cambiarse solo el icono o tambien incluira abrir o no la seccion de vehiculo
	 * Retorno: seccion abierta o cerrada
	 * Nota: Ninguna
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
	
	/**
	 * Descripcion: Permitira actualizar la informacion del requerimiento 
	 * Parametros: @param view: enviarRequerimientoProv.zul
	 * Retorno: informacion del requerimiento actualizada 
	 * Nota: Ninguna
	 * */
	@Command
	public void actualizar(){
		if(checkIsFormValid()){
			if(traccion!=null)
				requerimiento.setTraccionV(traccion.getValor());
			if(transmision!=null)
				requerimiento.setTransmisionV(transmision.getValor());
			requerimiento.setEstatus("E");
			sTransaccion.actualizarRequerimiento(requerimiento);
			
		}
	}

	/**
	 * Descripcion: Permitira enviar la solicitud de cotizacion al proveedor 
	 * Parametros: requerimiento @param view: enviarRequerimientoProv.zul
	 * Retorno: solicitud de cotizacion enviada al proveedor
	 * Nota: Ninguna
	 * */
	@Command
	public void enviarSolicitudProv(
			@Default("false") @BindingParam("enviar") Boolean enviar,
			@BindingParam("requerimiento") Requerimiento requerimiento){
		if(listaDetalleRequerimientoSeleccionados!= null && this.listaDetalleRequerimientoSeleccionados.size()>0){
			if (validarListaClasificacion()==true){
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("enviar", enviar);
				parametros.put("requerimiento", requerimiento);
				parametros.put("repuestosseleccionados", listaDetalleRequerimientoSeleccionados);
				crearModal(BasePackageSistemaFunc+"emitidos/seleccionarProveedores.zul", parametros);
			}
			else
				mostrarMensaje("Información", "Seleccione una clasificacion para los repuestos seleccionados", 
						null, null, null, null);
		} 
		else
			mostrarMensaje("Información", "Seleccione al menos un Repuesto", null, null, null, null);
	}
	
	/**
	 * Descripcion: Llama a actualizar los requerimientos
	 * Parametros: @param view: enviarRequerimientoProv.zul
	 * Retorno: requerimientos actualizados
	 * Nota: Ninguna
	 * */
	@Command
	public void actualizarRequerimientos(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}
	
	/**
	 * Descripcion: Permite validar al proveedor segun la clasificacion del repuesto
	 * Parametros: @param view: enviarRequerimientoProv.zul
	 * Retorno: proveedores validados segun la clasificacion ddel repuesto
	 * Nota: Ninguna
	 * */
	@Command
	public boolean validarListaClasificacion(){
		
		if (listaDetalleRequerimientoSeleccionados!= null)
		{
			for( DetalleRequerimiento detalleReq: listaDetalleRequerimientoSeleccionados)
			{
				if ( detalleReq.getClasificacionRepuesto()==null)
				     return false;
			}
		}
		
			return true;
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	
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

	public void setListaDetalleRequerimientoSeleccionados(
			List<DetalleRequerimiento> listaDetalleRequerimientoSeleccionados) {
		this.listaDetalleRequerimientoSeleccionados = listaDetalleRequerimientoSeleccionados;
	}

	public List<DetalleRequerimiento> getListaDetalleRequerimientoSeleccionados() {
		return listaDetalleRequerimientoSeleccionados;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public List<ModeloCombo<Boolean>> getListaTipoRepuesto() {
		return listaTipoRepuesto;
	}

	public void setListaTipoRepuesto(List<ModeloCombo<Boolean>> listaTipoRepuesto) {
		this.listaTipoRepuesto = listaTipoRepuesto;
	}

	
}
