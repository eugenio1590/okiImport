package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Ciudad;

public interface CiudadDAO extends IGenericDao<Ciudad, Integer> {
	public List<Ciudad> listaCiudadesEstado(Integer idEstado, int start, int limit);
}
