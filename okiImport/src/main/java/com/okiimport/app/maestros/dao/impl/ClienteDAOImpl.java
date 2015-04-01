package com.okiimport.app.maestros.dao.impl;

import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.modelo.Cliente;

public class ClienteDAOImpl extends PersonaDAOImpl<Cliente> implements ClienteDAO {

	public ClienteDAOImpl() {
		super(Cliente.class);
		// TODO Auto-generated constructor stub
	}

	
}
