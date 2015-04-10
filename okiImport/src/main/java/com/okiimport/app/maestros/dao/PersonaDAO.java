package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Persona;

public interface PersonaDAO<T extends Persona> extends IGenericDao<T, Integer> {
	public T consultarPersona(T persona);
	public List<T> consultarPersonaSinUsuarios(T persona, String fieldSort, Boolean sortDirection, int start, int limit);
}
