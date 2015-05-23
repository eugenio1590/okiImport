package com.okiimport.app.configuracion.dao.impl;

import org.springframework.stereotype.Repository;

import com.okiimport.app.configuracion.dao.MonedaDAO;
import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Moneda;

@Repository
public class MonedaDAOImpl extends AbstractJpaDao<Moneda, Integer> implements MonedaDAO {

	public MonedaDAOImpl() {
		super(Moneda.class);
		// TODO Auto-generated constructor stub
	}

}
