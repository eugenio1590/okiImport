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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class RegistrarProveedorViewModel extends AbstractViewModel {
	
	private Proveedor proveedor;
	
	private List<MarcaVehiculo> listaMarcaVehiculos;
	
	



	@Wire("#gridMarcas")
	private Listbox gridMarcas;
	@Wire("#pagMarcas")
	private Paging pagMarcas;
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	private Integer page_size = 6;
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		limpiar();
		pagMarcas.setPageSize(page_size);
		consultarMarcas(0);
	
		
	}
	
	@Command
	@NotifyChange({"proveedor"})
	public void limpiar(){
		proveedor = new Proveedor();
		
	}
	
	@Command
	@NotifyChange({"proveedor"})
	public void registrar(){
		proveedor = sMaestros.registrarProveedor(proveedor);
	
		String str = "El Proveedor ha sido registrado existosamente ";

		Messagebox.show(str, "Informacion", Messagebox.OK,
				Messagebox.INFORMATION, new EventListener() {
					public void onEvent(Event event) throws Exception {
						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							recargar();
						}
					}
				});
		
	}
	
	
	@NotifyChange({"*"})
	@Command
	public void paginarLista(@BindingParam("tipo")int tipo){
		switch(tipo){
		case 1: consultarMarcas(pagMarcas.getActivePage());
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
	
	@NotifyChange({"listaMarcaVehiculos"})
	private void consultarMarcas(int page){
		Map<String, Object> Parametros= sMaestros.ConsultarMarca(page, page_size);
		listaMarcaVehiculos = (List<MarcaVehiculo>) Parametros.get("marcas");
		Integer total = (Integer) Parametros.get("total");
		gridMarcas.setMultiple(true);
		gridMarcas.setCheckmark(true);
		pagMarcas.setActivePage(page);
		pagMarcas.setTotalSize(total);
	}
	
	public List<MarcaVehiculo> getListaMarcaVehiculos() {
		return listaMarcaVehiculos;
	}

	public void setListaMarcaVehiculos(List<MarcaVehiculo> listaMarcaVehiculos) {
		this.listaMarcaVehiculos = listaMarcaVehiculos;
	}

}
