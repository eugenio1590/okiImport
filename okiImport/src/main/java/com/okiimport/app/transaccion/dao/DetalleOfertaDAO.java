package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.DetalleOferta;

public interface DetalleOfertaDAO extends IGenericDao<DetalleOferta, Integer> {
	public List<DetalleOferta> consultarDetalleOferta(Integer idOferta, int start, int limit);
	public List<DetalleOferta> consultarDetalleCompra(Integer idCompra, String fieldSort, Boolean sortDirection, int start, int limit);
}
