package com.okiimport.app.maestros.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.modelo.Cliente;

public class ClienteDAOImpl extends AbstractJpaDao<Cliente, Integer> implements ClienteDAO {

	public ClienteDAOImpl() {
		super(Cliente.class);
		// TODO Auto-generated constructor stub
	}

	
}
