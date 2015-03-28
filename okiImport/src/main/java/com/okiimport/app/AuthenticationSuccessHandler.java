package com.okiimport.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.okiimport.app.dao.configuracion.UsuarioDAO;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.servicios.seguridad.SHistorial;


@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private SHistorial sHistorial;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	/**METODOS OVERRIDE*/
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
    		Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioDAO.consultarUsuario(user.getUsername(), user.getPassword());
		sHistorial.registrarHistorialSession(usuario);
		setDefaultTargetUrl("/admin/home");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
