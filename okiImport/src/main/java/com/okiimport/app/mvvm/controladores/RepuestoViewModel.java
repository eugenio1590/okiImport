package com.okiimport.app.mvvm.controladores;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;

import com.okiimport.app.mvvm.AbstractViewModel;


public class RepuestoViewModel extends AbstractViewModel {

	@AfterCompose
	public void doAfterCompose(Component view){
		super.doAfterCompose(view);
	}
	
	@Command
	public void iniciarSession(){
		this.redireccionar("/inicioSession");
	}

	//METODOS SETTERS AND GETTERS
	
}
