package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Oferta;

public interface OfertaDAO extends IGenericDao<Oferta, Integer> {
	public List<Oferta> consultarOfertasPorRequerimiento(Integer idRequerimiento, List<String> estatus, 
			String fieldSort, Boolean sortDirection, int start, int limit);
}
