package com.okiimport.app.servicios.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.okiimport.app.servicios.SControlUsuario;
import com.okiimport.app.dao.AnalistaDAO;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.mvvm.BeanInjector;

	
	@Service
	public class SControlUsuarioImpl implements SControlUsuario, UserDetailsService {

		@Autowired
		@BeanInjector("analistaDAO")
		private AnalistaDAO analistaDAO;
		
		

		@Override
		public List<Analista> listaAnalistas() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public UserDetails loadUserByUsername(String userName)
				throws UsernameNotFoundException {
            Analista analista = analistaDAO.BuscarAnalistaByUserName(userName);
            
            if (analista != null) {
     
			List<GrantedAuthority> roles = new ArrayList();
			roles.add(new GrantedAuthorityImpl("ROLE_USER"));
			User usuario = new User(userName,analista.getClave(),true,true,true,true, roles);
            	return usuario;
            }
            else throw new UsernameNotFoundException("Usuario No Encontrado"); 
		}
		
		
		
		//METODOS GETTERS AND SETTERS
		public AnalistaDAO getAnalistaDAO() {
			return analistaDAO;
		}

		public void setAnalistaDAO(AnalistaDAO analistaDAO) {
			this.analistaDAO = analistaDAO;
		}
		
		

	}

