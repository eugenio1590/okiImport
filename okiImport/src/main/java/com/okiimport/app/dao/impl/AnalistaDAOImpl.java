package com.okiimport.app.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.okiimport.app.dao.AnalistaDAO;
import com.okiimport.app.modelo.Analista;

public class AnalistaDAOImpl extends AbstractJpaDao<Analista, Integer> implements
		AnalistaDAO {

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
				orders.put("idAnalista", true);
				
				List<Analista> analistas = this.ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, 0, -1);
				if(analistas.size()>0)
					return analistas.get(0);
				else
					return null;
	}


}
