package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.DetalleCotizacion;

public interface DetalleCotizacionDAO extends IGenericDao<DetalleCotizacion, Integer> {
	
	List<DetalleCotizacion> ConsultarDetalleCotizacion(int idCotizacion, int page, int limit);

}
