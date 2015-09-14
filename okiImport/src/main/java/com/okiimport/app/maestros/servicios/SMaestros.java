package com.okiimport.app.maestros.servicios;

import java.util.List;
import java.util.Map;

import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Proveedor;

public interface SMaestros {
	//Marcas
	public Map<String,Object> ConsultarMarca(Integer page,Integer limit);
	public MarcaVehiculo registrarMarca(MarcaVehiculo marca);
	
	//Personas
	public <T extends Persona> T acutalizarPersona(T persona);
	
	//Cliente
	public Cliente registrarOActualizarCliente(Cliente cliente);
	public Cliente consultarCliente(Cliente cliente);

	//Analistas
	public Map<String, Object> consultarAnalistasSinUsuarios(Persona personaF,  String fieldSort, Boolean sortDirection, 
			int pagina, int limit);
	public Map<String, Object> consultarAdministradoresSinUsuarios(Persona personaF,  String fieldSort, Boolean sortDirection, 
			int pagina, int limit);
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int page, int limit);
	public Map<String, Object> consultarAnalistas(Analista analista, int page, int limit);
	/*public Map<String, Object> consultarAnalistas(Analista analistaF, String fieldSort, Boolean sortDirection, 
			int pagina, int limit);*/
	public Analista registrarAnalista(Analista analista);
	
	
	//Proveedores
	public Map<String, Object> consultarProveedoresSinUsuarios(Persona personaF, String fieldSort, Boolean sortDirection,
			int page, int limit);
	
	public Map<String,Object> ConsultarClasificacionRepuesto(Integer page,Integer limit);
	
	public Proveedor registrarProveedor(Proveedor proveedor);
	
	public Map<String,Object> ConsultarMotor(Integer page,Integer limit);
	
	public Map<String, Object> ConsultarProveedoresListaClasificacionRepuesto(Persona persona, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, List<Integer> idsClasificacionRepuesto, int page, int limit);
	
	public Map<String, Object> consultarProveedores(Proveedor proveedor, int page, int limit);
	
	public Map<String, Object> consultarProveedoresConSolicitudCotizaciones(Proveedor proveedor, Integer idRequerimiento, 
			String fieldSort, Boolean sortDirection, int page, int limit);
	
	//Estados
	public Map<String,Object> ConsultarEstado(Integer page, Integer limit);
		
    //Ciudades

	 
	public Map<String,Object> ConsultarCiudad(Integer idEstado, Integer page, Integer limit);
	  
	//Banco
	public Map<String, Object> consultarBancos(int page, int limit);

}
