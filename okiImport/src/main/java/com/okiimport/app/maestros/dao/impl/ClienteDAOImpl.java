package com.okiimport.app.maestros.dao.impl;

import java.util.List;

import javax.persistence.criteria.Predicate;

import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.modelo.Cliente;

public class ClienteDAOImpl extends PersonaDAOImpl<Cliente> implements ClienteDAO {

	public ClienteDAOImpl() {
		super(Cliente.class);
		// TODO Auto-generated constructor stub
	}

	/**METODOS OVERRIDE*/
	@Override
	protected void agregarRestriccionesPersona(Cliente persona, List<Predicate> restricciones) {
		// TODO Auto-generated method stub
		
	}
}
