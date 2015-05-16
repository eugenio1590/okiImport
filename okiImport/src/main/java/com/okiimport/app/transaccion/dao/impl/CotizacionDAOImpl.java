package com.okiimport.app.transaccion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.transaccion.dao.CotizacionDAO;

public class CotizacionDAOImpl extends AbstractJpaDao<Cotizacion, Integer> implements
		CotizacionDAO {

	public CotizacionDAOImpl() {
		super(Cotizacion.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Cotizacion> consultarCotizacionesAsignadas(Cotizacion cotizacion, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento,List<String> estatus, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("detalleCotizacions", JoinType.INNER);
		entidades.put("proveedor", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		// 3. Creamos las Restricciones de la busqueda
		this.distinct=true;
		this.selected=new Selection[]{
				this.entity.get("idCotizacion"),
				this.entity.get("fechaCreacion"),
				this.entity.get("fechaVencimiento"),
				this.entity.get("estatus"),
				this.entity.get("mensaje"),
				this.entity.get("proveedor"),
		};
		List<Predicate> restricciones = new ArrayList<Predicate>();
		AgregarFiltro(cotizacion,restricciones,joins);
		restricciones.add(criteriaBuilder.equal(
					joins.get("detalleCotizacions").join("detalleRequerimiento").join("requerimiento").get("idRequerimiento"), 
					idRequerimiento));
		restricciones.add(entity.get("estatus").in(estatus));

		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null) 
			orders.add((sortDirection) ? this.criteriaBuilder.asc(this.entity.get(fieldSort)) : 
				this.criteriaBuilder.desc(this.entity.get(fieldSort)));
				
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}

	private void AgregarFiltro(Cotizacion cotizacionF,List<Predicate> restricciones,Map<String, Join> joins){
		if (cotizacionF != null){
			if (cotizacionF.getIdCotizacion() != null){
				restricciones.add(criteriaBuilder.like(criteriaBuilder.lower(this.entity.get("idCotizacion").as(String.class)), "%"+String.valueOf(cotizacionF.getIdCotizacion()).toLowerCase()+"%"));
			}
			if (cotizacionF.getFechaCreacion() != null){
				restricciones.add(criteriaBuilder.like(criteriaBuilder.lower(this.entity.get("fechaCreacion").as(String.class)), "%"+dateFormat.format(cotizacionF.getFechaCreacion()).toLowerCase()+"%"));
			}
			if (cotizacionF.getFechaVencimiento() != null){
				restricciones.add(criteriaBuilder.like(criteriaBuilder.lower(this.entity.get("fechaVencimiento").as(String.class)), "%"+dateFormat.format(cotizacionF.getFechaVencimiento()).toLowerCase()+"%"));
			}
			if(joins.get("proveedor") != null && cotizacionF.getProveedor() != null){
				if(cotizacionF.getProveedor().getNombre() != null){
					restricciones.add(criteriaBuilder.like(criteriaBuilder.lower(joins.get("proveedor").get("nombre").as(String.class)), "%"+String.valueOf(cotizacionF.getProveedor().getNombre()).toLowerCase()+"%"));
				}
			}
		}
	}
	
	@Override
	public List<Cotizacion> consultarSolicitudCotizaciones(Cotizacion cotizacion, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int idProveedor, List<String> estatus, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("detalleCotizacions", JoinType.INNER);
		entidades.put("proveedor", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		// 3. Creamos las Restricciones de la busqueda
		this.distinct=true;
		this.selected=new Selection[]{
				this.entity.get("idCotizacion"),
				this.entity.get("fechaCreacion"),
				this.entity.get("fechaVencimiento"),
				this.entity.get("estatus"),
				this.entity.get("mensaje"),
				this.entity.get("proveedor")
		};
		
		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
				joins.get("detalleCotizacions").join("detalleRequerimiento").join("requerimiento").get("idRequerimiento"), 
				idRequerimiento));
		
		restricciones.add(criteriaBuilder.equal(joins.get("proveedor").get("id"), idProveedor));
		restricciones.add(entity.get("estatus").in(estatus));

		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null)
				orders.add((sortDirection) ? this.criteriaBuilder.asc(this.entity.get(fieldSort)) : 
					this.criteriaBuilder.desc(this.entity.get(fieldSort)));

		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}
}