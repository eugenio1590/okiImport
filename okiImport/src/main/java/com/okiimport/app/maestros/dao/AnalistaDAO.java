package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Persona;

public interface AnalistaDAO extends PersonaDAO<Analista> {
	public Analista BuscarAnalistaByUserName(String userName);
	public List<Analista> consultarAnalistasSinUsuarios(Persona persona, String fieldSort, Boolean sortDirection, 
			int start, int limit);
	public List<Analista> consultarAdministradoresSinUsuarios(Persona persona, String fieldSort, Boolean sortDirection, 
			int start, int limit);
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int start, int limit);
}