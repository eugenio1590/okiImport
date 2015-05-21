package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.ClasificacionRepuesto;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class SeleccionarProveedoresViewModel extends AbstractViewModel implements EventListener<ClickEvent> {
	
	//Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	//GUI
	@Wire("#winListProveedores")
	private Window winListProveedores;
	
	@Wire("#pagProveedores")
	private Paging pagProveedores;
	
	private Proveedor proveedor;
	private Cotizacion cotizacion;
	private DetalleRequerimiento detalleRequerimiento;
	private DetalleCotizacion detalleCotizacion;
	
	private List<Proveedor> listaProveedores;
	private List<Proveedor> proveedoresSeleccionados;
	private List<Proveedor> listaProveedoresSeleccionados1;
	private List<Proveedor> listaProveedoresSeleccionados2;
	private List<DetalleRequerimiento> listaDetalleRequerimientos;
	
	private static Integer PAGE_SIZE = 3;
	
	//*******parte de fase 2
	private List <Integer> idsClasificacionRepuesto;
	
	@Wire("#gridProveedores")
	private Listbox gridProveedores;
	@Wire("#gridProveedoresSeleccionados")
	private Listbox gridProveedoresSeleccionados;
	
	

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("repuestosseleccionados") List <DetalleRequerimiento> repuestosseleccionados){
		this.cotizacion = new Cotizacion("Estimado Proveedor le hacemos el envio de un nuevo requerimiento para su posterior revisión ");
		this.listaDetalleRequerimientos = repuestosseleccionados;
		listaProveedoresSeleccionados1 = new ArrayList<Proveedor>(); 
		super.doAfterCompose(view);
		limpiar();
		pagProveedores.setPageSize(PAGE_SIZE);
	
		idsClasificacionRepuesto = new ArrayList<Integer>();
		for(DetalleRequerimiento detalle:repuestosseleccionados)
			idsClasificacionRepuesto.add(detalle.getClasificacionRepuesto().getIdClasificacionRepuesto());
		    consultarProveedores(0);
	}
	
	/**Interface*/
	@Override
	public void onEvent(ClickEvent event) throws Exception {
		// TODO Auto-generated method stub
		winListProveedores.detach();
		ejecutarGlobalCommand("removerSeleccionados", null);
	}
	
	@Command
	@NotifyChange({"proveedor"})
	public void limpiar(){
		proveedor = new Proveedor();	
	}


	@NotifyChange({"*"})
	@Command
	public void agregarProveedores(){
		super.moveSelection(listaProveedores,listaProveedoresSeleccionados1, proveedoresSeleccionados, "No se puede agregar Proveedor");
		
	}
	

	@NotifyChange({"*"})
	@Command
	public void eliminarProveedores(){
		this.moveSelection( listaProveedoresSeleccionados1, listaProveedores, listaProveedoresSeleccionados2, "No se puede eliminar el Proveedor");
	}
	

	@NotifyChange({"*"})
	@Command
	public void paginarLista(@BindingParam("tipo")int tipo){
		switch(tipo){
		case 1: consultarProveedores(pagProveedores.getActivePage());
		break;
		}
	}
	
	
	public void recargar() {
		redireccionar("/");
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
	
	@NotifyChange({"listaProveedores"})
	private void consultarProveedores(int page){
		Map<String, Object> Parametros= sMaestros.ConsultarProveedoresListaClasificacionRepuesto(null, null, null, idsClasificacionRepuesto,page, PAGE_SIZE);
		listaProveedores = (List<Proveedor>) Parametros.get("proveedores");
		Integer total = (Integer) Parametros.get("total");
		gridProveedores.setMultiple(true);
		gridProveedores.setCheckmark(true);
		gridProveedoresSeleccionados.setMultiple(true);
		gridProveedoresSeleccionados.setCheckmark(true);
		pagProveedores.setActivePage(page);
		pagProveedores.setTotalSize(total);
	}
	
	
	@Command
	@NotifyChange({"listaProveedoresSeleccionados1"})
	public void enviar(){
		if(!listaProveedoresSeleccionados1.isEmpty()){
			for(Proveedor proveedor:listaProveedoresSeleccionados1){
				cotizacion.setProveedor(proveedor);
				List<DetalleCotizacion> detalleCotizacions = new ArrayList<DetalleCotizacion>();

				for(DetalleRequerimiento detalleRequerimiento:listaDetalleRequerimientos){
					DetalleCotizacion detalleCotizacion = new DetalleCotizacion();
					detalleCotizacion.setDetalleRequerimiento(detalleRequerimiento);
					detalleCotizacions.add(detalleCotizacion);
				}
				sTransaccion.registrarSolicitudCotizacion(cotizacion, detalleCotizacions);
			}
			mostrarMensaje("Informacion", "Cotizacion enviada Exitosamente ", null, null, this, null);
		}
		else
			mostrarMensaje("Informacion", "Seleccione al menos un Proveedor ", null, null, null, null);
	}

	
	
	public List<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}


	public List<Proveedor> getProveedoresSeleccionados() {
		return proveedoresSeleccionados;
	}

	public void setProveedoresSeleccionados(List<Proveedor> proveedoresSeleccionados) {
		this.proveedoresSeleccionados = proveedoresSeleccionados;
	}

	public List<Integer> getIdsClasificacionRepuesto() {
		return idsClasificacionRepuesto;
	}

	public void setIdsClasificacionRepuesto(List<Integer> idsClasificacionRepuesto) {
		this.idsClasificacionRepuesto = idsClasificacionRepuesto;
	}

	public List<Proveedor> getListaProveedoresSeleccionados1() {
		return listaProveedoresSeleccionados1;
	}

	public void setListaProveedoresSeleccionados1(
			List<Proveedor> listaProveedoresSeleccionados1) {
		this.listaProveedoresSeleccionados1 = listaProveedoresSeleccionados1;
	}

	public List<Proveedor> getListaProveedoresSeleccionados2() {
		return listaProveedoresSeleccionados2;
	}

	public void setListaProveedoresSeleccionados2(
			List<Proveedor> listaProveedoresSeleccionados2) {
		this.listaProveedoresSeleccionados2 = listaProveedoresSeleccionados2;
	}

	public Listbox getGridProveedores() {
		return gridProveedores;
	}

	public void setGridProveedores(Listbox gridProveedores) {
		this.gridProveedores = gridProveedores;
	}

	public Listbox getGridProveedoresSeleccionados() {
		return gridProveedoresSeleccionados;
	}

	public void setGridProveedoresSeleccionados(Listbox gridProveedoresSeleccionados) {
		this.gridProveedoresSeleccionados = gridProveedoresSeleccionados;
	}

	public List<DetalleRequerimiento> getListaDetalleRequerimientos() {
		return listaDetalleRequerimientos;
	}

	public void setListaDetalleRequerimientos(
			List<DetalleRequerimiento> listaDetalleRequerimientos) {
		this.listaDetalleRequerimientos = listaDetalleRequerimientos;
	}
	
	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public DetalleRequerimiento getDetalleRequerimiento() {
		return detalleRequerimiento;
	}

	public void setDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
		this.detalleRequerimiento = detalleRequerimiento;
	}

	
	
	
	
}
