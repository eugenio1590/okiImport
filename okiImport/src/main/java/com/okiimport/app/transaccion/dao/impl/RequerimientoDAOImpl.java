package com.okiimport.app.transaccion.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;

public class RequerimientoDAOImpl extends AbstractJpaDao<Requerimiento, Integer> implements
		RequerimientoDAO {

	public RequerimientoDAOImpl() {
		super(Requerimiento.class);
		// TODO Auto-generated constructor stub
	}

}
