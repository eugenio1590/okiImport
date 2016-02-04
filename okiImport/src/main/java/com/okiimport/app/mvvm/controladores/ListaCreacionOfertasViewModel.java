package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.okiimport.app.model.DetalleCotizacion;
import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.mvvm.resource.decorator.ofertas.DecoratorTabOferta;
import com.okiimport.app.mvvm.resource.estrategia.detalles_cotizacion.ResolveEstrategiaSortDetalleCotizacion;
import com.okiimport.app.service.transaccion.STransaccion;
import com.okiimport.app.service.web.SLocalizacion;

@SuppressWarnings("rawtypes")
public class ListaCreacionOfertasViewModel extends AbstractRequerimientoViewModel {
	
	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("sLocalizacion")
	private SLocalizacion sLocalizacion;
	
	//GUI
	@Wire("#tabsOfertas")
	private Tabs tabsOfertas;
	
	@Wire("#tabpOfertas")
	private Tabpanels tabpOfertas;
	
	//Atributos
	private ResolveEstrategiaSortDetalleCotizacion resolve;
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
		this.resolve = new ResolveEstrategiaSortDetalleCotizacion(sLocalizacion);
		crearOfertas();
	}
	
	/**COMMAND*/
	@Command
	public void cancelar(){
		
	}
	
	@Command
	public void enviarCliente(){
		
	}
	
	@Command
	@NotifyChange({"ofertas"})
	public void aprobar(@BindingParam("detalleOferta") DetalleOferta detalleOferta,
			@BindingParam("decorator") DecoratorTabOferta decorator,
			@BindingParam("row") Listitem row,
			@BindingParam("buttons") Div buttons,
			@BindingParam("button") Button button){
		detalleOferta.setAprobado(true);
		decorator.updateButton(row, buttons, button, true);
	}
	
	@Command
	@NotifyChange({"ofertas"})
	public void invalidar(@BindingParam("detalleOferta") DetalleOferta detalleOferta,
			@BindingParam("decorator") DecoratorTabOferta decorator,
			@BindingParam("row") Listitem row,
			@BindingParam("buttons") Div buttons,
			@BindingParam("button") Button button){
		detalleOferta.setAprobado(false);
		decorator.updateButton(row, buttons, button, false);
	}
	
	/**METODOS OVERRIDE*/
	@Override
	@Command
	public void closeModal(){
		super.closeModal();
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	@SuppressWarnings("unchecked")
	private void crearOfertas() {
		int pos = cantOfertas + 1; //rectificar calculo con los estatus
		Oferta oferta;
		List<DetalleCotizacion> detallesCotizacion;
		List<DetalleRequerimiento> keys = new ArrayList<DetalleRequerimiento>();
		ofertas = new ArrayList<Oferta>();
		listasDetalleCotizacion = sTransaccion.consultarDetallesCotizacion(requerimiento.getIdRequerimiento());
		
		while(ofertas.size()<3 && pos<=6){
			//1. Creamos una oferta nueva
			oferta = new Oferta(ofertas.size()+1);
			
			//2. Actualizamos el ordenamiento de cada una de las listas
			for(DetalleRequerimiento key: listasDetalleCotizacion.keySet()){
				detallesCotizacion = listasDetalleCotizacion.get(key);
				this.resolve.resolveEstrategia(detallesCotizacion, pos);
				
				//2.1 Agregamos la mejor opcion a la oferta respectiva
				if(detallesCotizacion.size()>0){
					oferta.addDetalleOferta(new DetalleOferta(detallesCotizacion.get(0)));
					
					//2.1.1 Removemos la mejor oferta de la lista
					detallesCotizacion.remove(0);
					
					//2.1.2 Verificamos el vacio de la lista y Actualizamos
					if(detallesCotizacion.isEmpty())
						keys.add(key);
				}
			}
			
			//3. Eliminamos los keys del map
			if(!keys.isEmpty())
				for(DetalleRequerimiento key:keys)
					listasDetalleCotizacion.remove(key);
			
			//4. Agregamos la nueva oferta a la lista de ofertas
			if(oferta.isNotEmpty())
				this.ofertas.add(oferta);
			
			//5. Actualizamos la pos de la estrategia a usar en la siguiente iteracion
			pos++;
		}
		
		DecoratorTabOferta decorator;
		if(!ofertas.isEmpty())
			for(Oferta oferta2 : ofertas){
				decorator = new DecoratorTabOferta(tabsOfertas, tabpOfertas, oferta2);
				decorator.agregarOferta(this);
			}
		
//		detallesOfertas = new ArrayList<DetalleOferta>();
//		listasDetalleCotizacion = sTransaccion.consultarDetallesCotizacion(requerimiento.getIdRequerimiento());
//		for(DetalleRequerimiento key: listasDetalleCotizacion.keySet()){
//			Oferta oferta = new Oferta();
//			for(DetalleCotizacion detallleC : listasDetalleCotizacion.get(key)){
//				DetalleOferta detalleOferta = new DetalleOferta(detallleC);
//				oferta.addDetalleOferta(detalleOferta );
//				detallesOfertas.add(detalleOferta);
//			}
//			ofertas.add(oferta);
//		}
	}

	/**GETTERS Y SETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public SLocalizacion getsLocalizacion() {
		return sLocalizacion;
	}

	public void setsLocalizacion(SLocalizacion sLocalizacion) {
		this.sLocalizacion = sLocalizacion;
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
