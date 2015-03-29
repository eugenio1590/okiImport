package com.okiimport.app.maestros.servicios;

import java.util.Map;

import com.okiimport.app.modelo.Cliente;

public interface SMaestros {
	
	public Map<String,Object> ConsultarMarca(Integer page,Integer limit);
	
	public Cliente registrarCliente(Cliente cliente);

}
