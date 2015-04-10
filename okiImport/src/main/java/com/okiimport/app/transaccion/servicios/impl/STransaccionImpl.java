package com.okiimport.app.transaccion.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.dao.DetalleRequerimientoDAO;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class STransaccionImpl extends AbstractServiceImpl implements
		STransaccion {
	
	@Autowired
	@BeanInjector("requerimientoDAO")
	private RequerimientoDAO requerimientoDAO;
	
	@Autowired
	@BeanInjector("detalleRequerimientoDAO")
	private DetalleRequerimientoDAO detalleRequerimientoDAO;

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

	@Override
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento) {
		// TODO Auto-generated method stub
		Requerimiento requerimientoT = requerimientoDAO.save(requerimiento);
		for(DetalleRequerimiento detalle:requerimiento.getDetalleRequerimientos()){
			detalle.setRequerimiento(requerimientoT);
			detalle.setEstatus("activo");
			detalleRequerimientoDAO.save(detalle);
		}
		return requerimientoT;
	}

}
