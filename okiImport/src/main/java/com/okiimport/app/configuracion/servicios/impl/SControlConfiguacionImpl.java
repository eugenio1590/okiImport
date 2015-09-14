package com.okiimport.app.configuracion.servicios.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okiimport.app.configuracion.dao.ConfiguracionDAO;
import com.okiimport.app.configuracion.dao.HistoricoMonedaDAO;
import com.okiimport.app.configuracion.dao.MonedaDAO;
import com.okiimport.app.configuracion.servicios.SControlConfiguracion;
import com.okiimport.app.modelo.Configuracion;
import com.okiimport.app.modelo.HistoricoMoneda;
import com.okiimport.app.modelo.Moneda;
import com.okiimport.app.mvvm.BeanInjector;

@Service
public class SControlConfiguacionImpl implements SControlConfiguracion {

	@Autowired
	@BeanInjector("monedaDAO")
	private MonedaDAO monedaDAO;
	
	@Autowired
	@BeanInjector("historicoMonedaDAO")
	private HistoricoMonedaDAO historicoMonedaDAO;
	
	@Autowired
	@BeanInjector("configuracionDAO")
	private ConfiguracionDAO configuracionDAO;
	
	public SControlConfiguacionImpl() {
		// TODO Auto-generated constructor stub
	}

	public MonedaDAO getMonedaDAO() {
		return monedaDAO;
	}

	public void setMonedaDAO(MonedaDAO monedaDAO) {
		this.monedaDAO = monedaDAO;
	}

	public HistoricoMonedaDAO getHistoricoMonedaDAO() {
		return historicoMonedaDAO;
	}

	public void setHistoricoMonedaDAO(HistoricoMonedaDAO historicoMonedaDAO) {
		this.historicoMonedaDAO = historicoMonedaDAO;
	}

	public ConfiguracionDAO getConfiguracionDAO() {
		return configuracionDAO;
	}

	public void setConfiguracionDAO(ConfiguracionDAO configuracionDAO) {
		this.configuracionDAO = configuracionDAO;
	}
	
	//Configuracion
	@Override
	public Configuracion consultarConfiguracionActual() {
		// TODO Auto-generated method stub
		List<Configuracion> configuraciones = configuracionDAO.findAll(0, 1);
		return configuraciones.get(0);
	}

	//Historico de Moneda
	@Override
	public Map<String, Object> consultarMonedasConHistorico(int page, int limite) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", monedaDAO.consultarMonedasConHistorico("activo", 0, -1).size());
		parametros.put("monedas", monedaDAO.consultarMonedasConHistorico("activo", page*limite, limite));
		return parametros;
	}

	@Override
	public HistoricoMoneda consultarActualConversion(Moneda moneda) {
		// TODO Auto-generated method stub
		Integer idMoneda = moneda.getIdMoneda();
		return this.historicoMonedaDAO.consultarActualConversion(idMoneda);
	}
}
