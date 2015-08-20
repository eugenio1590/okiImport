package com.okiimport.app.maestros.dao.impl;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.BancoDAO;
import com.okiimport.app.modelo.Banco;

public class BancoDAOImpl extends AbstractJpaDao<Banco, Integer> implements BancoDAO {

	public BancoDAOImpl() {
		super(Banco.class);
		// TODO Auto-generated constructor stub
	}

	
}
