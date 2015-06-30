package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.DetalleCotizacionInternacional;

public interface DetalleCotizacionInternacionalDAO extends IGenericDao<DetalleCotizacionInternacional, Integer> {
	public List<DetalleCotizacionInternacional> consultarDetallesCotizacion(DetalleCotizacionInternacional detalleF, Integer idCotizacion, Integer idRequerimiento,
			boolean distinct,  boolean cantExacta, String fieldSort, Boolean sortDirection, int start, int limit);
}
