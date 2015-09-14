package com.okiimport.app.mvvm.controladores;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;
import com.okiimport.app.configuracion.servicios.SControlUsuario;

public class ListaAnalistasViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	//GUI
	@Wire("#gridAnalistas")
	private Listbox gridAnalistas;
	
	@Wire("#pagAnalistas")
	private Paging pagAnalistas;
	
	private Textbox txtUsername;
	
	
	//Modelos
	private List<Analista> analistas;
	private Analista analistaFiltro;
	
	//private Persona persona;
	//private Usuario usuario;
	
	//Atributos

	
	/*private AbstractValidator validadorUsername;
	private String username;*/
	
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		analistaFiltro = new Analista();
		pagAnalistas.setPageSize(pageSize);
		agregarGridSort(gridAnalistas);
		cambiarAnalistas(0, null, null);
	}
	
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("analistas")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort",  event.getTarget().getId().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarUsuarios", parametros );
		}
		
	}
	
	
	/**GLOBAL COMMAND*/
	@GlobalCommand
	@NotifyChange("analistas")
	public void cambiarAnalistas(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sMaestros.consultarAnalistas(analistaFiltro, page, pageSize);
		//Map<String, Object> parametros = sControlUsuario.consultarUsuarios(usuarioFiltro, fieldSort, sortDirection, page, PAGE_SIZE);
		Integer total = (Integer) parametros.get("total");
		analistas = (List<Analista>) parametros.get("analistas");
		pagAnalistas.setActivePage(page);
		pagAnalistas.setTotalSize(total);
	}     
	

	
	@Command
	@NotifyChange("*")
	public void cerrarvista(){
		
		cambiarAnalistas(0, null, null);
	}
	
	/**COMMAND*/
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagAnalistas.getActivePage();
		cambiarAnalistas(page, null, null);
	}
	
	
	
	
	/*@Command
	@NotifyChange("analistas")
	public void aplicarFiltro(){
		Radio selectedItem = radEstado.getSelectedItem();
		this.analistaFiltro.setActivo((selectedItem!=null) ? Boolean.valueOf((String)selectedItem.getValue()) : null);
		cambiarAnalistas(0, null, null);
	}
	
	*/
	
	
	
	@Command
	public void verAnalista(@BindingParam("analista") Analista analista){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("analista", analista);
		//parametros.put("editar", false);
		llamarFormulario("ver Analistas.zul", parametros);
	}
	

	@Command
	public void editarAnalista(@BindingParam("analista") Analista analista){
		
		
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("analista", analista);
			//parametros.put("editar", true);
			llamarFormulario("editarAnalistas.zul", parametros);
	}
	
	
	@Command
	public void nuevoAnalista(){
		llamarFormulario("formularioAnalistas.zul", null);
	}
	
	
	/*
	
	@Command
	public void verAnalista(@BindingParam("analista") Analista analista){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("analista", analista);
		parametros.put("editar", false);
		llamarFormulario("editarAnalistas.zul", parametros);
	}
	
	
	
	@Command
	public void editarAnalista(@BindingParam("analista") Analista analista){
		
		
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("analista", analista);
			parametros.put("editar", true);
			llamarFormulario("editarAnalistas.zul", parametros);
	}
	
	
	*/
	
	
	
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/sistema/maestros/"+ruta, parametros);
	}

	/**METODOS PROPIOS DE LA CLASE*/
	
	/**SETTERS Y GETTERS*/
	/*public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}*/

	public List<Analista> getAnalistas() {
		return analistas;
	}
	
	public void setAnalistas(List<Analista> analistas) {
		this.analistas = analistas;
	}
	
	public Analista getAnalistaFiltro() {
		return analistaFiltro;
	}

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}
	
	
}