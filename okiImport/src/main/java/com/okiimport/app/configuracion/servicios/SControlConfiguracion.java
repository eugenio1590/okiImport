package com.okiimport.app.configuracion.servicios;

import java.util.Map;

import com.okiimport.app.modelo.Configuracion;
import com.okiimport.app.modelo.HistoricoMoneda;
import com.okiimport.app.modelo.Moneda;

public interface SControlConfiguracion {
	//Configuracion
	public Configuracion consultarConfiguracionActual();
	
	//Historico de Moneda
	public Map<String, Object> consultarMonedasConHistorico(int page, int limite);
	public HistoricoMoneda consultarActualConversion(Moneda moneda);
}
