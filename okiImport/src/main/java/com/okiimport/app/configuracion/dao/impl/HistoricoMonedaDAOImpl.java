package com.okiimport.app.configuracion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.okiimport.app.configuracion.dao.HistoricoMonedaDAO;
import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.HistoricoMoneda;

@Repository
public class HistoricoMonedaDAOImpl extends AbstractJpaDao<HistoricoMoneda, Integer> implements HistoricoMonedaDAO {

	public HistoricoMonedaDAOImpl() {
		super(HistoricoMoneda.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public HistoricoMoneda consultarActualConversion(Integer idMoneda) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();

		
		//2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("moneda", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		//3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();
		
		restricciones.add(criteriaBuilder.equal(joins.get("moneda").get("idMoneda"), idMoneda));
		
		//4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("fechaCreacion", false);
		orders.put("idHistoria", true);
		
		List<HistoricoMoneda> historicos = this.ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, 0, 1);
		if(historicos.size()>0)
			return historicos.get(0);
		else
			return null;
	}

}
