package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.DetalleCotizacion;

public interface DetalleCotizacionDAO extends IGenericDao<DetalleCotizacion, Integer> {
	public List<DetalleCotizacion> consultarDetallesCotizacion(DetalleCotizacion detalleF, Integer idCotizacion, Integer idRequerimiento,
			boolean distinct, String fieldSort, Boolean sortDirection, int start, int limit);
	public List<DetalleCotizacion> ConsultarDetalleCotizacion(int idCotizacion, int page, int limit);
}
