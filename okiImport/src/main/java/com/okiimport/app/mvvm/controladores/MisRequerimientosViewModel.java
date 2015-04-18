package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
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

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class MisRequerimientosViewModel extends AbstractViewModel implements EventListener<SortEvent>{
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;

	private List <Requerimiento> listaRequerimientos;
	
	//GUI
	@Wire("#gridMisRequerimientos")
	private Listbox gridMisRequerimientos;
	
	@Wire("#pagMisRequerimientos")
	private Paging pagMisRequerimientos;
	
	//Atributos
	private static final int PAGE_SIZE = 3;
	
	private Usuario usuario;
	
	private Requerimiento requerimientoFiltro;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		UserDetails user = this.getUser();
		requerimientoFiltro = new Requerimiento(new Cliente());
		usuario = sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
		cambiarRequerimientos(0, null, null);
		agregarGridSort(gridMisRequerimientos);
		pagMisRequerimientos.setPageSize(PAGE_SIZE);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaRequerimientos")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarRequerimientos", parametros );
		}
		
	}
	
	/**GLOBAL COMMAND*/
	@GlobalCommand
	@NotifyChange("listaRequerimientos")
	public void cambiarRequerimientos(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		System.out.println(usuario==null);
		Map<String, Object> parametros = sTransaccion.ConsultarMisRequerimientos(requerimientoFiltro, 
				fieldSort, sortDirection,usuario.getPersona().getId(), page, PAGE_SIZE);
		Integer total = (Integer) parametros.get("total");
		listaRequerimientos = (List<Requerimiento>) parametros.get("requerimientos");
		gridMisRequerimientos.setMultiple(true);
		gridMisRequerimientos.setCheckmark(true);
		pagMisRequerimientos.setActivePage(page);
		pagMisRequerimientos.setTotalSize(total);
	}
	
	/**COMMAND*/
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagMisRequerimientos.getActivePage();
		cambiarRequerimientos(page, null, null);
	}
	
	@Command
	@NotifyChange("listaRequerimientos")
	public void aplicarFiltro(){
		cambiarRequerimientos(0, null, null);
	}
	
	@Command
	public void editarReguerimiento(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
	}
	
	/**SETTERS Y GETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<Requerimiento> getListaRequerimientos() {
		return listaRequerimientos;
	}

	public void setListaRequerimientos(List<Requerimiento> listaRequerimientos) {
		this.listaRequerimientos = listaRequerimientos;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

	public Requerimiento getRequerimientoFiltro() {
		return requerimientoFiltro;
	}

	public void setRequerimientoFiltro(Requerimiento requerimientoFiltro) {
		this.requerimientoFiltro = requerimientoFiltro;
	}

}
