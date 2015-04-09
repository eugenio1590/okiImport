package com.okiimport.app.transaccion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Requerimiento;

public interface RequerimientoDAO extends IGenericDao<Requerimiento, Integer> {
	
	List <Requerimiento> ConsultarRequerimientoUsuario(Requerimiento regFiltro, String fieldSort, Boolean sortDirection,Integer idusuario, int start, int limit);
	
	List <Requerimiento> ConsultarRequerimientosCliente(Requerimiento regFiltro, String fieldSort, Boolean sortDirection, String cedula, int start, int limit);
	

}
