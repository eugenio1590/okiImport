package com.okiimport.app.dao;

import com.okiimport.app.modelo.Analista;

public interface AnalistaDAO extends IGenericDao<Analista, Integer> {
	
	public Analista BuscarAnalistaByUserName(String userName);

}
