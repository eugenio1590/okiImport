package com.okiimport.app.maestros.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

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
	public List<Proveedor> consultarProveedoresListaClasificacionRepuesto(Persona persona, String fieldSort, Boolean sortDirection,
			List<Integer> idsClasificacionRepuesto, int start, int limit) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();

		//2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("clasificacionRepuestos", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		//3. Creamos las Restricciones de la busqueda
		this.distinct = true;
		this.selected = new Selection[]{
				entity.get("id"),
				entity.get("cedula"),
				entity.get("correo"),
				entity.get("direccion"),
				entity.get("nombre"),
				entity.get("telefono"),
				entity.get("estatus"),
				entity.get("tipoProveedor")
		};
		
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(joins.get("clasificacionRepuestos").get("idClasificacionRepuesto")
				.in(idsClasificacionRepuesto));
		Proveedor proveedor = (persona==null) ? new Proveedor() : new Proveedor(persona);
		proveedor.setEstatus("activo");
		agregarFiltros(proveedor, restricciones);
		
		//4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();
		orders.add(criteriaBuilder.asc(entity.get("id")));
		
		List<Expression<?>> groupBy = new ArrayList<Expression<?>>();
		groupBy.add(entity.get("id"));
		groupBy.add(entity.get("cedula"));
		groupBy.add(entity.get("correo"));
		groupBy.add(entity.get("direccion"));
		groupBy.add(entity.get("nombre"));
		groupBy.add(entity.get("telefono"));
		groupBy.add(entity.get("estatus"));
		groupBy.add(entity.get("tipoProveedor"));
		
		return this.ejecutarCriteriaOrder(concatenaArrayPredicate(restricciones), null, groupBy, orders, start, limit);
	}

	@Override
	protected void agregarRestriccionesPersona(Proveedor personaF, List<Predicate> restricciones) {
		// TODO Auto-generated method stub
	}
}
