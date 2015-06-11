package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Estado;

public interface EstadoDAO extends IGenericDao<Estado, Integer> {
	
	public List<Estado> listaEstadosActivos(Integer start, Integer limit);

}
