package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Compra;
import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarSolicitudPedidoViewModel extends AbstractRequerimientoViewModel {
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	 
	//GUI
	@Wire("#winCompras")
	private Window winCompras;
	@Wire("#cmbFlete")
	Combobox cmbFlete;
	
	@Wire Label lblFlete;
	private Float flete;
	
	//Atributos
	private Requerimiento requerimiento;
    private Compra compra;
    private List<ModeloCombo<Boolean>> listaTipoFlete;
    private ModeloCombo<Boolean> tipoFlete;
    private List<ModeloCombo<Boolean>> listaFormaPago;
    private ModeloCombo<Boolean> formaPago;
    private boolean cerrar = false;
    
    /**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: formularioSolicituddePedido.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("detallesOfertas") List<DetalleOferta> detallesOfertas)
	{	
		super.doAfterCompose(view);	
		this.compra = new Compra(requerimiento, this.calendar.getTime());
		this.compra.setDetalleOfertas(detallesOfertas);
		this.requerimiento = requerimiento;
		flete = (float) 0.0;
		llenarTiposFlete();
		llenarFormaPago();
		limpiar();
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: Permite limpiar los campos del formulario registrar solicitud de pedido
	 * Parametros: @param view: formularioSolicituddePedido.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"compra","tipoFlete", "formaPago"})
	public void limpiar(){
		this.compra.setObservacion(null);
		this.tipoFlete = listaTipoFlete.get(0);
		this.formaPago = listaFormaPago.get(0);
		this.flete = (float) 0.00;
		super.cleanConstraintForm();
	}
	
	/**
	 * Descripcion: Permite Registrar la solicitud de pedido
	 * Parametros: @param view: formularioSolicituddePedido.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void registrar(){
		if(this.checkIsFormValid()){
			compra.setTipoFlete(tipoFlete.getValor());
			compra = sTransaccion.registrarCompra(compra, requerimiento, true);
			cerrarVentana();
		}
	}
	/**
	 * Descripcion: Permitira 
	 * Parametros: @param parametros: parametros a pasar al .zul
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 */
	@Command
	@NotifyChange({"flete"})
	public void seleccionar(){
		if(cmbFlete.getValue().equals("Si"))
			//flete = compra.calcularFlete();
			;
		else
			flete = (float) 0.00;
	}
	/**
	 * Descripcion: Evento que se ejecuta al cerrar la ventana y que valida si el proceso actual de la compra se perdera o no
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@Override
	public void closeModal(){
		if(!cerrar){
			super.mostrarMensaje("Informaci\u00F3n", "Si cierra la ventana el proceso realizado se perdera, �Desea continuar?", null, 
					new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){
				@Override
				public void onEvent(Event event) throws Exception {
					Messagebox.Button button = (Messagebox.Button) event.getData();
					if (button == Messagebox.Button.YES) {
						//REAJUSTAR ESTATUS DE LAS OFERTAS
						ejecutarGlobalCommand("cambiarRequerimientos", null);
						cerrarVentana();
					}
				}
			}, null);
		}
		else {
			ejecutarGlobalCommand("cambiarRequerimientos", null);
			super.closeModal();
		}
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	/**
	 * Descripcion: Permite llenar la lista con los tipo de flete
	 * Parametros: @param view: formularioSolicituddePedido.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void llenarTiposFlete(){
		listaTipoFlete = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoFlete.add(new ModeloCombo<Boolean>("No", false));
		listaTipoFlete.add(new ModeloCombo<Boolean>("Si", true));
	}
	/**METODOS PROPIOS DE LA CLASE*/
	/**
	 * Descripcion: Permite llenar la lista con los tipo de forma de pago
	 * Parametros: @param view: formularioSolicituddePedido.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void llenarFormaPago(){
		listaFormaPago = new ArrayList<ModeloCombo<Boolean>>();
		listaFormaPago.add(new ModeloCombo<Boolean>("Seleccione", false));		
		listaFormaPago.add(new ModeloCombo<Boolean>("Mercado Pago", true));		
		listaFormaPago.add(new ModeloCombo<Boolean>("Tarjeta de crédito", true));		
//		listaFormaPago.add(new ModeloCombo<Boolean>("Tarjeta de débito", true));		
	}
	/**
	 * Descripcion: metodo que actualiza la variable cerrar y llama al comman respectivo al cerrar la ventana.
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void cerrarVentana(){
		cerrar = true;
		closeModal();
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

	public List<ModeloCombo<Boolean>> getListaFormaPago() {
		return listaFormaPago;
	}

	public void setListaFormaPago(List<ModeloCombo<Boolean>> listaFormaPago) {
		this.listaFormaPago = listaFormaPago;
	}

	public ModeloCombo<Boolean> getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(ModeloCombo<Boolean> formaPago) {
		this.formaPago = formaPago;
	}

	public Float getFlete() {
		return flete;
	}

	public void setFlete(Float flete) {
		this.flete = flete;
	}   
	
	@Command
	public void abrirInterfazPago(Map<String, Object> paramets){
		final Float c = this.compra.calcularTotal();
		super.mostrarMensaje("Informaci\u00F3n", "Desea registrar el pago de la factura de productos?", null, 
				new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){
			
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.Button button = (Messagebox.Button) event.getData();
				if (button == Messagebox.Button.YES) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("totalFactura", c);
					crearModal(BasePackagePortal+"formularioFormaPago.zul", parametros);
				}else
					closeModal();
			}
		}, null);
	}
	
}
