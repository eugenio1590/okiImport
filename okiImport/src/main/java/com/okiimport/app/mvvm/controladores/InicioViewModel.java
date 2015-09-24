package com.okiimport.app.mvvm.controladores;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;

import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;

public class InicioViewModel extends AbstractRequerimientoViewModel {
	
	@Wire("#principal")
	private Borderlayout principal;
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		registrarRequerimiento();
	}
	
	@Command
	public void registrarProveedor(){
		insertComponent(principal.getPage(), "#mainInclude", BasePackagePortal+"formularioProveedor.zul");
	}
	
	@Command
	public void verificarRequerimiento(){
		insertComponent(principal.getPage(), "#mainInclude", BasePackagePortal+"formularioVerificarRequerimiento.zul");
	}
	
	@Command
	public void registrarRequerimiento(){
		insertComponent(principal.getPage(), "#mainInclude", BasePackagePortal+"formularioRequerimiento.zul");
	}

}
