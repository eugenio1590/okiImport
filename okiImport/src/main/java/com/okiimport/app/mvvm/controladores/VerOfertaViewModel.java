package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class VerOfertaViewModel extends AbstractRequerimientoViewModel 
{
	
	@Wire("#winOferta")
	private Window winOferta;
	
	private Requerimiento requerimiento;

    private Oferta oferta;
    
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
    
    private List<DetalleOferta> listaDetOferta;
    
    
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento, 
			@ExecutionArgParam("detallesOfertas") List<DetalleOferta> listaDetOferta )
	{	
		super.doAfterCompose(view);	
		this.requerimiento = requerimiento;
		this.listaDetOferta = (listaDetOferta == null) ? new ArrayList<DetalleOferta>() : listaDetOferta;
		cargarOferta();
	}
	
	/**GLOBAL COMMAND*/
	@Command
	@NotifyChange("oferta")
	public void cargarOferta(){
		oferta = sTransaccion.consultarOfertaEnviadaPorRequerimiento(requerimiento.getIdRequerimiento());
	}
	
	/**COMMAND*/
	@Command
	@NotifyChange({ "oferta" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar) {		
		if ( checkIsFormValid() ) {
			
			oferta.setEstatus("recibida");
			llenarListAprobados();
			oferta = sTransaccion.actualizarOferta(oferta);

			//sTransaccion.actualizarRequerimiento(requerimiento);  Falta definir estatus

			cargarOferta();
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("requerimiento", requerimiento);
			parametros.put("detallesOfertas", listaDetOferta);


			if (oferta != null)
			{
				//redireccionar
				ejecutarGlobalCommand("verOferta", parametros);
				this.winOferta.onClose();
			}
			else
			{
				//antes cerrar formulario de oferta
				this.winOferta.onClose();
				llamarFormulario("formularioSolicituddePedido.zul", parametros);
			}
		}
	}
	
	@Command
	public void aprobarDetalleOferta(@ContextParam(ContextType.COMPONENT) Checkbox checkbox,
			@BindingParam("detalleOferta") DetalleOferta detalleOferta){
		detalleOferta.setAprobado(checkbox.isChecked());
	}
	
	/**METODOS PRIVADOS DE LA CLASE*/
	private void llenarListAprobados() {
		for ( DetalleOferta detalleOferta : this.oferta.getDetalleOfertas()){
			if (detalleOferta.getAprobado() != null && detalleOferta.getAprobado()){
				listaDetOferta.add(detalleOferta);
			}
		}
	}
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/"+ruta, parametros);
	}
	
	/**GETTERS Y SETTERS*/
	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
}
