package com.okiimport.app.configuracion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Moneda;

public interface MonedaDAO extends IGenericDao<Moneda, Integer> {
	public List<Moneda> consultarMonedasConHistorico(String estatus, int start, int limit);
}
