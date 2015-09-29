package com.okiimport.app.mvvm.controladores;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;

import com.okiimport.app.model.Cliente;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;
import com.okiimport.app.service.transaccion.STransaccion;

public class VerDetalleRequerimientoViewModel extends AbstractRequerimientoViewModel  {

	//Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	//Atributos
	private List <Requerimiento> listaRequerimientos;
	private List <MarcaVehiculo> listaMarcasVehiculo;
	private Requerimiento requerimiento;
	private Cliente cliente;
	
	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view:  
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("requerimiento") Requerimiento requerimiento)
	{
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		List<DetalleRequerimiento> detallesRequerimiento 
			= (List<DetalleRequerimiento>) sTransaccion
				.consultarDetallesRequerimiento(requerimiento.getIdRequerimiento(), 0, -1).get("detallesRequerimiento");
		this.requerimiento.setDetalleRequerimientos(detallesRequerimiento);
	}
	
	
	/** METODOS PROPIOS DE LA CLASE */

	/** GETTERS Y SETTERS */
	
	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}
	
	public List<MarcaVehiculo> getListaMarcasVehiculo() {
		return listaMarcasVehiculo;
	}

	public void setListaMarcasVehiculo(List<MarcaVehiculo> listaMarcasVehiculo) {
		this.listaMarcasVehiculo = listaMarcasVehiculo;
	}
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
}
