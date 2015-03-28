package com.okiimport.app.mvvm.controladores.seguridad.configuracion;

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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.configuracion.SControlUsuario;

public class ListaUsuariosViewModel extends AbstractViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;
	
	//GUI
	@Wire("#gridUsuarios")
	private Listbox gridUsuarios;
	
	@Wire("#pagUsuarios")
	private Paging pagUsuarios;
	
	@Wire("#radEstado")
	private Radiogroup radEstado;
	
	//Modelos
	private List<Usuario> usuarios;
	private Set<Usuario> usuariosSeleccionados;
	
	//Atributos
	private static final int PAGE_SIZE = 3;
	private Integer idUserFiltro;
	private String usernameFiltro;
	private Boolean activoFiltro;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		pagUsuarios.setPageSize(PAGE_SIZE);
		agregarGridSort(gridUsuarios);
		cambiarUsuarios(0, null, null);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("usuarios")
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
	@NotifyChange("usuarios")
	public void cambiarUsuarios(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sControlUsuario.consultarUsuarios(idUserFiltro, usernameFiltro, 
				this.activoFiltro, fieldSort, sortDirection, page, PAGE_SIZE);
		Long total = (Long) parametros.get("total");
		usuarios = (List<Usuario>) parametros.get("usuarios");
		gridUsuarios.setMultiple(true);
		gridUsuarios.setCheckmark(true);
		pagUsuarios.setActivePage(page);
		pagUsuarios.setTotalSize(total.intValue());
	}
	
	/**COMMAND*/
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagUsuarios.getActivePage();
		cambiarUsuarios(page, null, null);
	}
	
	@Command
	@NotifyChange("usuarios")
	public void aplicarFiltro(){
		Radio selectedItem = radEstado.getSelectedItem();
		activoFiltro = (selectedItem!=null) ? Boolean.valueOf((String)selectedItem.getValue()) : null;
		cambiarUsuarios(0, null, null);
	}
	
	@Command
	@NotifyChange("usuarios")
	public void limpiarRadios(){
		activoFiltro = null;
		radEstado.setSelectedIndex(-1);
		aplicarFiltro();
	}
	
	@Command
	public void nuevoUsuario(){
		llamarFormulario("Insertar", null);
	}
	
	@Command
	public void editarUsuario(@BindingParam("usuario") Usuario usuario){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", usuario);
		llamarFormulario("Modificar", parametros );
	}
	
	@Command
	@NotifyChange("usuarios")
	public void actualizarEstado(@BindingParam("usuario") Usuario usuario, @BindingParam("estado") Boolean estado){
		Usuario userSession = consultarUsuarioSession();
		if(userSession.getId()!=usuario.getId()){
			if(sControlUsuario.cambiarEstadoUsuario(usuario, estado)){
				String mensaje = (estado) ? "Activado" : "Desactivado";
				mostrarMensaje("Informacion", "Usuario "+mensaje+" Exitosamente", null, null, null, null);
				paginarLista();
			}
		}
		else
			mostrarMensaje("Error", "No se puede Desactivar el Usuario de la Session", Messagebox.ERROR, null, null, null);
	}
	
	@Command
	@NotifyChange("usuarios")
	public void eliminarUsuario(@BindingParam("usuario") Usuario usuario){
		Usuario userSession = consultarUsuarioSession();
		if(usuario!=null){
			if(userSession.getId()!=usuario.getId())
				if(sControlUsuario.eliminarUsuario(usuario)){
					mostrarMensaje("Informacion", "Usuario Eliminado Exitosamente", null, null, null, null);
					paginarLista();
				}
				else
					mostrarMensaje("Informacion", "El Usuario no se ha podido Eliminar", Messagebox.ERROR, null, null, null);
			else
				mostrarMensaje("Error", "No se puede Eliminar el Usuario de la Session", Messagebox.ERROR, null, null, null);
		
		}
		else if(usuariosSeleccionados!=null){
			for(Usuario usuarioSeleccionado : usuariosSeleccionados)
				if(userSession.getId()!=usuarioSeleccionado.getId())
					sControlUsuario.eliminarUsuario(usuarioSeleccionado);
			cambiarUsuarios(0, null, null);
			mostrarMensaje("Informacion", "Usuarios Eliminados Exitosamente", null, null, null, null);
		}
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	private Usuario consultarUsuarioSession(){
		UserDetails user = this.getUser();
		return sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
	}
	
	private void llamarFormulario(String operacion, Map<String, Object> parametros){
		parametros=(parametros == null) ? new HashMap<String, Object>() : parametros;
		parametros.put("operacion", operacion);
		crearModal("/WEB-INF/views/sistema/seguridad/configuracion/usuarios/formularioUsuarios.zul", parametros);
	}

	/**SETTERS Y GETTERS*/
	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Usuario> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}

	public void setUsuariosSeleccionados(Set<Usuario> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}
	
	public Integer getIdUserFiltro() {
		return idUserFiltro;
	}

	public void setIdUserFiltro(Integer idUserFiltro) {
		this.idUserFiltro = idUserFiltro;
	}

	public String getUsernameFiltro() {
		return usernameFiltro;
	}

	public void setUsernameFiltro(String usernameFiltro) {
		this.usernameFiltro = usernameFiltro;
	}

	public Boolean getActivoFiltro() {
		return activoFiltro;
	}

	public void setActivoFiltro(Boolean activoFiltro) {
		this.activoFiltro = activoFiltro;
	}
}
