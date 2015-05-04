package com.okiimport.app.transaccion.servicios.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.dao.CotizacionDAO;
import com.okiimport.app.transaccion.dao.DetalleCotizacionDAO;
import com.okiimport.app.transaccion.dao.DetalleRequerimientoDAO;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class STransaccionImpl extends AbstractServiceImpl implements STransaccion {
	
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

	@Override
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros) {
		// TODO Auto-generated method stub
		Date fechaCreacion = calendar.getTime();
		Date fechaVencimiento = sumarORestarFDia(fechaCreacion, 15);
		asignarRequerimiento(requerimiento, sMaestros);
		requerimiento.setFechaCreacion(fechaCreacion);
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
	public void asignarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros) {
		// TODO Auto-generated method stub
		List<String> estatus=new ArrayList<String>();
		estatus.add("CR");
		estatus.add("R");
		estatus.add("EP");
		estatus.add("CT");
		estatus.add("O");
		List<Analista> analistas = sMaestros.consultarCantRequerimientos(estatus, 0, -1);
		if(analistas.size()>0)
			requerimiento.setAnalista(analistas.get(0));
	}
	
	
	@Override
	public Map<String, Object> ConsultarMisRequerimientos(
			Requerimiento regFiltro,  String fieldSort, Boolean sortDirection, Integer idusuario, int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientoUsuario(regFiltro,fieldSort, sortDirection, idusuario, 0,-1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientoUsuario(regFiltro,fieldSort, sortDirection,idusuario, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> ConsultarRequerimientosCliente (Requerimiento regFiltro, String fieldSort, Boolean sortDirection, String cedula, 
			int pagina, int limit)  
	{
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
			Boolean sortDirection, int idProveedor, int pagina, int limit) {
		// TODO Auto-generated method stub
		List<String> estatus=new ArrayList<String>();
		estatus.add("CT");
		estatus.add("O");
		estatus.add("CC");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", requerimientoDAO.ConsultarRequerimientosConSolicitudesCotizacion(regFiltro, fieldSort, sortDirection, idProveedor, estatus, 0, -1).size());
		parametros.put("requerimientos", requerimientoDAO.ConsultarRequerimientosConSolicitudesCotizacion(regFiltro, fieldSort, sortDirection, idProveedor, estatus, pagina*limit, limit));
		return parametros;

	}

	@Override
	public Map<String, Object> ConsultarDetalleCotizacion( Integer idcotizacion,
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
	

}
