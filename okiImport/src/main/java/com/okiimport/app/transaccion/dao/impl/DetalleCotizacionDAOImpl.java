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
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.transaccion.dao.DetalleCotizacionDAO;

public class DetalleCotizacionDAOImpl extends
		AbstractJpaDao<DetalleCotizacion, Integer> implements
		DetalleCotizacionDAO {

	public DetalleCotizacionDAOImpl() {
		super(DetalleCotizacion.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<DetalleCotizacion> ConsultarDetalleCotizacion(int idCotizacion,
			int page, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("cotizacion", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
					joins.get("cotizacion").get("idCotizacion"), 
					idCotizacion));
		// 4. Creamos los campos de ordenamiento y ejecutamos
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.asc(entity.get("idDetalleCotizacion")));

	return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, page, limit);
	}

}
