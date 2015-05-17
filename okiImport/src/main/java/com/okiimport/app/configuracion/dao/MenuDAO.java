package com.okiimport.app.configuracion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Menu;

public interface MenuDAO extends IGenericDao<Menu, Integer> {
	public List<Menu> consultarPadresMenuUsuario(Integer tipo);
}
