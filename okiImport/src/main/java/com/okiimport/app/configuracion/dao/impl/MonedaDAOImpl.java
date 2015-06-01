package com.okiimport.app.configuracion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.okiimport.app.configuracion.dao.MonedaDAO;
import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.HistoricoMoneda;
import com.okiimport.app.modelo.Moneda;

@Repository
public class MonedaDAOImpl extends AbstractJpaDao<Moneda, Integer> implements MonedaDAO {

	public MonedaDAOImpl() {
		super(Moneda.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Moneda> consultarMonedasConHistorico(int start, int limit) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();
		
		//2. Generamos los Joins
		this.crearJoins(null);

		//3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.isNotEmpty(this.entity.get("historicoMonedas").as(List.class)));

		//4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("idMoneda", true);

		return this.ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}

}
