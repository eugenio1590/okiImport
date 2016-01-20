package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;

import com.okiimport.app.model.Requerimiento;

public class MisRequerimientosEmitidosViewModel extends AbstractMisRequerimientosViewModel {

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaMisRequerimientosEmitidos.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		listaEstatus = llenarListaEstatusEmitidos();
		listaEstatus.add(estatusFiltro);
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: permitira crear el emergente (modal) necesario para editar el requerimiento seleccionado
	 * Parametros: requerimiento @param view: listaMisRequerimientosEmitidos.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void editarReguerimiento(@BindingParam("requerimiento") final Requerimiento requerimiento){
		requerimiento.setDetalleRequerimientos(consultarDetallesRequerimiento(requerimiento));
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		parametros.put("editar", true);
		crearModal(BasePackageSistemaFunc+"emitidos/editarRequerimiento.zul", parametros);
	}
	
	/**
	 * Descripcion: permitira crear el emergente (modal) necesario para enviar las solicitudes de cotizacion
	 * a los proveedores del requerimiento seleccionado
	 * Parametros: Requerimiento: requerimiento seleccionado @param view: listaMisRequerimientosEmitidos.zul    
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void enviarProveedores(@BindingParam("requerimiento") final Requerimiento requerimiento){
		if(!requerimiento.isCerrarSolicitud()){
			requerimiento.setDetalleRequerimientos(consultarDetallesRequerimiento(requerimiento));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("requerimiento", requerimiento);
			crearModal(BasePackageSistemaFunc+"emitidos/enviarRequerimientoProv.zul", parametros);
		}
		else
			mostrarMensaje("Informaci\u00F3n", "Ha expirado el tiempo para Enviar a Proveedores", null, null, null, null);
	}

	/**METODOS OVERRIDE*/
	@Override
	protected Map<String, Object> consultarMisRequerimientos(String fieldSort, Boolean sortDirection, int page) {
		return sTransaccion.consultarMisRequerimientosEmitidos(requerimientoFiltro, 
				fieldSort, sortDirection,usuario.getPersona().getId(), page, pageSize);
	}

}
