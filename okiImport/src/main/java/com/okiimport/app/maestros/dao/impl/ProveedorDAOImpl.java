package com.okiimport.app.maestros.dao.impl;

import java.util.List;

import javax.persistence.criteria.Predicate;

import com.okiimport.app.maestros.dao.ProveedorDAO;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Proveedor;

public class ProveedorDAOImpl extends PersonaDAOImpl<Proveedor> implements ProveedorDAO {

	public ProveedorDAOImpl() {
		super(Proveedor.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Proveedor> consultarProveedoresSinUsuarios(Persona persona,  String fieldSort, Boolean sortDirection, 
			int start, int limit) {
		// TODO Auto-generated method stub
		Proveedor proveedor = (persona==null) ? new Proveedor() : new Proveedor(persona);
		return consultarPersonaSinUsuarios(proveedor, fieldSort, sortDirection, start, limit);
	}

	@Override
	protected void agregarRestriccionesPersona(Proveedor persona, List<Predicate> restricciones) {
		// TODO Auto-generated method stub
		
	}
}
