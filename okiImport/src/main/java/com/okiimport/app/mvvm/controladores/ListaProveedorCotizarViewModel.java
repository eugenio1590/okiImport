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

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.BeanInjector;

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
		Usuario usuario = proveedor.getUsuario();
		if(usuario==null)
			usuario = sControlUsuario.consultarUsuario((int) proveedor.getId());
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", this.requerimiento);
		parametros.put("usuario", usuario);
		System.out.println("PROVEEDOR ID"+proveedor.getId());
		System.out.println("NULO USUARIO - PROVEEDOR: "+(usuario==null));
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