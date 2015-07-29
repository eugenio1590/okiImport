package com.okiimport.app.maestros.dao;

import java.util.List;

import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Proveedor;

public interface ProveedorDAO extends PersonaDAO<Proveedor> {
	public List<Proveedor> consultarProveedoresSinUsuarios(Persona persona,  String fieldSort, Boolean sortDirection, 
			int start, int limit);
	public List<Proveedor> consultarProveedoresListaClasificacionRepuesto(Persona persona, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, List<Integer> idsClasificacionRepuesto, int start, int limit);
	public List<Proveedor> consultarProveedoresConSolicitudCotizaciones(Proveedor proveedor, Integer idRequerimiento, 
			String fieldSort, Boolean sortDirection, int start, int limit);
}
