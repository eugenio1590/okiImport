package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Oferta;
import com.okiimport.app.transaccion.dao.OfertaDAO;

public class OfertaDAOImpl extends AbstractJpaDao<Oferta, Integer> implements OfertaDAO {

	public OfertaDAOImpl() {
		super(Oferta.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Oferta> consultarOfertasPorRequerimiento(Integer idRequerimiento, List<String> estatus, 
			String fieldSort, Boolean sortDirection, int start, int limit) {
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();
		
		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("detalleOfertas", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		// 3. Creamos las Restricciones de la busqueda
		this.distinct = true;
		this.selected = new Selection[]{
			this.entity.get("idOferta"),
			this.entity.get("fechaCreacion"),
			this.entity.get("estatus")
		};
		
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
				joins.get("detalleOfertas").join("detalleCotizacion")
					.join("detalleRequerimiento").join("requerimiento").get("idRequerimiento"), 
				idRequerimiento));
		
		if(estatus!=null && !estatus.isEmpty())
			restricciones.add(this.entity.get("estatus").in(estatus));
		
		// 4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		if(fieldSort!=null && sortDirection!=null)
			orders.put(fieldSort, sortDirection);
		else
			orders.put("idOferta", true);
		
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}
}
