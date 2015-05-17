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
import org.zkoss.zul.Paging;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.ClasificacionRepuesto;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class SeleccionarProveedoresViewModel extends AbstractViewModel {
	
	private Proveedor proveedor;
	private List<Proveedor> listaProveedores;


	@Wire("#pagProveedores")
	private Paging pagProveedores;
	
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	private Integer page_size = 3;
	private List<Proveedor> proveedoresSeleccionados;
	
	//*******parte de fase 2
	private List <Integer> idsClasificacionRepuesto;
	private Listbox gridProveedores;
	private Listbox gridProveedoresSeleccionados;
	
	

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("ids") List <Integer> idsClasificacionRepuesto){
		super.doAfterCompose(view);
		limpiar();
		pagProveedores.setPageSize(page_size);
		//*******parte de fase 2
		//this.idsClasificacionRepuesto=idsClasificacionRepuesto;
	
	}
	
	@Command
	@NotifyChange({"proveedor"})
	public void limpiar(){
		proveedor = new Proveedor();	
	}


	@NotifyChange({"*"})
	@Command
	public void agregarProveedores(){
		this.moveSelection(listaProveedores, proveedor.getNombre(), proveedoresSeleccionados , "No se puede agregar Proveedor");
		
	}
	
	private void moveSelection(List<Proveedor> listaProveedores2,
			String nombre, List<Proveedor> proveedoresSeleccionados2,
			String failMessage) {
		// TODO Auto-generated method stub
		
	}

	@NotifyChange({"*"})
	@Command
	public void eliminarProveedores(){
		this.moveSelection(proveedor.getNombre(), listaProveedores, proveedoresSeleccionados, "No se puede eliminar el Proveedor");
	}
	
	
	private void moveSelection(String nombre,
			List<Proveedor> listaProveedores2,
			List<Proveedor> proveedoresSeleccionados2, String failMessage) {
		// TODO Auto-generated method stub
		
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
		Map<String, Object> Parametros= sMaestros.ConsultarProveedoresListaClasificacionRepuesto(null, null, null, idsClasificacionRepuesto,page, page_size);
		listaProveedores = (List<Proveedor>) Parametros.get("proveedores");
		Integer total = (Integer) Parametros.get("total");
		gridProveedores.setMultiple(true);
		gridProveedores.setCheckmark(true);
		gridProveedoresSeleccionados.setMultiple(true);
		gridProveedoresSeleccionados.setCheckmark(true);
		pagProveedores.setActivePage(page);
		pagProveedores.setTotalSize(total);
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

}