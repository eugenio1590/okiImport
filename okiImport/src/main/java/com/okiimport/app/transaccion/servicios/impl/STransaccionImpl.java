package com.okiimport.app.transaccion.servicios.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.transaccion.dao.RequerimientoDAO;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class STransaccionImpl extends AbstractServiceImpl implements STransaccion {
	
	@Autowired
	@BeanInjector("requerimientoDAO")
	private RequerimientoDAO requerimientoDAO;

	public RequerimientoDAO getRequerimientoDAO() {
		return requerimientoDAO;
	}

	public void setRequerimientoDAO(RequerimientoDAO requerimientoDAO) {
		this.requerimientoDAO = requerimientoDAO;
	}

	@Override
	public Requerimiento registrarRequerimiento(Requerimiento requerimiento, SMaestros sMaestros) {
		// TODO Auto-generated method stub
		asignarRequerimiento(requerimiento, sMaestros);
		requerimiento.setFechaCreacion(new Timestamp(calendar.getTime().getTime()));
		requerimiento.setEstatus("CR");
		return requerimientoDAO.save(requerimiento);
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

}
