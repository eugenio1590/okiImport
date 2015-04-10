package com.okiimport.app.transaccion.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
