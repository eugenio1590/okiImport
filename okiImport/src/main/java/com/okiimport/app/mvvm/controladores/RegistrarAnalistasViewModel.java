package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Ciudad;
import com.okiimport.app.modelo.ClasificacionRepuesto;
import com.okiimport.app.modelo.Estado;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class RegistrarAnalistasViewModel extends AbstractRequerimientoViewModel {
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	private Analista analista;
	private Ciudad ciudad;
	private Estado estado;
	
	private Integer page_size = 6;
	private List<ModeloCombo<Boolean>> listaTipoPersona;
	private ModeloCombo<Boolean> tipoPersona;
	private List<Estado> listaEstados;
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		super.doAfterCompose(view);
		limpiar();
		listaEstados = llenarListaEstados();
		listaTipoPersona = llenarListaTipoPersona();
		this.tipoPersona = listaTipoPersona.get(1);
		
	}
	
	@Command
	@NotifyChange({ "analista" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar) {
		if (checkIsFormValid()) {
				
				btnEnviar.setDisabled(true);
				btnLimpiar.setDisabled(true);
				String tipo = (this.tipoPersona.getValor()) ? "J" : "V";
				analista.setCedula(tipo + analista.getCedula());
				analista = sMaestros.registrarAnalista(analista);

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombreSolicitante", analista.getNombre());
				model.put("cedula", analista.getCedula());
				
				String str = "Analista Registrado con Exito ";

				Messagebox.show(str, "Informacion", Messagebox.OK,
						Messagebox.INFORMATION, new EventListener() {
							public void onEvent(Event event) throws Exception {
								if (((Integer) event.getData()).intValue() == Messagebox.OK) {

									recargar();
								}
							}
						});
			}	
	}
	
	public void recargar() {
		redireccionar("/WEB-INF/views/sistema/maestros/listaAnalistas.zul");
	}
	
	@Command
	@NotifyChange({ "analista" })
	public void limpiar() {
		analista = new Analista();
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public Analista getAnalista() {
		return analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public List<ModeloCombo<Boolean>> getListaTipoPersona() {
		return listaTipoPersona;
	}

	public void setListaTipoPersona(List<ModeloCombo<Boolean>> listaTipoPersona) {
		this.listaTipoPersona = listaTipoPersona;
	}

	public ModeloCombo<Boolean> getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(ModeloCombo<Boolean> tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public List<Estado> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Estado> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}
	
	
	
	
	

}
