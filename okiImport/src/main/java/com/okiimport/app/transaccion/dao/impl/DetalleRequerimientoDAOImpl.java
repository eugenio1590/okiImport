package com.okiimport.app.transaccion.dao.impl;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.DetalleRequerimiento;

import com.okiimport.app.transaccion.dao.DetalleRequerimientoDAO;

public class DetalleRequerimientoDAOImpl extends AbstractJpaDao<DetalleRequerimiento, Integer>
		implements DetalleRequerimientoDAO {

	public DetalleRequerimientoDAOImpl() {
		super(DetalleRequerimiento.class);
		// TODO Auto-generated constructor stub
	}

}
