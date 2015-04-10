package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.MarcaVehiculo;

public interface MarcaVehiculoDAO extends IGenericDao<MarcaVehiculo, Integer> {
	
	public List<MarcaVehiculo> listaMarcasVehiculosActivas(Integer start, Integer limit);

}
