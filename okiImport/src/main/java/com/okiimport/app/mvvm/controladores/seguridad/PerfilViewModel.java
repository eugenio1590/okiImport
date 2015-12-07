package com.okiimport.app.mvvm.controladores.seguridad;

import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;

import com.okiimport.app.model.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;


public class PerfilViewModel extends AbstractRequerimientoViewModel {
	
	//Servicios
	
	//GUI
	@Wire("#menInfoUsuario")
	private Menuitem menInfoUsuario;
	
	private Div hrefEnlacesMenu;
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		
		UserDetails user = super.getUser();
		if(user!=null){
			Usuario usuario = sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword(), null);
			menInfoUsuario.setTooltiptext(usuario.getUsername()+" Avatar");
			menInfoUsuario.setLabel(usuario.getUsername().toUpperCase());
		}
	}
	
	@Command
	public void editarPerfil(){
		if(hrefEnlacesMenu==null)
        	hrefEnlacesMenu = (Div) findComponent(menInfoUsuario.getPage(), "#hrefEnlacesMenu");
		
		//Borramos los Hijos
        hrefEnlacesMenu.getChildren().clear();
        
        //Nuevo Enlace del Menu
        Div nuevoEnlace = new Div();
        nuevoEnlace.setSclass("breadcrumb");
        Label label = new Label();
        label.setValue("Editar Perfil");
		nuevoEnlace.appendChild(label);
		
		hrefEnlacesMenu.appendChild(nuevoEnlace);
		
		insertComponent(menInfoUsuario.getPage(), "#mainInclude", BasePackageSistema+"configuracion/editarPerfil.zul");
	}
	
	@Command
	public void logout(){
		redireccionar("/logout");
	}

	//GETTERS Y SETTERS	
}
