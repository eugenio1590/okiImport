package com.okiimport.app.transaccion.servicios;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Requerimiento;

public interface STransaccion {
	
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);
	public void asignarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros);

}
