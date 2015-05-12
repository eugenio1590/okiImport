package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Cotizacion;

public interface CotizacionDAO extends IGenericDao<Cotizacion, Integer> {
	public List<Cotizacion> consultarCotizacionesAsignadas(Cotizacion cotizacion, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int start, int limit);
	public List<Cotizacion> consultarSolicitudCotizaciones(Cotizacion cotizacion, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int idProveedor, List<String> estatus, int start, int limit);
}
