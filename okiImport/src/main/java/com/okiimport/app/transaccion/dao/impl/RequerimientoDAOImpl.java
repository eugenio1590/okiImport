package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
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
	public List<Requerimiento> ConsultarRequerimientoUsuario ( Requerimiento regFiltro, String fieldSort, Boolean sortDirection, Integer idusuario,
			int start, int limit) {
		// TODO Auto-generated method stub
		
		//1. Creamos el Criterio de busqueda
				this.crearCriteria();
		
		//2. Generamos los Joins
				Map<String, JoinType> entidades = new HashMap<String, JoinType>();
				entidades.put("analista", JoinType.INNER);
				entidades.put("cliente", JoinType.INNER);
				Map<String, Join> joins = this.crearJoins(entidades );
				
	    //3. Creamos las Restricciones de la busqueda
				List<Predicate> restricciones = new ArrayList<Predicate>();
		
				restricciones.add(criteriaBuilder.equal(joins.get("analista").get("id"),idusuario));
				
	    //4. Creamos los campos de ordenamiento y ejecutamos
				List<Order> orders = new ArrayList<Order>();
				
				
				if ( fieldSort != null && sortDirection!= null)
				{
					if ( fieldSort.equalsIgnoreCase("nombre"))
						orders.add((sortDirection) ? this.criteriaBuilder.asc(joins.get("cliente").get("nombre")) : this.criteriaBuilder.desc(joins.get("cliente").get("nombre")));
					else
						orders.add((sortDirection) ? this.criteriaBuilder.asc(this.entity.get(fieldSort)) : this.criteriaBuilder.desc(this.entity.get(fieldSort)));
					
				}
				
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start,limit);
	}
	
	@Override
	public List<Requerimiento> ConsultarRequerimientosCliente( Requerimiento regFiltro, String fieldSort, Boolean sortDirection, String cedula,
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
				agregarRestriccionesFiltros(restricciones, regFiltro);
				
	    //4. Creamos los campos de ordenamiento y ejecutamos
				Map<String, Boolean> orders = new HashMap<String, Boolean>();
				orders.put("fechaCreacion", false);	// true ascendente	
				
				/**
				 * //4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		
		if(fieldSort!=null && sortDirection!=null)
			orders.put(fieldSort, sortDirection);
		
		orders.put("fechaCreacion", false);
				 * */
				
				//Faltan los filtros
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start,limit);
	}
	
	private void agregarRestriccionesFiltros (List<Predicate> restricciones,Requerimiento regFiltro  )
	{
		
		if (regFiltro != null )
		{
			if ( regFiltro.getIdRequerimiento() != null)
			{
				restricciones.add(this.criteriaBuilder.like(this.entity.get("idRequerimiento").as(String.class),
								"%" + String.valueOf(regFiltro.getIdRequerimiento()) +"%" ));
			}
			
			if (regFiltro.getFechaCreacion() != null)
			{
				restricciones.add(this.criteriaBuilder.like(this.entity.get("fechaCreacion").as(String.class),
						"%" + String.valueOf(regFiltro.getFechaCreacion()) +"%") );
			}

			if (regFiltro.getModeloV() != null)
			{
				restricciones.add(this.criteriaBuilder.like(this.entity.get("modeloV").as(String.class),
						"%" + String.valueOf(regFiltro.getModeloV()) +"%" ) ) ;
			}
			
			if (regFiltro.getSerialCarroceriaV() != null)
			{
				restricciones.add(this.criteriaBuilder.like(this.entity.get("serialCarroceriaV").as(String.class),
						"%" + String.valueOf(regFiltro.getSerialCarroceriaV()) +"%"));
			}
		}
	}

}

