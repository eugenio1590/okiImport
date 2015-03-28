package com.okiimport.app.configuracion.dao;

import java.util.List;

import com.okiimport.app.dao.IGenericDao;
import com.okiimport.app.modelo.Usuario;

public interface UsuarioDAO extends IGenericDao<Usuario, Integer> {
	public Usuario consultarUsuario(String usuario, String clave);
	public List<Usuario> consultarUsuarios(Usuario usuarioF, String fieldSort, Boolean sortDirection, 
			int pagina, int limit);
}
