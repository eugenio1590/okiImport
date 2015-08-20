package com.okiimport.app.transaccion.dao.impl;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.transaccion.dao.DetalleOfertaDAO;

public class DetalleOfertaDAOImpl extends AbstractJpaDao<DetalleOferta, Integer> implements DetalleOfertaDAO {

	public DetalleOfertaDAOImpl() {
		super(DetalleOferta.class);
		// TODO Auto-generated constructor stub
	}

	
}
