package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.modelo.Analista;

public interface AnalistaDAO extends PersonaDAO<Analista> {
	public Analista BuscarAnalistaByUserName(String userName);
	public List<Analista> consultarAnalistasSinUsuarios(int start, int limit);
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int start, int limit);
}