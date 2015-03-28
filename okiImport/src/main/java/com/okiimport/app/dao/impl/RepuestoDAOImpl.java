package com.okiimport.app.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.okiimport.app.dao.RepuestoDAO;
import com.okiimport.app.modelo.Repuesto;

@Repository
public class RepuestoDAOImpl extends AbstractJpaDao<Repuesto, Integer> implements
		RepuestoDAO {

	public RepuestoDAOImpl() {
		super(Repuesto.class);
		// TODO Auto-generated constructor stub
	}

	
}
