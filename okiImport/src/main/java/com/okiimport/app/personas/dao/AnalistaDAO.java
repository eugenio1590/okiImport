package com.okiimport.app.personas.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Analista;

public interface AnalistaDAO extends IGenericDao<Analista, Integer> {
	public Analista BuscarAnalistaByUserName(String userName);
	public List<Analista> consultarAnalistasSinUsuarios(int start, int limit);
}