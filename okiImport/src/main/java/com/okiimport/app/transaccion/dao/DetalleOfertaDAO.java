package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.DetalleOferta;

public interface DetalleOfertaDAO extends IGenericDao<DetalleOferta, Integer> {
	public List<DetalleOferta> consultarDetalleOferta(Integer idOferta, int start, int limit);
}
