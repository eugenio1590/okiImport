package com.okiimport.app.transaccion.servicios;

import java.util.Map;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Requerimiento;

public interface STransaccion {
	
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	public void asignarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	
	public Map <String, Object> ConsultarMisRequerimientos(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int pagina, int limit);

	public Map <String, Object> ConsultarRequerimientosCliente(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, String cedula,
			int pagina, int limit);
	

}
