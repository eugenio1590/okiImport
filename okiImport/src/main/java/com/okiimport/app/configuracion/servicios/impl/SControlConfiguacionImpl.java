package com.okiimport.app.configuracion.servicios.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okiimport.app.configuracion.dao.MonedaDAO;
import com.okiimport.app.configuracion.servicios.SControlConfiguracion;
import com.okiimport.app.mvvm.BeanInjector;

@Service
public class SControlConfiguacionImpl implements SControlConfiguracion {

	@Autowired
	@BeanInjector("monedaDAO")
	private MonedaDAO monedaDAO;
	
	public SControlConfiguacionImpl() {
		// TODO Auto-generated constructor stub
	}

	public MonedaDAO getMonedaDAO() {
		return monedaDAO;
	}

	public void setMonedaDAO(MonedaDAO monedaDAO) {
		this.monedaDAO = monedaDAO;
	}

	@Override
	public Map<String, Object> consultarMonedas(int page, int limite) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", Long.valueOf(monedaDAO.countAll()).intValue());
		parametros.put("monedas", monedaDAO.findAll(page*limite, limite));
		return parametros;
	}

}
