package com.okiimport.app.maestros.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.modelo.MarcaVehiculo;

public class MarcaVehiculoDAOImpl extends AbstractJpaDao<MarcaVehiculo, Integer> implements
		MarcaVehiculoDAO {

	public MarcaVehiculoDAOImpl() {
		super(MarcaVehiculo.class);
		// TODO Auto-generated constructor stub
	}

	

}
