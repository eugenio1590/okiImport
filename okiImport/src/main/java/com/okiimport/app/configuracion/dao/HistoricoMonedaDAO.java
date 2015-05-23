package com.okiimport.app.configuracion.dao;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.HistoricoMoneda;

public interface HistoricoMonedaDAO extends IGenericDao<HistoricoMoneda, Integer> {
	public HistoricoMoneda consultarActualConversion(Integer idMoneda);
}
