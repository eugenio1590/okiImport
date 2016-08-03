package com.okiimport.app.mvvm.controladores;

import java.util.Date;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Pago;
import com.okiimport.app.model.PagoCliente;
import com.okiimport.app.model.enumerados.EEstatusGeneral;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;
import com.okiimport.app.service.web.SPago;

public class RegistrarPagoFacturaEfectivoViewModel extends AbstractRequerimientoViewModel{
	
	//Servicios
	@BeanInjector("sPago")
	private SPago sPago;
		
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
		
	//GUI
	@Wire("#winPagoFacturaEfect")
	private Window winPagoFactura;	
	@Wire
	private Textbox decMonto;
	@Wire
	private Textbox txtTitular;
	@Wire
	private Datebox dateFechaPago;
	@Wire
	private Doublebox doubMonto;
	@Wire
	private Textbox txtObservaciones;
		
	private CustomConstraint constraintCampoObligatorio;
		
	private Pago pago;
		
	private boolean cerrar = false;

	private CustomConstraint constraintMensaje;
	

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: verDetalleOferta.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@NotifyChange({"totalFactura"})
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("pago")  Pago pago){
		super.doAfterCompose(view);
		this.pago = pago;
		dateFechaPago.setValue(new Date());
	}

	/**COMMAND*/
	@Command
	public void registrarPago(@BindingParam("btnEnviar") Button button){
		if(checkIsFormValid()){
			if(validacionesParaGuardar() == true){
				Boolean exito = sPago.registrarPagoEfectivo(llenarPago());
				if(exito){
					mostrarMensaje("Informaci\u00F3n", "�Operacion registrada exitosamente!", Messagebox.INFORMATION, null, null, null);
					//limpiar();
					this.winPagoFactura.onClose();
				}
				else 
					mostrarMensaje("Error", "�El Pago no pudo realizarse, intente de nuevo!", Messagebox.ERROR, null, null, null);
			}
		}
	}
	
	private PagoCliente llenarPago() {
		PagoCliente pago = new PagoCliente();
		pago.setFechaPago(new Date());
		pago.setMonto((Float.valueOf((decMonto.getValue()))));
		pago.setEstatus(EEstatusGeneral.ACTIVO.name());
		pago.setDescripcion(txtObservaciones.getValue());
		//pago.setFormaPago(formaPago); CORREGIR
		return pago;
	}

	public boolean validacionesParaGuardar(){
		boolean guardar = false;
		if(Float.valueOf(decMonto.getValue()) != 0 && txtTitular.getValue() != ""){
			if(Float.valueOf(decMonto.getValue()) > 0)
				guardar = true;
			else
				mostrarMensaje("Error", "¡El monto total a pagar debe ser mayor a cero!", null, null, null, null);
		}else{
			mostrarMensaje("Error", "¡Debe ingresar todos los campos!", null, null, null, null);
		}
		return guardar;
	}
	
	@Command
	public void limpiar(){
		this.txtTitular.setValue("");
		this.txtObservaciones.setValue("");
		this.decMonto.setValue("");
		super.cleanConstraintForm();
	}
	
	/**GETTERS Y SETTERS*/

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public SPago getsPago() {
		return sPago;
	}

	public void setsPago(SPago sPago) {
		this.sPago = sPago;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public CustomConstraint getConstraintCampoObligatorio() {
		return constraintCampoObligatorio;
	}

	public void setConstraintCampoObligatorio(CustomConstraint constraintCampoObligatorio) {
		this.constraintCampoObligatorio = constraintCampoObligatorio;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public boolean isCerrar() {
		return cerrar;
	}

	public void setCerrar(boolean cerrar) {
		this.cerrar = cerrar;
	}

	public CustomConstraint getConstraintMensaje() {
		return constraintMensaje;
	}

	public void setConstraintMensaje(CustomConstraint constraintMensaje) {
		this.constraintMensaje = constraintMensaje;
	}
	

}
