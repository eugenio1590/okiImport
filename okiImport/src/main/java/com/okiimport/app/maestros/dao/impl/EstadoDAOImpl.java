package com.okiimport.app.maestros.dao.impl;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.EstadoDAO;
import com.okiimport.app.modelo.Estado;

public class EstadoDAOImpl extends AbstractJpaDao<Estado, Integer> implements EstadoDAO {

	public EstadoDAOImpl() {
		super(Estado.class);
		// TODO Auto-generated constructor stub
	}

	
}
