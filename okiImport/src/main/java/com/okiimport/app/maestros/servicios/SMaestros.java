package com.okiimport.app.maestros.servicios;

import java.util.List;
import java.util.Map;

import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Persona;

public interface SMaestros {
	//Marcas
	public Map<String,Object> ConsultarMarca(Integer page,Integer limit);
	
	//Cliente
	public Cliente registrarOActualizarCliente(Cliente cliente);

	//Analistas
	public Map<String, Object> consultarAnalistasSinUsuarios(Persona personaF,  String fieldSort, Boolean sortDirection, 
			int pagina, int limit);
	public Map<String, Object> consultarAdministradoresSinUsuarios(Persona personaF,  String fieldSort, Boolean sortDirection, 
			int pagina, int limit);
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int page, int limit);
	
	//Proveedores
	public Map<String, Object> consultarProveedoresSinUsuarios(Persona personaF, String fieldSort, Boolean sortDirection,
			int pagina, int limit);
}
