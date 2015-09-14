package com.okiimport.app.configuracion.dao.impl;

import com.okiimport.app.configuracion.dao.ConfiguracionDAO;
import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Configuracion;

public class ConfiguracionDAOImpl extends AbstractJpaDao<Configuracion, Integer> implements ConfiguracionDAO {

	public ConfiguracionDAOImpl() {
		super(Configuracion.class);
		// TODO Auto-generated constructor stub
	}

}
