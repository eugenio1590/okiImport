package com.okiimport.app.configuracion.servicios;

import java.util.Map;

import com.okiimport.app.modelo.HistoricoMoneda;
import com.okiimport.app.modelo.Moneda;

public interface SControlConfiguracion {
	public Map<String, Object> consultarMonedas(int page, int limite);
	public HistoricoMoneda consultarActualConversion(Moneda moneda);
}
