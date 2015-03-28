package com.okiimport.app.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okiimport.app.servicios.SControlInventario;
import com.okiimport.app.dao.RepuestoDAO;
import com.okiimport.app.modelo.Repuesto;
import com.okiimport.app.mvvm.BeanInjector;


@Service
public class SControlInventarioImpl implements SControlInventario {
	
	@Autowired
	@BeanInjector("repuestoDAO")
	private RepuestoDAO repuestoDAO;

	@Override
	public List<Repuesto> listaRepuestos() {
		// TODO Auto-generated method stub
		return repuestoDAO.findAll();
	}
	
	//METODOS SETTERS AND GETTERS
	public RepuestoDAO getRepuestoDAO() {
		return repuestoDAO;
	}

	public void setRepuestoDAO(RepuestoDAO repuestoDAO) {
		this.repuestoDAO = repuestoDAO;
	}
	

}
