package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import com.okiimport.app.modelo.Compra;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class RegistrarSolicitudPedidoViewModel extends AbstractRequerimientoViewModel 
{
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	 
	//GUI
	@Wire("#winCompras")
	private Window winCompras;
	
	//Atributos
	private Requerimiento requerimiento;

    private Compra compra;
    
    private List<ModeloCombo<Boolean>> listaTipoFlete;
    
    private ModeloCombo<Boolean> tipoFlete;
    
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("detallesOfertas") List<DetalleOferta> detallesOfertas)
	{	
		super.doAfterCompose(view);	
		this.compra = new Compra(requerimiento, this.calendar.getTime());
		this.compra.setDetalleOfertas(detallesOfertas);
		this.requerimiento = requerimiento;
		llenarTiposFlete();
		limpiar();
	}
	
	/**COMMAND*/
	@Command
	@NotifyChange({"compra","tipoFlete"})
	public void limpiar(){
		this.compra.setObservacion(null);
		this.tipoFlete = listaTipoFlete.get(0);
	}
	
	@Command
	public void registrar(){
		if(this.checkIsFormValid()){
			this.compra = this.sTransaccion.registrarCompra(compra);
			this.compra.setTipoFlete(tipoFlete.getValor());
			this.winCompras.onClose();
		}
	}

	/**METODOS PROPIOS DE LA CLASE*/
	private void llenarTiposFlete(){
		listaTipoFlete = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoFlete.add(new ModeloCombo<Boolean>("No", false));
		listaTipoFlete.add(new ModeloCombo<Boolean>("Si", true));
	}
	
	/**GETTERS Y SETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<ModeloCombo<Boolean>> getListaTipoFlete() {
		return listaTipoFlete;
	}

	public void setListaTipoFlete(List<ModeloCombo<Boolean>> listaTipoFlete) {
		this.listaTipoFlete = listaTipoFlete;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public ModeloCombo<Boolean> getTipoFlete() {
		return tipoFlete;
	}

	public void setTipoFlete(ModeloCombo<Boolean> tipoFlete) {
		this.tipoFlete = tipoFlete;
	}   
}