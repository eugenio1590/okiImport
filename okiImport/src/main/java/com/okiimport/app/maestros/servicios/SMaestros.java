package com.okiimport.app.maestros.servicios;

import java.util.List;
import java.util.Map;

import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cliente;

public interface SMaestros {
	//Marcas
	public Map<String,Object> ConsultarMarca(Integer page,Integer limit);
	
	//Cliente
	public Cliente registrarOActualizarCliente(Cliente cliente);

	//Analistas
	public Map<String, Object> consultarAnalistasSinUsuarios(int pagina, int limit);
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int page, int limit);
}
