package com.okiimport.app.transaccion.servicios;

import java.util.List;
import java.util.Map;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleCotizacionInternacional;
import com.okiimport.app.modelo.Requerimiento;

public interface STransaccion {
	
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	public Requerimiento actualizarRequerimiento(Requerimiento requerimiento);
	public void asignarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	
	public Map <String, Object> consultarMisRequerimientosEmitidos(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int pagina, int limit);
	
	public Map <String, Object> consultarMisRequerimientosProcesados(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int pagina, int limit);

	public Map <String, Object> ConsultarRequerimientosCliente(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, String cedula,
			int pagina, int limit);
	
	public Map <String, Object> RequerimientosCotizados(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int pagina, int limit);
	

	public Map <String, Object> ConsultarCotizacionesRequerimiento(Cotizacion cotFiltro, String fieldSort, Boolean sortDirection, Integer idrequerimiento,
			int pagina, int limit);

	public Map <String, Object> ConsultarRequerimientosConSolicitudesCotizacion(Requerimiento regFiltro, String fieldSort, 
			Boolean sortDirection, Integer idProveedor, int pagina, int limit);
	
	//Cotizaciones
	public Map<String, Object> consultarSolicitudCotizaciones(Cotizacion cotizacionF, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int idProveedor, int pagina, int limit);
	public Cotizacion ActualizarCotizacion(Cotizacion cotizacion);
	public Cotizacion registrarSolicitudCotizacion(Cotizacion cotizacion, List<DetalleCotizacion> detalleCotizacions);
	public Cotizacion registrarCotizacion(Cotizacion cotizacion);
	
	//Detalle de Cotizaciones
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacion detalleF, int idCotizacion,
			String fieldSort, Boolean sortDirection, int pagina, int limit);
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacion detalleF, Integer idRequerimiento,
			String fieldSort, Boolean sortDirection, int pagina, int limit);
	public Map <String, Object> ConsultarDetalleCotizacion(Integer idcotizacion,int pagina, int limit);
	
		//Internacional
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacionInternacional detalleF, int idCotizacion,
			String fieldSort, Boolean sortDirection, int pagina, int limit);
}
