package com.okiimport.app.maestros.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;

import com.okiimport.app.maestros.dao.ProveedorDAO;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Proveedor;

public class ProveedorDAOImpl extends PersonaDAOImpl<Proveedor> implements ProveedorDAO {

	public ProveedorDAOImpl() {
		super(Proveedor.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Proveedor> consultarProveedoresSinUsuarios(Persona persona,  String fieldSort, Boolean sortDirection, 
			int start, int limit) {
		// TODO Auto-generated method stub
		Proveedor proveedor = (persona==null) ? new Proveedor() : new Proveedor(persona);
		return consultarPersonaSinUsuarios(proveedor, fieldSort, sortDirection, start, limit);
	}
	
	@Override
	public List<Proveedor> consultarProveedoresListaClasificacionRepuesto(Persona persona, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, List<Integer> idsClasificacionRepuesto, int start, int limit) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();

		//2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("clasificacionRepuestos", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		//3. Creamos las Restricciones de la busqueda
		this.distinct = true;
		this.selected = new Selection[]{
				entity.get("id"),
				entity.get("cedula"),
				entity.get("correo"),
				entity.get("direccion"),
				entity.get("nombre"),
				entity.get("telefono"),
				entity.get("estatus"),
				entity.get("tipoProveedor")
		};
		
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(joins.get("clasificacionRepuestos").get("idClasificacionRepuesto").in(idsClasificacionRepuesto));
		
		entidades = new HashMap<String, JoinType>();
		entidades.put("proveedor", JoinType.INNER);
		entidades.put("detalleCotizacions", JoinType.INNER);
		Map<String, Object> paramSubquery = createSubquery(Cotizacion.class, "idCotizacion", entidades);
		Subquery<Cotizacion> subQuCotizacion = (Subquery<Cotizacion>) paramSubquery.get("subquery");
		Map<String, Join> joinsSubquery = (Map<String, Join>) paramSubquery.get("joins");
		
		Join joinDetalleRequerimiento = joinsSubquery.get("detalleCotizacions").join("detalleRequerimiento");
		Join joinProveedor = joinsSubquery.get("proveedor");
		
		List<Predicate> restriccionesSubquery = new ArrayList<Predicate>();
		
		restriccionesSubquery.add(this.criteriaBuilder.equal(
				joinDetalleRequerimiento.join("requerimiento").get("idRequerimiento"),
				idRequerimiento
		));
		
		restriccionesSubquery.add(this.criteriaBuilder.equal(joinProveedor.get("id"), this.entity.get("id")));
		restriccionesSubquery.add(joinDetalleRequerimiento.join("clasificacionRepuesto", JoinType.LEFT)
			.in(idsClasificacionRepuesto));
		
		subQuCotizacion=addRestriccionesSubquery(subQuCotizacion, concatenaArrayPredicate(restriccionesSubquery));
		
		restricciones.add(this.criteriaBuilder.not(this.criteriaBuilder.exists(subQuCotizacion)));
		
		Proveedor proveedor = (persona==null) ? new Proveedor() : new Proveedor(persona);
		proveedor.setEstatus("activo");
		agregarFiltros(proveedor, restricciones);
		
		//4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();
		orders.add(criteriaBuilder.asc(entity.get("id")));
		
		return this.ejecutarCriteriaOrder(concatenaArrayPredicate(restricciones), null, null, orders, start, limit);
	}
	
	@Override
	public List<Proveedor> consultarProveedoresConSolicitudCotizaciones(Proveedor proveedor, Integer idRequerimiento, 
			String fieldSort, Boolean sortDirection, int start, int limit) {
		// TODO Auto-generated method stub
		//1. Creamos el Criterio de busqueda
		this.crearCriteria();

		//2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("cotizacions", JoinType.INNER);
		entidades.put("usuario", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		//3. Creamos las Restricciones de la busqueda
		this.distinct = true;
		this.selected = new Selection[]{
				entity.get("id"),
				entity.get("cedula"),
				entity.get("correo"),
				entity.get("direccion"),
				entity.get("nombre"),
				entity.get("telefono"),
				entity.get("estatus"),
				entity.get("tipoProveedor")
		};
		
		List<Predicate> restricciones = new ArrayList<Predicate>();
		restricciones.add(this.criteriaBuilder.isNotEmpty(this.entity.<List>get("cotizacions")));
		restricciones.add(this.criteriaBuilder.equal(joins.get("cotizacions").get("estatus"), "SC"));
		restricciones.add(this.criteriaBuilder.equal(
				joins.get("cotizacions").join("detalleCotizacions").join("detalleRequerimiento")
				.join("requerimiento").get("idRequerimiento"),
				idRequerimiento));
		
		
		proveedor = (proveedor==null) ? new Proveedor() : proveedor;
		proveedor.setEstatus("activo");
		agregarFiltros(proveedor, restricciones);
		
		//4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();
		
		if(fieldSort!=null && sortDirection!=null){
			Path<Object> field = this.entity.get(fieldSort);
			orders.add((sortDirection) ? this.criteriaBuilder.asc(this.entity.get(fieldSort)) : this.criteriaBuilder.desc(field));
		}else
			orders.add(criteriaBuilder.asc(entity.get("id")));
		
		return this.ejecutarCriteriaOrder(concatenaArrayPredicate(restricciones), null, null, orders, start, limit);
	}

	@Override
	protected void agregarRestriccionesPersona(Proveedor personaF, List<Predicate> restricciones) {
		// TODO Auto-generated method stub
	}

	
}
