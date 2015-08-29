package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Compra;
import com.okiimport.app.transaccion.dao.CompraDAO;

public class CompraDAOImpl extends AbstractJpaDao<Compra, Integer> implements CompraDAO {

	public CompraDAOImpl() {
		super(Compra.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Compra> consultarComprasPorRequerimiento(Compra compraF, int idRequerimiento, 
			String fieldSort, Boolean sortDirection, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("requerimiento", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(
				criteriaBuilder.equal(
						joins.get("requerimiento").get("idRequerimiento"), 
						idRequerimiento)
		);
		
		agregarFiltros(compraF, restricciones);
		
		// 4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		if(fieldSort!=null && sortDirection!=null)
			orders.put(fieldSort, sortDirection);
		else
			orders.put("idCompra", true);
		
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}

	private void agregarFiltros(Compra compraF, List<Predicate> restricciones) {
		// TODO Auto-generated method stub
		
	}

	
}
