package com.okiimport.app.configuracion.servicios;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Menu;
import com.okiimport.app.modelo.Usuario;

public interface SControlUsuario {
	//Usuarios
	public Usuario consultarUsuario(Integer id);
	public Usuario consultarUsuario(String usuario, String clave);
	public Usuario grabarUsuario(Usuario usuario, SMaestros smaestros);
	public Usuario actualizarUsuario(Usuario usuario, boolean encriptar);
	public Boolean cambiarEstadoUsuario(Usuario usuario, boolean estado);
	public Boolean validarAutenticacion(User user);
	public UsernamePasswordAuthenticationToken consultarAutenticacion(User user);
	public Map<String, Object> consultarUsuarios(Usuario usuarioF, String fieldSort, Boolean sortDirection, 
			int pagina, int limit);
	public boolean verificarUsername(String username);
	public Usuario consultarUsuario(int idPersona);
	
	//Menu
	public List<Menu> consultarPadresMenuUsuario(Integer tipo);
}
