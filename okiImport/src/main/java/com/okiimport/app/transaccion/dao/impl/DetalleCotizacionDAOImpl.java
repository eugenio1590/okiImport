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
import com.okiimport.app.modelo.Ciudad;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.Estado;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.transaccion.dao.DetalleCotizacionDAO;

public class DetalleCotizacionDAOImpl extends AbstractJpaDao<DetalleCotizacion, Integer> implements DetalleCotizacionDAO {

	public DetalleCotizacionDAOImpl() {
		super(DetalleCotizacion.class);
		// TODO Auto-generated constructor stub
	}

	public List<DetalleCotizacion> ConsultarDetalleCotizacion(int idCotizacion,
			int page, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();

		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("cotizacion", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);

		List<Predicate> restricciones = new ArrayList<Predicate>();

		restricciones.add(criteriaBuilder.equal(
				joins.get("cotizacion").get("idCotizacion"), 
				idCotizacion));
		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();
		orders.add(criteriaBuilder.asc(entity.get("idDetalleCotizacion")));

		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, page, limit);
	}
	
	@Override
	public List<DetalleCotizacion> consultarDetallesCotizacion(DetalleCotizacion detalleF, Integer idCotizacion, Integer idRequerimiento,
			String fieldSort, Boolean sortDirection, int start, int limit) {
		// TODO Auto-generated method stub
		// 1. Creamos el Criterio de busqueda
		this.crearCriteria();
		
		// 2. Generamos los Joins
		Map<String, JoinType> entidades = new HashMap<String, JoinType>();
		entidades.put("cotizacion", JoinType.INNER);
		entidades.put("detalleRequerimiento", JoinType.INNER);
		Map<String, Join> joins = this.crearJoins(entidades);
		
		List<Predicate> restricciones = new ArrayList<Predicate>();

		if(idCotizacion!=null)
			restricciones.add(criteriaBuilder.equal(
					joins.get("cotizacion").get("idCotizacion"), 
					idCotizacion));
		
		if(idRequerimiento!=null)
			restricciones.add(criteriaBuilder.equal(
					joins.get("detalleRequerimiento").join("requerimiento").get("idRequerimiento"), 
					idRequerimiento));
		
		agregarRestricciones(detalleF, restricciones, joins);
		
		// 4. Creamos los campos de ordenamiento y ejecutamos
		List<Order> orders = new ArrayList<Order>();

		if (fieldSort != null && sortDirection != null)
				orders.add((sortDirection) ? this.criteriaBuilder.asc(this.entity.get(fieldSort)) : 
					this.criteriaBuilder.desc(this.entity.get(fieldSort)));
		return ejecutarCriteria(concatenaArrayPredicate(restricciones), orders, start, limit);
	}
	
	private void agregarRestricciones(DetalleCotizacion detalleF, List<Predicate> restricciones, Map<String, Join> joins){
		if(detalleF!=null){
			if(detalleF.getMarcaRepuesto()!=null)
				restricciones.add(criteriaBuilder.like(
						criteriaBuilder.lower(this.entity.get("marcaRepuesto").as(String.class)),
						"%"+String.valueOf(detalleF.getMarcaRepuesto()).toLowerCase()+"%"));
			
			if(detalleF.getCantidad()!=null)
				restricciones.add(criteriaBuilder.like(
						criteriaBuilder.lower(this.entity.get("cantidad").as(String.class)),
						"%"+String.valueOf(detalleF.getCantidad()).toLowerCase()+"%"));
			
			if(detalleF.getPrecioVenta()!=null)
				restricciones.add(criteriaBuilder.like(
						criteriaBuilder.lower(this.entity.get("precioVenta").as(String.class)),
						"%"+String.valueOf(detalleF.getPrecioVenta()).replaceAll(".?0*$", "").toLowerCase()+"%"));
			
			if(detalleF.getPrecioFlete()!=null)
				restricciones.add(criteriaBuilder.like(
						criteriaBuilder.lower(this.entity.get("precioFlete").as(String.class)),
						"%"+String.valueOf(detalleF.getPrecioFlete()).replaceAll(".?0*$", "").toLowerCase()+"%"));
			
			//Cotizacion
			Cotizacion cotizacion = detalleF.getCotizacion();
			if(cotizacion!=null){
				if(cotizacion.getIdCotizacion()!=null)
					restricciones.add(criteriaBuilder.like(
							criteriaBuilder.lower(joins.get("cotizacion").get("idCotizacion").as(String.class)),
							"%"+String.valueOf(cotizacion.getIdCotizacion()).toLowerCase()+"%"));
				
				//Proveedor
				Proveedor proveedor = cotizacion.getProveedor();
				if(proveedor!=null){
					Join joinP = joins.get("cotizacion").join("proveedor");
					if(proveedor.getNombre()!=null)
						restricciones.add(criteriaBuilder.like(
								criteriaBuilder.lower(joinP.get("nombre").as(String.class)),
								"%"+String.valueOf(proveedor.getNombre()).toLowerCase()+"%"));
					
					/**Ubicacion*/
					//Ciudad
					Ciudad ciudadP = proveedor.getCiudad();
					if(ciudadP!=null){
						Join joinC = joinP.join("ciudad");
						
						//Estado
						Estado estadoP = ciudadP.getEstado();
						if(estadoP!=null){
							Join joinE = joinC.join("estado");
							if(estadoP.getNombre()!=null && ciudadP.getNombre()!=null)
								restricciones.add(criteriaBuilder.or(
										criteriaBuilder.like(
												criteriaBuilder.lower(joinC.get("nombre").as(String.class)),
													"%"+String.valueOf(ciudadP.getNombre()).toLowerCase()+"%"),
												criteriaBuilder.like(
														criteriaBuilder.lower(joinE.get("nombre").as(String.class)),
														"%"+String.valueOf(estadoP.getNombre()).toLowerCase()+"%")
										));
							else if(estadoP.getNombre()!=null)
								restricciones.add(criteriaBuilder.like(
										criteriaBuilder.lower(joinE.get("nombre").as(String.class)),
										"%"+String.valueOf(estadoP.getNombre()).toLowerCase()+"%"));
						}
						else {
							if(ciudadP.getNombre()!=null)
								restricciones.add(criteriaBuilder.like(
										criteriaBuilder.lower(joinC.get("nombre").as(String.class)),
										"%"+String.valueOf(ciudadP.getNombre()).toLowerCase()+"%"));
						}
					}
				}
			}
			
			//Detalle Requerimiento
			DetalleRequerimiento detalleR = detalleF.getDetalleRequerimiento();
			if(detalleR!=null){
				if(detalleR.getDescripcion()!=null)
					restricciones.add(criteriaBuilder.like(
							criteriaBuilder.lower(joins.get("detalleRequerimiento").get("descripcion").as(String.class)),
							"%"+String.valueOf(detalleR.getDescripcion()).toLowerCase()+"%"));
			}
		}
	}
}
