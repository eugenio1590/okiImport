package com.okiimport.app.transaccion.dao.impl;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Compra;
import com.okiimport.app.transaccion.dao.CompraDAO;

public class CompraDAOImpl extends AbstractJpaDao<Compra, Integer> implements CompraDAO {

	public CompraDAOImpl() {
		super(Compra.class);
		// TODO Auto-generated constructor stub
	}

	
}
