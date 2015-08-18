package com.okiimport.app.transaccion.dao.impl;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Oferta;
import com.okiimport.app.transaccion.dao.OfertaDAO;

public class OfertaDAOImpl extends AbstractJpaDao<Oferta, Integer> implements OfertaDAO {

	public OfertaDAOImpl() {
		super(Oferta.class);
		// TODO Auto-generated constructor stub
	}

	
}
