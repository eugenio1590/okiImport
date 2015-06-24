package com.okiimport.app.transaccion.servicios.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleCotizacionInternacional;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.dao.CotizacionDAO;
import com.okiimport.app.transaccion.dao.DetalleCotizacionDAO;
import com.okiimport.app.transaccion.dao.DetalleCotizacionInternacionalDAO;
import com.okiimport.app.transaccion.dao.DetalleRequerimientoDAO;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class STransaccionImpl extends AbstractServiceImpl implements STransaccion {
	
	private static List<String> ESTATUS_EMITIDOS;
	private static List<String> ESTATUS_PROCESADOS;
	
	@Autowired
	@BeanInjector("cotizacionDAO")
	private CotizacionDAO cotizacionDAO;
	
	@Autowired
	@BeanInjector("requerimientoDAO")
	private RequerimientoDAO requerimientoDAO;
	
	@Autowired
	@BeanInjector("detalleRequerimientoDAO")
	private DetalleRequerimientoDAO detalleRequerimientoDAO;
	
	@Autowired
	@BeanInjector("detalleCotizacionDAO")
	private DetalleCotizacionDAO detalleCotizacionDAO;
	
	@Autowired
	@BeanInjector("detalleCotizacionInternacionalDAO")
	private DetalleCotizacionInternacionalDAO detalleCotizacionInternacionalDAO;

	public STransaccionImpl() {
		super();
		
		ESTATUS_EMITIDOS = new ArrayList<String>();
		ESTATUS_EMITIDOS.add("CR");
		ESTATUS_EMITIDOS.add("E");
		ESTATUS_EMITIDOS.add("EP");
		
		ESTATUS_PROCESADOS = new ArrayList<String>();
		ESTATUS_PROCESADOS.add("CT");
		ESTATUS_PROCESADOS.add("O");
		ESTATUS_PROCESADOS.add("CC");
	}

	public DetalleRequerimientoDAO getDetalleRequerimientoDAO() {
		return detalleRequerimientoDAO;
	}

	public void setDetalleRequerimientoDAO(DetalleRequerimientoDAO detalleRequerimientoDAO) {
		this.detalleRequerimientoDAO = detalleRequerimientoDAO;
	}

	public RequerimientoDAO getRequerimientoDAO() {
		return requerimientoDAO;
	}

	public void setRequerimientoDAO(RequerimientoDAO requerimientoDAO) {
		this.requerimientoDAO = requerimientoDAO;
	}

	public CotizacionDAO getCotizacionDAO() {
		return cotizacionDAO;
	}

	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public DetalleCotizacionDAO getDetalleCotizacionDAO() {
		return detalleCotizacionDAO;
	}

	public void setDetalleCotizacionDAO(DetalleCotizacionDAO detalleCotizacionDAO) {
		this.detalleCotizacionDAO = detalleCotizacionDAO;
	}

	public DetalleCotizacionInternacionalDAO getDetalleCotizacionInternacionalDAO() {
		return detalleCotizacionInternacionalDAO;
	}

	public void setDetalleCotizacionInternacionalDAO(
			DetalleCotizacionInternacionalDAO detalleCotizacionInternacionalDAO) {
		this.detalleCotizacionInternacionalDAO = detalleCotizacionInternacionalDAO;
	}

	@Override
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros) {
		// TODO Auto-generated method stub
		Date fechaCreacion = calendar.getTime();
		Date fechaVencimiento = sumarORestarFDia(fechaCreacion, 15);
		asignarRequerimiento(requerimiento, sMaestros);
		requerimiento.setFechaCreacion(new Timestamp(fechaCreacion.getTime()));
		requerimiento.setFechaVencimiento(fechaVencimiento);
		requerimiento.setEstatus("CR");
		for(DetalleRequerimiento detalle:requerimiento.getDetalleRequerimientos())
			detalle.setEstatus("activo");
		return requerimiento = requerimientoDAO.save(requerimiento);
	}
	
	@Override
	public Requerimiento actualizarRequerimiento(Requerimiento requerimiento){
		return this.requerimientoDAO.update(requerimiento);
	}
	
	@Override
	public void guardarSeleccionRequerimiento(
			DetalleCotizacion detalleCotizacion) {
		// TODO Auto-generated method stub
		detalleCotizacion.setEstatus("seleccionado");
		this.detalleCotizacionDAO.update(detalleCotizacion);
	}

	@Override
	public void asignarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros) {
		// TODO Auto-generated method stub
		List<String> estatus=new ArrayList<String>();
		estatus.addAll(ESTATUS_EMITIDOS);
//		estatus.add("CR");
//		estatus.add("E");
//		estatus.add("EP");
		estatus.add("CT");
		estatus.add("O");
		List<Analista> analistas = sMaestros.consultarCantRequerimientos(estatus, 0, 1);
		if(analistas.size()>0)
			requerimiento.setAnalista(analistas.get(0));
	}
	
	@Override
	public Map<String, Object> consultarRequerimientosGeneral(Requerimiento regFiltro, String fieldSort, Boolean sortDirection,
			int pagina, int limit){
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", Long.valueOf(requerimientoDAO.countAll()).intValue());
		parametros.put("requerimientos", requerimientoDAO.findAll(pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> consultarMisRequerimientosEmitidos(
			Requerimiento regFiltro,  String fieldSort, Boolean sortDirection, Integer idusuario, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientoUsuario(regFiltro,fieldSort, sortDirection, idusuario, ESTATUS_EMITIDOS, 0,-1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientoUsuario(regFiltro,fieldSort, sortDirection,idusuario, ESTATUS_EMITIDOS, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> consultarMisRequerimientosProcesados(
			Requerimiento regFiltro,  String fieldSort, Boolean sortDirection, Integer idusuario, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientoUsuario(regFiltro,fieldSort, sortDirection, idusuario, ESTATUS_PROCESADOS, 0,-1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientoUsuario(regFiltro,fieldSort, sortDirection,idusuario, ESTATUS_PROCESADOS, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> ConsultarRequerimientosCliente (Requerimiento regFiltro, String fieldSort, 
			Boolean sortDirection, String cedula, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientosCliente(regFiltro,fieldSort, sortDirection, cedula, 0,-1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientosCliente(regFiltro,fieldSort, sortDirection, cedula, pagina*limit, limit));
		return parametros;
	}

	@Override
	public Map<String, Object> RequerimientosCotizados(
			Requerimiento regFiltro,  String fieldSort, Boolean sortDirection, Integer idusuario, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientosCotizados(regFiltro,fieldSort, sortDirection, idusuario, 0,-1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientosCotizados(regFiltro,fieldSort, sortDirection,idusuario, pagina*limit, limit));
		return parametros;
	}

	@Override
	public Map<String, Object> ConsultarCotizacionesRequerimiento(Cotizacion cotFiltro,
			String fieldSort, Boolean sortDirection, Integer idrequerimiento,
			int pagina, int limit) {
		// TODO Auto-generated method stub
		List<String> estatus=new ArrayList<String>();
		estatus.add("C");
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", cotizacionDAO.consultarCotizacionesAsignadas(cotFiltro, fieldSort, sortDirection, idrequerimiento,estatus, 0, -1).size());
		parametros.put("cotizaciones", cotizacionDAO.consultarCotizacionesAsignadas(cotFiltro, fieldSort, sortDirection, idrequerimiento,estatus, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> ConsultarRequerimientosConSolicitudesCotizacion(Requerimiento regFiltro, String fieldSort, 
			Boolean sortDirection, Integer idProveedor, int pagina, int limit) {
		// TODO Auto-generated method stub
		List<String> estatus=new ArrayList<String>();
		estatus.add("O");
		estatus.add("CC");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientosConSolicitudesCotizacion(regFiltro, fieldSort, sortDirection, idProveedor, estatus, 0, -1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientosConSolicitudesCotizacion(regFiltro, fieldSort, sortDirection, idProveedor, estatus, pagina*limit, limit));
		return parametros;

	}

	@Override
	public Map<String, Object> ConsultarDetalleCotizacion(Integer idcotizacion,
			int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", detalleCotizacionDAO.ConsultarDetalleCotizacion(idcotizacion, 0, -1).size());
		parametros.put("detalleCotizacion", detalleCotizacionDAO.ConsultarDetalleCotizacion(idcotizacion, pagina*limit, limit));
        return parametros;
	}

	@Override
	public Cotizacion ActualizarCotizacion(Cotizacion cotizacion) {
		// TODO Auto-generated method stub
		return cotizacionDAO.update(cotizacion);
	}
	
	//Cotizaciones
	@Override
	public Map<String, Object> consultarSolicitudCotizaciones(Cotizacion cotizacionF, String fieldSort, Boolean sortDirection,
			Integer idRequerimiento, int idProveedor, int pagina, int limit){
		List<String> estatus=new ArrayList<String>();
		estatus.add("SC");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", cotizacionDAO.consultarSolicitudCotizaciones(cotizacionF, fieldSort, sortDirection, idRequerimiento, idProveedor, estatus, 0, -1).size());
		parametros.put("cotizaciones", cotizacionDAO.consultarSolicitudCotizaciones(cotizacionF, fieldSort, sortDirection, idRequerimiento, idProveedor, estatus, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Cotizacion registrarCotizacion(Cotizacion cotizacion) {
		// TODO Auto-generated method stub
		cotizacion.setEstatus("C");
		List<DetalleCotizacion> detalles = cotizacion.getDetalleCotizacions();
		cotizacion = cotizacionDAO.update(cotizacion);
		for(DetalleCotizacion detalle : detalles){
			this.detalleCotizacionDAO.update(detalle);
			DetalleRequerimiento detalleRequerimiento = detalle.getDetalleRequerimiento();
			
			detalleRequerimiento.setEstatus("CT");
			this.detalleRequerimientoDAO.update(detalleRequerimiento);
		
			Requerimiento requerimiento = detalleRequerimiento.getRequerimiento();
			requerimiento.setEstatus("CT");
			this.requerimientoDAO.update(requerimiento);
		}
		return cotizacion;
	}

	@Override
	public Cotizacion registrarSolicitudCotizacion(Cotizacion cotizacion, List<DetalleCotizacion> detalleCotizacions) {
		// TODO Auto-generated method stub
		cotizacion.setEstatus("SC");
		cotizacion.setFechaCreacion(calendar.getTime());
		cotizacion = cotizacionDAO.save(cotizacion);
		for(DetalleCotizacion detalleCotizacion : detalleCotizacions){
			detalleCotizacion.getDetalleRequerimiento().setEstatus("EP");
			detalleCotizacion.setCotizacion(cotizacion); 
			this.detalleCotizacionDAO.save(detalleCotizacion);
			
			DetalleRequerimiento detalleRequerimiento = detalleCotizacion.getDetalleRequerimiento();
			detalleRequerimiento.setEstatus("EP");
			this.detalleRequerimientoDAO.update(detalleRequerimiento);
			
			Requerimiento requerimiento = detalleRequerimiento.getRequerimiento();
			if(requerimiento.getFechaSolicitud()==null){
				requerimiento.setEstatus("EP");
				requerimiento.setFechaSolicitud(new Timestamp(Calendar.getInstance().getTime().getTime()));
				this.requerimientoDAO.update(requerimiento);
			}
			
		}
		cotizacion.setDetalleCotizacions(detalleCotizacions);
		return cotizacion;
	}
	
	//Detalles Cotizacion
	@Override
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacion detalleF, int idCotizacion, 
			String fieldSort, Boolean sortDirection, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", detalleCotizacionDAO.consultarDetallesCotizacion(detalleF, idCotizacion, null, false, true, fieldSort, sortDirection, 0, -1).size());
		parametros.put("detallesCotizacion", detalleCotizacionDAO.consultarDetallesCotizacion(detalleF, idCotizacion, null, false, true, fieldSort, sortDirection, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacion detalleF, Integer idRequerimiento,
			String fieldSort, Boolean sortDirection, int pagina, int limit){
		boolean nuloCantidad = false;
		detalleF.getCotizacion().setEstatus("C");
		if(detalleF.getCantidad()==null){
			nuloCantidad = true;
			detalleF.setCantidad(new Long(0));
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", detalleCotizacionDAO.consultarDetallesCotizacion(detalleF, null, idRequerimiento, true, false, fieldSort, sortDirection, 0, -1).size());
		parametros.put("detallesCotizacion", detalleCotizacionDAO.consultarDetallesCotizacion(detalleF, null, idRequerimiento, true, false, fieldSort, sortDirection, pagina*limit, limit));
		if(nuloCantidad)
			detalleF.setCantidad(null);
		return parametros;
	}
	
		//Internacional
	@Override
	public Map<String, Object> consultarDetallesCotizacion(DetalleCotizacionInternacional detalleF, int idCotizacion, 
			String fieldSort, Boolean sortDirection, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", detalleCotizacionInternacionalDAO.consultarDetallesCotizacion(detalleF, idCotizacion, null, false, true, fieldSort, sortDirection, 0, -1).size());
		parametros.put("detallesCotizacion", detalleCotizacionInternacionalDAO.consultarDetallesCotizacion(detalleF, idCotizacion, null, false, true, fieldSort, sortDirection, pagina*limit, limit));
		return parametros;
	}
}
