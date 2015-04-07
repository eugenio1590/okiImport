package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;

public class RequerimientoDAOImpl extends AbstractJpaDao<Requerimiento, Integer> implements
		RequerimientoDAO {

	public RequerimientoDAOImpl() {
		super(Requerimiento.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Requerimiento> ConsultarRequerimientoUsuario( Requerimiento regFiltro, Integer idusuario,
			int start, int limit) {
		// TODO Auto-generated method stub
		
		//1. Creamos el Criterio de busqueda
				this.crearCriteria();
		
		//2. Generamos los Joins
				Map<String, JoinType> entidades = new HashMap<String, JoinType>();
				entidades.put("analista", JoinType.INNER);
				Map<String, Join> joins = this.crearJoins(entidades );
				
	    //3. Creamos las Restricciones de la busqueda
				List<Predicate> restricciones = new ArrayList<Predicate>();
		
				restricciones.add(criteriaBuilder.equal(joins.get("analista").get("id"),idusuario));
				
	    //4. Creamos los campos de ordenamiento y ejecutamos
				Map<String, Boolean> orders = new HashMap<String, Boolean>();
				orders.put("idRequerimiento", true);	// true ascendente	
				//Falta el filtro
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start,limit);
	}
	
	@Override
	public List<Requerimiento> ConsultarRequerimientosCliente( Requerimiento regFiltro, String cedula,
			int start, int limit) {
		// TODO Auto-generated method stub
		
		//1. Creamos el Criterio de busqueda
				this.crearCriteria();
		
		//2. Generamos los Joins
				Map<String, JoinType> entidades = new HashMap<String, JoinType>();
				entidades.put("cliente", JoinType.INNER);
				Map<String, Join> joins = this.crearJoins(entidades);
				
	    //3. Creamos las Restricciones de la busqueda
				List<Predicate> restricciones = new ArrayList<Predicate>();
		
				restricciones.add(criteriaBuilder.equal(joins.get("cliente").get("cedula"),cedula));
				
	    //4. Creamos los campos de ordenamiento y ejecutamos
				Map<String, Boolean> orders = new HashMap<String, Boolean>();
				orders.put("fechaCreacion", false);	// true ascendente	
				
				//Faltan los filtros
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start,limit);
	}

}

