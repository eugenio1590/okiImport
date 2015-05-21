package com.okiimport.app.transaccion.dao.impl;

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

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;

public class RequerimientoDAOImpl extends
		AbstractJpaDao<Requerimiento, Integer> implements RequerimientoDAO {

	public RequerimientoDAOImpl() {
		super(Requerimiento.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Requerimiento> ConsultarRequerimientoUsuario(
			Requerimiento regFiltro, String fieldSort, Boolean sortDirection,
			Integer idusuario, int start, int limit) {
		// TODO Auto-generated method stub

		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("analista", JoinType.INNER);
		entidades.put("cliente", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
				joins.get("analista").get("id"), idusuario));
		agregarRestriccionesFiltros(restricciones, regFiltro, joins);

		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null) {
			if (fieldSort.equalsIgnoreCase("nombre"))
				orders.add((sortDirection) ? this.criteriaBuilder.asc(joins
						.get("cliente").get("nombre")) : this.criteriaBuilder
						.desc(joins.get("cliente").get("nombre")));
			else
				orders.add((sortDirection) ? this.criteriaBuilder
						.asc(this.entity.get(fieldSort)) : this.criteriaBuilder
						.desc(this.entity.get(fieldSort)));

		}

		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders,
				start, limit);
	}

	@Override
	public List<Requerimiento> ConsultarRequerimientosCliente(
			Requerimiento regFiltro, String fieldSort, Boolean sortDirection,
			String cedula, int start, int limit) {
		// TODO Auto-generated method stub

		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("cliente", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
				joins.get("cliente").get("cedula"), cedula));
		agregarRestriccionesFiltros(restricciones, regFiltro, joins);

		// 4. Creamos los campos de ordenamiento y ejecutamos
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("fechaCreacion", false); // true ascendente

		/**
		 * //4. Creamos los campos de ordenamiento y ejecutamos Map<String,
		 * Boolean> orders = new HashMap<String, Boolean>();
		 * 
		 * if(fieldSort!=null && sortDirection!=null) orders.put(fieldSort,
		 * sortDirection);
		 * 
		 * orders.put("fechaCreacion", false);
		 * */

		// Faltan los filtros
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders,
				start, limit);
	}

	private void agregarRestriccionesFiltros(List<Predicate> restricciones,
			Requerimiento regFiltro, Map<String, Join> joins) {

		if (regFiltro != null) {
			if (regFiltro.getIdRequerimiento() != null) {
				restricciones.add(this.criteriaBuilder.like(
						this.entity.get("idRequerimiento").as(String.class),
						"%" + String.valueOf(regFiltro.getIdRequerimiento()) + "%"));
			}

			if (regFiltro.getFechaCreacion() != null) {
				restricciones.add(this.criteriaBuilder.like(
						this.entity.get("fechaCreacion").as(String.class),
						"%" + dateFormat.format(regFiltro.getFechaCreacion()) + "%"));
			}

			if (regFiltro.getModeloV() != null) {
				restricciones.add(this.criteriaBuilder.like(
						this.criteriaBuilder.lower(this.entity.get("modeloV").as(String.class)),
						"%" + String.valueOf(regFiltro.getModeloV()).toLowerCase() + "%"));
			}
			
			if(regFiltro.getFechaVencimiento() != null){
				restricciones.add(this.criteriaBuilder.like(
						this.entity.get("fechaVencimiento").as(String.class), 
						"%"+ dateFormat.format(regFiltro.getFechaVencimiento()) +"%"));
			}
			
			if(regFiltro.getSerialCarroceriaV() != null){
				restricciones.add(this.criteriaBuilder.like(
						this.criteriaBuilder.lower(this.entity.get("serialCarroceriaV").as(String.class)),
						"%" + String.valueOf(regFiltro.getSerialCarroceriaV()).toLowerCase() + "%"));
			}
			
			if (regFiltro.getEstatus() != null)
			{
				restricciones.add(this.criteriaBuilder.like(this.entity.get("estatus").as(String.class), 
						"%" + regFiltro.getEstatus() + "%"));
			}
			
			if(joins!=null){
				//Cliente
				Cliente cliente = regFiltro.getCliente();
				if (cliente != null && joins.get("cliente") != null) {
					if (cliente.getNombre() != null)
						restricciones.add(this.criteriaBuilder.like(
								this.criteriaBuilder.lower(joins.get("cliente").get("nombre").as(String.class)),
								"%"+ String.valueOf(cliente.getNombre()).toLowerCase() + "%"));
				}
				
				//Marca Vehiculo
				MarcaVehiculo marcaVehiculo = regFiltro.getMarcaVehiculo();
				if(marcaVehiculo!=null && joins.get("marcaVehiculo") != null){
					if(marcaVehiculo.getNombre()!=null)
						restricciones.add(this.criteriaBuilder.like(
								this.criteriaBuilder.lower(joins.get("marcaVehiculo").get("nombre").as(String.class)), 
								"%"+String.valueOf(marcaVehiculo.getNombre()).toLowerCase()+"%"));
				}
			}
		}
	}

	@Override
	public List<Requerimiento> ConsultarRequerimientosCotizados(
			Requerimiento regFiltro, String fieldSort, Boolean sortDirection,
			Integer idusuario, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("analista", JoinType.INNER);
		entidades.put("cliente", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		// 3. Creamos las Restricciones de la busqueda
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
				joins.get("analista").get("id"), idusuario));
		restricciones.add(criteriaBuilder.equal(entity.get("estatus"), "CT"));
		agregarRestriccionesFiltros(restricciones, regFiltro, joins);

		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null) {
			if (fieldSort.equalsIgnoreCase("nombre"))
				orders.add((sortDirection) ? this.criteriaBuilder.asc(joins
						.get("cliente").get("nombre")) : this.criteriaBuilder
						.desc(joins.get("cliente").get("nombre")));
			else
				orders.add((sortDirection) ? this.criteriaBuilder
						.asc(this.entity.get(fieldSort)) : this.criteriaBuilder
						.desc(this.entity.get(fieldSort)));

		}

		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders,
				start, limit);

	}

	@Override
	public List<Requerimiento> ConsultarRequerimientosConSolicitudesCotizacion(Requerimiento regFiltro, String fieldSort, 
			Boolean sortDirection, int idProveedor, List<String> estatus, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();
		
		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("analista", JoinType.INNER);
		entidades.put("cliente", JoinType.INNER);
		entidades.put("detalleRequerimientos", JoinType.INNER);
		entidades.put("marcaVehiculo", JoinType.LEFT);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		// 3. Creamos las Restricciones de la busqueda y los campos a seleccionar
		this.distinct = true;
		this.selected=new Selection[]{
				this.entity.get("idRequerimiento"),
				this.entity.get("estatus"),
				this.entity.get("fechaCreacion"),
				this.entity.get("fechaVencimiento"),
				this.entity.get("modeloV"),
				this.entity.get("analista"),
				this.entity.get("cliente"),
				this.entity.get("marcaVehiculo")
		};
		
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(this.criteriaBuilder.equal(
				joins.get("detalleRequerimientos").join("detalleCotizacions").join("cotizacion").join("proveedor").get("id"), 
				idProveedor));
		restricciones.add(this.criteriaBuilder.not(this.entity.get("estatus").in(estatus)));
		agregarRestriccionesFiltros(restricciones, regFiltro, joins);
		
		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null) {
			if (fieldSort.equalsIgnoreCase("nombre"))
				orders.add((sortDirection) ? this.criteriaBuilder.asc(joins
						.get("cliente").get("nombre")) : this.criteriaBuilder
						.desc(joins.get("cliente").get("nombre")));
			else
				orders.add((sortDirection) ? this.criteriaBuilder
						.asc(this.entity.get(fieldSort)) : this.criteriaBuilder
						.desc(this.entity.get(fieldSort)));

		}
		
		
		List<Expression<?>> groupBy = new ArrayList<Expression<?>>();
		groupBy.add(entity.get("idRequerimiento"));
		groupBy.add(entity.get("estatus"));
		groupBy.add(entity.get("fechaCreacion"));
		groupBy.add(entity.get("fechaVencimiento"));
		groupBy.add(entity.get("modeloV"));
		groupBy.add(entity.get("analista"));
		groupBy.add(entity.get("cliente"));
		groupBy.add(entity.get("marcaVehiculo"));
		return this.ejecutarCriteriaOrder(concatenaArrayPredicate(restricciones), null, groupBy , orders, start, limit);
	}

}