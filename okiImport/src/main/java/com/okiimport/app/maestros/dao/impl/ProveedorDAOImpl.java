package com.okiimport.app.maestros.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.ProveedorDAO;
import com.okiimport.app.modelo.Proveedor;

public class ProveedorDAOImpl extends AbstractJpaDao<Proveedor, Integer> implements
		ProveedorDAO {

	public ProveedorDAOImpl() {
		super(Proveedor.class);
		// TODO Auto-generated constructor stub
	}

}
