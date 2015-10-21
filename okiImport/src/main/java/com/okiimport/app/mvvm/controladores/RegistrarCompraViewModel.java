package com.okiimport.app.mvvm.controladores;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Compra;
import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarCompraViewModel extends AbstractRequerimientoViewModel {
	
    //Servicios   
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
    
    //GUI
    @Wire("#winCompras")
	private Window winCompras;
    
  	@Wire("#gridDetallesCompra")
  	private Listbox gridDetallesCompra;
  	
  	@Wire("#pagDetallesCompra")
  	private Paging pagDetallesCompra;
    
    //Atributos
  	private List<DetalleOferta> listaDetallesCompra;
    private Requerimiento requerimiento;
    private Compra compra;
    
    /**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: formularioCompra.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("compra") Compra compra)
	{	
		super.doAfterCompose(view);	
		this.requerimiento = requerimiento;
		this.compra = compra;
		pagDetallesCompra.setPageSize(pageSize);
		agregarGridSort(gridDetallesCompra);
		cambiarDetallesCompra(0);
	}
	
	/**GLOBAL COMMAND*/
	/**
	 * Descripcion: Permitira cambiar los requerimientos de la grid de acuerdo a la pagina dada como parametro
	 * Parametros: @param view: formularioCompra.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaDetallesCompra")
	public void cambiarDetallesCompra(@Default("0") @BindingParam("page") int page){
		Map<String, Object> parametros = sTransaccion.consultarDetallesCompra(compra.getIdCompra(), null, null, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaDetallesCompra = (List<DetalleOferta>) parametros.get("detallesCompra");
		pagDetallesCompra.setActivePage(page);
		pagDetallesCompra.setTotalSize(total);
	}
	
	/**COMMAND*/
	
	 /**
	 * Descripcion: Permitira cambiar la paginacion de acuerdo a la pagina activa del Paging 
	 * Parametros: @param view: formularioCompra.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagDetallesCompra.getActivePage();
		cambiarDetallesCompra(page);
	}
	
	 /**
		 * Descripcion: Permite Registrar la compra 
		 * Parametros: @param view: formularioCompra.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
	@Command
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar) {
		if(checkIsFormValid()){
			this.sTransaccion.registrarCompra(compra, requerimiento, true);
			winCompras.onClose();
		}
	}
	
	/**
	 * Descripcion: Carga nuevamente las listas al cerrar la pantalla
	 * Parametros: @param view: formularioCompra.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void onCloseWindow(){
		ejecutarGlobalCommand("cambiarCompras", null);
	}
	
	
	/**METODOS PROPIOS DE LA CLASE*/
	
	/**GETTERS Y SETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
	public List<DetalleOferta> getListaDetallesCompra() {
		return listaDetallesCompra;
	}

	public void setListaDetallesCompra(List<DetalleOferta> listaDetallesCompra) {
		this.listaDetallesCompra = listaDetallesCompra;
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
}
