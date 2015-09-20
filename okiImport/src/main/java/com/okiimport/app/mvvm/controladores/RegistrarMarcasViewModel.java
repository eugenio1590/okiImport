package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarMarcasViewModel extends AbstractRequerimientoViewModel {
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	private MarcaVehiculo marca;
	
	private Integer page_size = 10;
	
	@Wire("#winFormularioMarca")
	private Window winFormularioMarca;
	
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view) {
		super.doAfterCompose(view);
		limpiar();
		
	}
	
	@Command
	@NotifyChange({ "marca" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar) {
		if (checkIsFormValid()) {
				
				btnEnviar.setDisabled(true);
				btnLimpiar.setDisabled(true);
				
				marca = sMaestros.registrarMarca(marca);

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("nombre", marca.getNombre());
				
				String str = "Marca Registrada con Exito ";

				Messagebox.show(str, "Informacion", Messagebox.OK,
						Messagebox.INFORMATION, new EventListener() {
							public void onEvent(Event event) throws Exception {
								if (((Integer) event.getData()).intValue() == Messagebox.OK) {
									winFormularioMarca.onClose();
								}
							}
						});
			}	
	}
	
	@Command
	@NotifyChange({ "marca" })
	public void limpiar() {
		marca = new MarcaVehiculo();
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public MarcaVehiculo getMarca() {
		return marca;
	}

	public void setMarca(MarcaVehiculo marca) {
		this.marca = marca;
	}

	

}
