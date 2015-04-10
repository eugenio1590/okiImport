package com.okiimport.app.maestros.servicios;

import java.util.Map;

import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Proveedor;

public interface SMaestros {
	
	public Map<String,Object> ConsultarMarca(Integer page,Integer limit);
	
	public Map<String,Object> ConsultarClasificacionRepuesto(Integer page,Integer limit);
	
	public Cliente registrarCliente(Cliente cliente);
	
	public Proveedor registrarProveedor(Proveedor proveedor);
	
	public Map<String,Object> ConsultarMotor(Integer page,Integer limit);

}
