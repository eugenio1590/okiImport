package com.okiimport.app.maestros.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.EstadoDAO;
import com.okiimport.app.modelo.Estado;

public class EstadoDAOImpl extends AbstractJpaDao<Estado, Integer> implements
		EstadoDAO {

	public EstadoDAOImpl() {
		super(Estado.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Estado> listaEstadosActivos(Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		this.crearCriteria();
		this.crearJoins(null);
		List<Predicate> restrinciones = new ArrayList<Predicate>();
		Map<String, Boolean> orders=new HashMap<String, Boolean>();
		return this.ejecutarCriteria(concatenaArrayPredicate(restrinciones), orders, start,limit);
	}

	

}