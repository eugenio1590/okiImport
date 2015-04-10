package com.okiimport.app.maestros.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.modelo.MarcaVehiculo;

public class MarcaVehiculoDAOImpl extends AbstractJpaDao<MarcaVehiculo, Integer> implements
		MarcaVehiculoDAO {

	public MarcaVehiculoDAOImpl() {
		super(MarcaVehiculo.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<MarcaVehiculo> listaMarcasVehiculosActivas(Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		this.crearCriteria();
		this.crearJoins(null);
		List<Predicate> restrinciones = new ArrayList<Predicate>();
		restrinciones.add(this.criteriaBuilder.equal(this.entity.get("estatus"), "activo"));
		return this.ejecutarCriteria(concatenaArrayPredicate(restrinciones), null, start,limit);
	}

	

}
