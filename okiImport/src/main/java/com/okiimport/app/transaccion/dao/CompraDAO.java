package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Compra;

public interface CompraDAO extends IGenericDao<Compra, Integer> {
	public List<Compra> consultarComprasPorRequerimiento(Compra compraF, int idRequerimiento, String fieldSort, Boolean sortDirection,
			int start, int limit);
}
