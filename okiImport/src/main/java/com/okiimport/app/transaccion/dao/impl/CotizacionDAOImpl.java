package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.transaccion.dao.CotizacionDAO;

public class CotizacionDAOImpl extends AbstractJpaDao<Cotizacion, Integer> implements
		CotizacionDAO {

	public CotizacionDAOImpl() {
		super(Cotizacion.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Cotizacion> consultarCotizacionesAsignadas(Cotizacion cotizacion, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("detalleCotizacions", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
					joins.get("detalleCotizacions").join("detalleRequerimiento").join("requerimiento").get("idRequerimiento"), 
					idRequerimiento));

		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null) 
			if (fieldSort.equalsIgnoreCase("nombre"))
						orders.add((sortDirection) ? this.criteriaBuilder.asc(this.entity.get(fieldSort)) : 
							this.criteriaBuilder.desc(this.entity.get(fieldSort)));
				
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}



}
