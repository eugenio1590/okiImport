package com.okiimport.app.transaccion.servicios;

import java.util.Map;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.Requerimiento;

public interface STransaccion {
	
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	public Requerimiento actualizarRequerimiento(Requerimiento requerimiento);
	public void asignarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	
	public Map <String, Object> ConsultarMisRequerimientos(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int pagina, int limit);

	public Map <String, Object> ConsultarRequerimientosCliente(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, String cedula,
			int pagina, int limit);
	
	public Map <String, Object> RequerimientosCotizados(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int pagina, int limit);
	
	public Map <String, Object> ConsultarRequerimientosConSolicitudesCotizacion(Requerimiento regFiltro, String fieldSort, 
			Boolean sortDirection, int idProveedor, int pagina, int limit);
	
	//Cotizaciones
	public Map<String, Object> consultarSolicitudCotizaciones(Cotizacion cotizacionF, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int idProveedor, int pagina, int limit);
	//Detalle de Cotizaciones
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacion detalleF, int idCotizacion,
			String fieldSort, Boolean sortDirection, int start, int limit);
	public Cotizacion registrarCotizacion(Cotizacion cotizacion);
}
