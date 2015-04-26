package com.okiimport.app.transaccion.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.okiimport.app.dao.impl.AbstractJpaDao;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.transaccion.dao.CotizacionDAO;

public class CotizacionDAOImpl extends AbstractJpaDao<Cotizacion, Integer> implements
		CotizacionDAO {

	public CotizacionDAOImpl() {
		super(Cotizacion.class);
		// TODO Auto-generated constructor stub
	}



}
