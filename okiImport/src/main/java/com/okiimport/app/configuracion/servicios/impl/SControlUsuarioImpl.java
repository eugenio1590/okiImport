package com.okiimport.app.configuracion.servicios.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.okiimport.app.configuracion.dao.MenuDAO;
import com.okiimport.app.configuracion.dao.UsuarioDAO;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Menu;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;

@Service
public class SControlUsuarioImpl extends AbstractServiceImpl implements SControlUsuario, UserDetailsService {

	@Autowired
	@BeanInjector("bcryptEncoder")
	private BCryptPasswordEncoder bcryptEncoder; //Encriptador de Claves
	
	@Autowired
	@BeanInjector("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	@BeanInjector("usuarioDAO")
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	@BeanInjector("menuDAO")
	private MenuDAO menuDAO;

	public SControlUsuarioImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**Interface: UserDetailsService*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = consultarUsuario(username, null);
		if(usuario!=null){
			if(usuario.getActivo()){
				//Depurar las Autoridades
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
				/*if(usuario.getGroupMembers()!=null)
					for(GroupMember grupoMiembro : usuario.getGroupMembers()){
						Group grupo = grupoMiembro.getGroup();
						if(grupo.getAuthorities()!=null)
							for(Authority authority : grupo.getAuthorities())
								authorities.add(new GrantedAuthorityImpl(authority.getAuthority()));

					}*/
				User securityUser = new User(username, usuario.getPasword(), true, true, true, true,  authorities);
				return securityUser;
			}
			else
				throw new UsernameNotFoundException("Usuario No Activo!!!");
		}
		else
			throw new UsernameNotFoundException("Usuario No Encontrado!!!");
	}
	

	/**Interface: ServicioControlUsuario*/
	//1. Usuarios
	@Override
	public Usuario consultarUsuario(Integer id){
		return usuarioDAO.findByPrimaryKey(id);
	}
	
	@Override
	public Usuario consultarUsuario(String usuario, String clave) {
		// TODO Auto-generated method stub
		return usuarioDAO.consultarUsuario(usuario, clave);
	}

	@Override
	public Usuario grabarUsuario(Usuario usuario, SMaestros sMaestros) {
		// TODO Auto-generated method stub
		//usuario.setPasword(this.bcryptEncoder.encode(usuario.getPasword()));
		Persona persona = usuario.getPersona();
		persona.getTipoMenu();
		usuario = usuarioDAO.save(usuario);
		persona = sMaestros.acutalizarPersona(persona);
		usuario.setPersona(persona);
		return usuario;
	}
	
	@Override
	public Usuario actualizarUsuario(Usuario usuario, boolean encriptar){
		/*if(encriptar)
			usuario.setPasword(this.bcryptEncoder.encode(usuario.getPasword()));*/
		return usuarioDAO.update(usuario);
	}
	
	@Override
	public Boolean cambiarEstadoUsuario(Usuario usuario, boolean estado){
		if((usuario=consultarUsuario(usuario.getId()))!=null) {
			usuario.setActivo(estado);
			actualizarUsuario(usuario, false);
			return true;
		}
		
		return false;
	}
	
	@Override
	public Boolean validarAutenticacion(User user){
		try{
			UsernamePasswordAuthenticationToken auth = consultarAutenticacion(user);
			authenticationManager.authenticate(auth);
			return auth.isAuthenticated();
		}
		catch(Exception e){
			System.out.println("Error en Autenticar: "+e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public UsernamePasswordAuthenticationToken consultarAutenticacion(User user){
		UserDetails userDetails = loadUserByUsername(user.getUsername());
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
	}
	
	@Override
	public Map<String, Object> consultarUsuarios(Usuario usuarioF, String fieldSort, Boolean sortDirection, 
			int pagina, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", usuarioDAO.consultarUsuarios(usuarioF, fieldSort, sortDirection, 0, -1).size());
		parametros.put("usuarios", usuarioDAO.consultarUsuarios(usuarioF, fieldSort, sortDirection, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public boolean verificarUsername(String username){
		Usuario usuario = consultarUsuario(username, null);
		return (usuario!=null);
	}
	
	//2. Menus
	@Override
	public List<Menu> consultarPadresMenuUsuario(Integer tipo) {
		// TODO Auto-generated method stub
		return this.menuDAO.consultarPadresMenuUsuario(tipo);
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	

	/**SETTERS Y GETTERS*/
	public BCryptPasswordEncoder getBcryptEncoder() {
		return bcryptEncoder;
	}

	public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}
}
