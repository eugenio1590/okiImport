package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.GroupsModelArray;

import com.okiimport.app.model.DetalleCotizacion;
import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class ListaCreacionOfertasViewModel extends AbstractRequerimientoViewModel {
	
	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	//Atributos
	private Map<DetalleRequerimiento, List<DetalleCotizacion>> listasDetalleCotizacion; //Servicio
	private List<DetalleOferta> detallesOfertas;
	private List<Oferta> ofertas;
	private Requerimiento requerimiento;
	
	private int cantOfertas;
	
	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento) {
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.cantOfertas = sTransaccion.consultarCantOfertasCreadasPorRequermiento(requerimiento.getIdRequerimiento());
		crearOfertas();
	}
	
	/**COMMAND*/
	@Command
	public void cancelar(){
		
	}
	
	@Command
	public void enviarCliente(){
		
	}
	
	/**METODOS OVERRIDE*/
	@Override
	@Command
	public void closeModal(){
		super.closeModal();
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	private void crearOfertas() {
		ofertas = new ArrayList<Oferta>();
		detallesOfertas = new ArrayList<DetalleOferta>();
		listasDetalleCotizacion = sTransaccion.consultarDetallesCotizacion(requerimiento.getIdRequerimiento());
		for(DetalleRequerimiento key: listasDetalleCotizacion.keySet()){
			Oferta oferta = new Oferta();
			for(DetalleCotizacion detallleC : listasDetalleCotizacion.get(key)){
				DetalleOferta detalleOferta = new DetalleOferta(detallleC);
				oferta.addDetalleOferta(detalleOferta );
				detallesOfertas.add(detalleOferta);
			}
			ofertas.add(oferta);
		}
	}

	/**GETTERS Y SETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public List<DetalleOferta> getDetallesOfertas() {
		return detallesOfertas;
	}

	public void setDetallesOfertas(List<DetalleOferta> detallesOfertas) {
		this.detallesOfertas = detallesOfertas;
	}

	
	
	
}
