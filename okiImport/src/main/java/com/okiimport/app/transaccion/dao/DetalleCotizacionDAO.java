package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.DetalleCotizacion;

public interface DetalleCotizacionDAO extends IGenericDao<DetalleCotizacion, Integer> {
	public List<DetalleCotizacion> consultarDetallesCotizacion(DetalleCotizacion detalleF, int idCotizacion,
			String fieldSort, Boolean sortDirection, int start, int limit);
}
