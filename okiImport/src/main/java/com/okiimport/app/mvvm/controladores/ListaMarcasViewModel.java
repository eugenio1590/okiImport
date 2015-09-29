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

import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;

public class ListaMarcasViewModel extends AbstractRequerimientoViewModel implements
		EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;

	// GUI
	@Wire("#gridMarcas")
	private Listbox gridMarcas;

	@Wire("#pagMarcas")
	private Paging pagMarcas;
	
	// Atributos
	// Modelos
	private List<MarcaVehiculo> marcas;
	private MarcaVehiculo marcaFiltro;
	private static final int PAGE_SIZE = 10;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaMarcas.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		super.doAfterCompose(view);
		marcaFiltro = new MarcaVehiculo();
		pagMarcas.setPageSize(PAGE_SIZE);
		agregarGridSort(gridMarcas);
		cambiarMarcas(0, null, null);
	}

	/** Interface: EventListener<SortEvent> */
	@Override
	@NotifyChange("marcas")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub
		if (event.getTarget() instanceof Listheader) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", event.getTarget().getId().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarUsuarios", parametros);
		}

	}

	/** GLOBAL COMMAND */
	/**
	 * Descripcion: Llama a consultar la marca
	 * Parametros: @param view: listaMarcas.zul 
	 * Retorno: Marca consultada
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@NotifyChange("marcas")
	public void cambiarMarcas(@Default("0") @BindingParam("page") int page,
			@BindingParam("fieldSort") String fieldSort,
			@BindingParam("sortDirection") Boolean sortDirection) {
		Map<String, Object> parametros = sMaestros.consultarMarcas(page,
				PAGE_SIZE);//marcaFiltro,
		Integer total = (Integer) parametros.get("total");
		marcas = (List<MarcaVehiculo>) parametros.get("marcas");
		pagMarcas.setActivePage(page);
		pagMarcas.setTotalSize(total);
	}
	

	/** COMMAND */
	/**
	 * Descripcion: Permitira cambiar la paginacion de acuerdo a la pagina activa del Paging 
	 * Parametros: @param view: listaMarcas.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista() {
		int page = pagMarcas.getActivePage();
		cambiarMarcas(page, null, null);
	}


	/**
	 * Descripcion: Llama a un modal para crear o registrar una Marca
	 * Parametros: @param view: listaMarcas.zul 
	 * Retorno: Formulario Marca Cargado para registrar una nueva marca
	 * Nota: Ninguna
	 * */
	@Command
	public void nuevaMarca() {
		llamarFormulario("formularioMarcas.zul", null);
	}

	/**
	 * Descripcion: Llama a un modal para ver los datos de la marca
	 * Parametros: Marca @param view: listaMarcas.zul 
	 * Retorno: Modal cargado con los datos de la marca
	 * Nota: Ninguna
	 * */
	@Command
	public void verMarca(@BindingParam("marcas") MarcaVehiculo marcas){
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("marcas", marcas);
		//parametros.put("editar", false);
		llamarFormulario("formularioMarcas.zul", parametros);
	}
	
	/**
	 * Descripcion: Llama a un modal para editar los datos de la marca
	 * Parametros: Marca @param view: listaMarcas.zul 
	 * Retorno: Modal cargado con los datos de la marca
	 * Nota: Ninguna
	 * */
	@Command
	public void editarMarca(@BindingParam("marcas") MarcaVehiculo marcas){
		
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("marcas", marcas);
			//parametros.put("editar", true);
			llamarFormulario("formularioMarcas.zul", parametros);
	}
	
	/**
	 * Descripcion: Metodo de la clase que permite llamar formularios 
	 * Parametros: @param view: listaMarcas.zul 
	 * Retorno: Formulario con los parametros dados
	 * Nota: Ninguna
	 * */
	private void llamarFormulario(String ruta, Map<String, Object> parametros) {
		crearModal(BasePackageSistemaMaest + ruta, parametros);
	}

	/** METODOS PROPIOS DE LA CLASE */
	/** SETTERS Y GETTERS */

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public List<MarcaVehiculo> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<MarcaVehiculo> marcas) {
		this.marcas = marcas;
	}

	public MarcaVehiculo getMarcaFiltro() {
		return marcaFiltro;
	}

	public void setMarcaFiltro(MarcaVehiculo marcaFiltro) {
		this.marcaFiltro = marcaFiltro;
	}

}
