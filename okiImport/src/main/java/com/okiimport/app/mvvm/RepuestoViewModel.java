package com.okiimport.app.mvvm;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import com.okiimport.app.servicios.SControlInventario;
import com.okiimport.app.modelo.Repuesto;


public class RepuestoViewModel extends AbstractViewModel {
	
	@BeanInjector("sControlInventario")
	
	private SControlInventario sControlInventario;
	private List<Repuesto> listaRepuestos;	

	@AfterCompose
	public void doAfterCompose(Component view){
		super.doAfterCompose(view);
		listaRepuestos = sControlInventario.listaRepuestos();
	}
	
	@Command
	public void grabar(){
		Executions.sendRedirect("grabar");
	}

	//METODOS SETTERS AND GETTERS
	public SControlInventario getsControlInventario() {
		return sControlInventario;
	}

	public void setsControlInventario(SControlInventario sControlInventario) {
		this.sControlInventario = sControlInventario;
	}
	
	public List<Repuesto> getListaRepuestos() {
		return listaRepuestos;
	}

	public void setListaRepuestos(List<Repuesto> listaRepuestos) {
		this.listaRepuestos = listaRepuestos;
	}

}
