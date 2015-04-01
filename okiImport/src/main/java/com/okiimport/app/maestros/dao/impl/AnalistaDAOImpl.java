package com.okiimport.app.maestros.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import com.okiimport.app.maestros.dao.AnalistaDAO;
import com.okiimport.app.modelo.Analista;

public class AnalistaDAOImpl extends PersonaDAOImpl<Analista> implements AnalistaDAO {

	public AnalistaDAOImpl() {
		super(Analista.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Analista BuscarAnalistaByUserName(String userName) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();

		//2. Generamos los Joins
		this.crearJoins(null);

		//3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();

		if(userName!=null)
			restricciones.add(this.criteriaBuilder.equal(
					this.criteriaBuilder.lower(this.entity.<String>get("usuario")), userName.toLowerCase()
					));

		//4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("id", true);

		List<Analista> analistas = this.ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, 0, -1);
		if(analistas.size()>0)
			return analistas.get(0);
		else
			return null;
	}
	
	@Override
	public List<Analista> consultarAnalistasSinUsuarios(int start, int limit){
		return super.consultarPersonaSinUsuarios(start, limit);
	}

	@Override
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int start, int limit) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();
		
		//2. Generamos los Joins
		Map<String, JoinType> entidades=new HashMap<String, JoinType>();
		entidades.put("requerimientos", JoinType.LEFT);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		 //3. Creamos los campos a seleccionar
		Expression<Long> cantRequerimientos = this.criteriaBuilder.countDistinct(joins.get("requerimientos"));
		
		this.selected=new Selection[]{
				cantRequerimientos.alias("cantRequerimientos"),
				this.entity.get("id")
		};
		
		//4. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(
				this.criteriaBuilder.or(
						joins.get("requerimientos").get("estatus").in(estatus),
						this.criteriaBuilder.isEmpty(this.entity.<List>get("requerimientos"))
					)
		);
		
		 //5. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();
		orders.add(this.criteriaBuilder.asc(cantRequerimientos));

		 List<Expression<?>> groupBy = new ArrayList<Expression<?>>();
		 groupBy.add(this.entity.get("id"));
		 
		 //5. Creamos los campos de ordenamiento y ejecutamos
		 return this.ejecutarCriteriaGroupBy(concatenaArrayPredicate(restricciones), null, groupBy , orders);
	}


}
