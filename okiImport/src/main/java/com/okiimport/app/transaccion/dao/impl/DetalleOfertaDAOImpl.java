package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.transaccion.dao.DetalleOfertaDAO;

public class DetalleOfertaDAOImpl extends AbstractJpaDao<DetalleOferta, Integer> implements DetalleOfertaDAO {

	public DetalleOfertaDAOImpl() {
		super(DetalleOferta.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<DetalleOferta> consultarDetalleOferta(Integer idOferta, int start, int limit) {
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();
				
		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("oferta", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(criteriaBuilder.equal(
				joins.get("oferta").get("idOferta"), idOferta));
		
		// 4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("idDetalleOferta", true);
		
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}

	@Override
	public List<DetalleOferta> consultarDetalleCompra(Integer idCompra, String fieldSort, Boolean sortDirection, 
			int start, int limit) {
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();
						
		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("compra", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
				
		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(criteriaBuilder.equal(
				joins.get("compra").get("idCompra"), idCompra));
		
		// 4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		if (fieldSort != null && sortDirection != null)
			orders.put(fieldSort, sortDirection);
		else
			orders.put("idDetalleOferta", true);
		
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}

	
}
