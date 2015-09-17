package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.model.Usuario;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.service.configuracion.SControlUsuario;

public class ListaProveedorCotizarViewModel extends ListaProveedoresViewModel {
	
	//Servicios
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;
	
	//Atributos
	private Requerimiento requerimiento;
	private String size;
	
	@AfterCompose(superclass=true)
	public void doAfterCompose(@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("size") String size){
		this.size = size;
		this.requerimiento = requerimiento;
		cambiarProveedores(0, null, null);
	}
	
	/**GLOBAL COMMAND OVERRIDE*/
	@GlobalCommand
	@Override
	@NotifyChange("proveedores")
	public void cambiarProveedores(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		if(requerimiento!=null){
			Map<String, Object> parametros = sMaestros.consultarProveedoresConSolicitudCotizaciones(proveedorFiltro, 
					requerimiento.getIdRequerimiento(), fieldSort, sortDirection, page, pageSize);
			Integer total = (Integer) parametros.get("total");
			proveedores = (List<Proveedor>) parametros.get("proveedores");
			pagProveedores.setActivePage(page);
			pagProveedores.setTotalSize(total);
		}
	}
	
	/**COMMAND*/
	/*
	 * Descripcion: permitira viszualizar la lista de proveedores para poder cotizar un requerimiento
	 * @param proveedor: proveedor seleccionado
	 * Retorno: Ninguno
	 */
	@Command
	public void cotizar(@BindingParam("proveedor") Proveedor proveedor){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", this.requerimiento);
		parametros.put("persona", proveedor);
		if(proveedor.getTipoProveedor())
			crearModal("/WEB-INF/views/sistema/funcionalidades/listaCotizacionesProveedorNacional.zul", parametros);
		else
			crearModal("/WEB-INF/views/sistema/funcionalidades/listaCotizacionesProveedorInternacional.zul", parametros);
	}
	
	/**GETTERS Y SETTERS*/
	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}	
}