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

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Compra;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class RegistrarCompraViewModel extends AbstractRequerimientoViewModel {
	
    //Servicios
    @BeanInjector("sMaestros")
	private SMaestros sMaestros;
    
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
	/*
	 * Descripcion: permitira cambiar los requerimientos de la grid de acuerdo a la pagina dada como parametro
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno
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
	/*
	 * Descripcion: permitira cambiar la paginacion de acuerdo a la pagina activa del Paging
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagDetallesCompra.getActivePage();
		cambiarDetallesCompra(page);
	}
	
	@Command
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar) {
		
	}
	
	/**GETTERS Y SETTERS*/
	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

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