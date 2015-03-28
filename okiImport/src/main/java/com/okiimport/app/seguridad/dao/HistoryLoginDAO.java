package com.okiimport.app.seguridad.dao;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.HistoryLogin;

public interface HistoryLoginDAO extends IGenericDao<HistoryLogin, Integer> {
	public HistoryLogin sessionNoTerminada(String username);
}
