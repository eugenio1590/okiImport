package com.okiimport.app.mvvm.controladores;

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

import com.okiimport.app.model.Analista;
import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.model.Usuario;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;

public class ListaMarcasViewModel extends AbstractViewModel implements
		EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;

	// GUI
	@Wire("#gridMarcas")
	private Listbox gridMarcas;

	@Wire("#pagMarcas")
	private Paging pagMarcas;

	// Modelos
	private List<MarcaVehiculo> marcas;
	private MarcaVehiculo marcaFiltro;

	// Atributos
	private static final int PAGE_SIZE = 10;

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
	@Command
	@NotifyChange("*")
	public void paginarLista() {
		int page = pagMarcas.getActivePage();
		cambiarMarcas(page, null, null);
	}

	/*
	 * @Command
	 * 
	 * @NotifyChange("analistas") public void aplicarFiltro(){ Radio
	 * selectedItem = radEstado.getSelectedItem();
	 * this.analistaFiltro.setActivo((selectedItem!=null) ?
	 * Boolean.valueOf((String)selectedItem.getValue()) : null);
	 * cambiarAnalistas(0, null, null); }
	 * 
	 * @Command
	 * 
	 * @NotifyChange("analistas") public void limpiarRadios(){
	 * this.analistaFiltro.setActivo(null); radEstado.setSelectedIndex(-1);
	 * aplicarFiltro(); }
	 */

	@Command
	public void nuevaMarca() {
		llamarFormulario("formularioMarcas.zul", null);
	}


	@Command
	public void verMarca(@BindingParam("marcas") MarcaVehiculo marcas){
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("marcas", marcas);
		//parametros.put("editar", false);
		llamarFormulario("formularioMarcas.zul", parametros);
	}
	

	@Command
	public void editarMarca(@BindingParam("marcas") MarcaVehiculo marcas){
		
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("marcas", marcas);
			//parametros.put("editar", true);
			llamarFormulario("formularioMarcas.zul", parametros);
	}

	/** METODOS PROPIOS DE LA CLASE */
	/*
	 * private Usuario consultarUsuarioSession(){ UserDetails user =
	 * this.getUser(); return
	 * sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
	 * }
	 */

	private void llamarFormulario(String ruta, Map<String, Object> parametros) {
		crearModal("/WEB-INF/views/sistema/maestros/" + ruta, parametros);
	}

	/** SETTERS Y GETTERS */
	/*
	 * public SControlUsuario getsControlUsuario() { return sControlUsuario; }
	 * 
	 * public void setsControlUsuario(SControlUsuario sControlUsuario) {
	 * this.sControlUsuario = sControlUsuario; }
	 */

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
