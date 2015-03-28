package com.okiimport.app.seguridad.servicios.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.seguridad.dao.HistoryLoginDAO;
import com.okiimport.app.modelo.HistoryLogin;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;
import com.okiimport.app.seguridad.servicios.SHistorial;

public class SHistorialImpl extends AbstractServiceImpl implements SHistorial {

	@Autowired
	@BeanInjector("historyLoginDAO")
	private HistoryLoginDAO historyLoginDAO;
	
	//1. Historial de Session
	@Override
	public void registrarHistorialSession(Usuario usuario){

		HistoryLogin history = this.historyLoginDAO.sessionNoTerminada(usuario.getUsername());
		if(history==null){
			history = new HistoryLogin();
			history.setUsuario(usuario);
			history.setDateLogin(new Timestamp(calendar.getTime().getTime()));
			this.historyLoginDAO.save(history);
		}
	}

	@Override
	public void cerarHistorialSession(Usuario usuario){
		HistoryLogin history = this.historyLoginDAO.sessionNoTerminada(usuario.getUsername());
		if(history!=null){
			history.setDateLogout(new Timestamp(calendar.getTime().getTime()));
			this.historyLoginDAO.update(history);
		}
	}
	
	/**SETTERS Y GETTERS*/
	public HistoryLoginDAO getHistoryLoginDAO() {
		return historyLoginDAO;
	}

	public void setHistoryLoginDAO(HistoryLoginDAO historyLoginDAO) {
		this.historyLoginDAO = historyLoginDAO;
	}

}
