package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;

import com.okiimport.app.model.Analista;
import com.okiimport.app.model.Cliente;
import com.okiimport.app.model.Vehiculo;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;

public class ListaMisVehiculosViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{

	
	//Servicios
		@BeanInjector("sMaestros")
		private SMaestros sMaestros;
		
		//GUI
		@Wire("#gridMisVehiculos")
		private Listbox gridMisVehiculos;
		
		@Wire("#pagMisVehiculos")
		private Paging pagMisVehiculos;
		
		//Modelos
		private List<Vehiculo> vehiculos;
		private Vehiculo vehiculoFiltro;
		
		/**
		 * Descripcion: Llama a inicializar la clase 
		 * Parametros: @param view: listaMisVehiculos.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
			super.doAfterCompose(view);
			vehiculoFiltro = new Vehiculo();
			pagMisVehiculos.setPageSize(pageSize);
			agregarGridSort(gridMisVehiculos);
			cambiarVehiculos(0, null, null);
		}
		
		
		/**Interface: EventListener<SortEvent>*/
		@Override
		@NotifyChange("vehiculos")
		public void onEvent(SortEvent event) throws Exception {
			// TODO Auto-generated method stub		
			if(event.getTarget() instanceof Listheader){
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("fieldSort",  event.getTarget().getId().toString());
				parametros.put("sortDirection", event.isAscending());
				ejecutarGlobalCommand("cambiarVehiculos", parametros );
			}
			
		}
		
		/**GLOBAL COMMAND*/
		/**
		 * Descripcion: Llama a consultar analistas  
		 * Parametros: @param view: listaMisVehiculos.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@GlobalCommand
		@NotifyChange("vehiculos")
		public void cambiarVehiculos(@Default("0") @BindingParam("page") int page, 
				@BindingParam("fieldSort") String fieldSort, 
				@BindingParam("sortDirection") Boolean sortDirection){
			Map<String, Object> parametros = sMaestros.consultarVehiculos(new Cliente(), page, pageSize);
			Integer total = (Integer) parametros.get("total");
			vehiculos = (List<Vehiculo>) parametros.get("vehiculos");
			pagMisVehiculos.setActivePage(page);
			pagMisVehiculos.setTotalSize(total);
		}     
		
		/**
		 * Descripcion: Llama a cerrar a vista
		 * Parametros: @param view: listaMisVehiculos.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@Command
		@NotifyChange("*")
		public void cerrarvista(){
			
			cambiarVehiculos(0, null, null);
		}
		
		/**COMMAND*/
		/**
		 * Descripcion: Permitira cambiar la paginacion de acuerdo a la pagina activa del Paging 
		 * Parametros: @param view: listaAnalistas.zul  
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@Command
		@NotifyChange("*")
		public void paginarLista(){
			int page=pagMisVehiculos.getActivePage();
			cambiarVehiculos(page, null, null);
		}
		
	

}
