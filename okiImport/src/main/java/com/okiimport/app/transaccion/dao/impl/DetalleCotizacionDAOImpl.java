package com.okiimport.app.transaccion.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleRequerimiento;
import com.okiimport.app.transaccion.dao.DetalleCotizacionDAO;

public class DetalleCotizacionDAOImpl extends AbstractJpaDao<DetalleCotizacion, Integer> implements
		DetalleCotizacionDAO {
	
	public DetalleCotizacionDAOImpl() {
		super(DetalleCotizacion.class);
		// TODO Auto-generated constructor stub
	}

	

}
