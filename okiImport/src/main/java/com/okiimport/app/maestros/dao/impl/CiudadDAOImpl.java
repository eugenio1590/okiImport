package com.okiimport.app.maestros.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.CiudadDAO;
import com.okiimport.app.modelo.Ciudad;

public class CiudadDAOImpl extends AbstractJpaDao<Ciudad, Integer> implements
		CiudadDAO {

	public CiudadDAOImpl() {
		super(Ciudad.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Ciudad> listaCiudadesActivas(Integer idEstado, Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		this.crearCriteria();
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("estado", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		List<Predicate> restrinciones = new ArrayList<Predicate>();
		restrinciones.add(this.criteriaBuilder.equal(joins.get("estado").get("idEstado"), idEstado));
		Map<String, Boolean> orders=new HashMap<String, Boolean>();
		return this.ejecutarCriteria(concatenaArrayPredicate(restrinciones), orders, start,limit);
	}

	

}