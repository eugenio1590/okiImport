package com.okiimport.app.servicios.seguridad;

import com.okiimport.app.modelo.Usuario;

public interface SHistorial {
	//Historial de Session
	public void registrarHistorialSession(Usuario usuario);
	public void cerarHistorialSession(Usuario usuario);
}
