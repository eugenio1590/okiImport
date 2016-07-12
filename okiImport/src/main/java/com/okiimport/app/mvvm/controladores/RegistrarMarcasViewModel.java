package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;

import org.zkoss.zul.Window;

import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.model.enumerados.EEstatusGeneral;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarMarcasViewModel extends AbstractRequerimientoViewModel {
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	
	//GUI
	@Wire("#winFormularioMarca")
	private Window winFormularioMarca;
	
	//Atributos
	private MarcaVehiculo marca;
	//private Integer page_size = 10;
	
	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: formularioMarcas.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		super.doAfterCompose(view);
		limpiar();
		
	}
	
	/**
	 * Descripcion: Permite registrar una marca en el sistema
	 * Parametros: @param view: formularioMarcas.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "marca" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar) {
		if (checkIsFormValid()) {
				
				btnEnviar.setDisabled(true);
				btnLimpiar.setDisabled(true);
				
				marca.setEstatus(EEstatusGeneral.ACTIVO);
				marca = sMaestros.registrarMarca(marca);
				

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombre", marca.getNombre());
				
				
				mostrarMensaje("Informaci\u00F3n", "Marca Registrada con Exito", null, null, null, null);
				
			}	
	}
	
	/**
	 * Descripcion: Permite limpiar los campos del formulario Marca
	 * Parametros: @param view: formularioMarcas.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "marca" })
	public void limpiar() {
		marca = new MarcaVehiculo();
		super.cleanConstraintForm();
	}

	/**METODOS PROPIOS DE LA CLASE*/
	
	/**METODOS SETTERS AND GETTERS */
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public MarcaVehiculo getMarca() {
		return marca;
	}

	public void setMarca(MarcaVehiculo marca) {
		this.marca = marca;
	}

	

}
