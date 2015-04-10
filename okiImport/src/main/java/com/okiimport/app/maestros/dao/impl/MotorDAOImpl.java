package com.okiimport.app.maestros.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.maestros.dao.MotorDAO;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Motor;

public class MotorDAOImpl extends AbstractJpaDao<Motor, Integer> implements MotorDAO {

	public MotorDAOImpl() {
		super(Motor.class);
		// TODO Auto-generated constructor stub
	}

}
