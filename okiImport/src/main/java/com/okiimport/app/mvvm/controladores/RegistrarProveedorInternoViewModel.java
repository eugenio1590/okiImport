package com.okiimport.app.mvvm.controladores;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.PasswordGenerator;

public class RegistrarProveedorInternoViewModel extends RegistrarProveedorViewModel {
	
	//Servicios
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;
	
	//GUI
	@Wire("#winRegistarProveedor")
	private Window winRegistarProveedor;
	

	@AfterCompose(superclass=true)
	public void doAfterCompose(){
		
	}
	
	/**COMMAND*/
	@Command
	@Override
	@NotifyChange({ "proveedor" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar) {
		if (checkIsFormValid()) {

			if (proveedor.getMarcaVehiculos().size() > 0
					&& proveedor.getClasificacionRepuestos().size() > 0) {
				
				btnEnviar.setDisabled(true);
				btnLimpiar.setDisabled(true);
				
				proveedor=registrarProveedor(false);
				proveedor.setEstatus("activo");
				
				Usuario usuario = new Usuario(proveedor);
				usuario.setUsername(buscarUsername(proveedor, sControlUsuario));
				usuario.setPasword(PasswordGenerator.getPassword(PasswordGenerator.MINUSCULAS+PasswordGenerator.MAYUSCULAS
						+PasswordGenerator.NUMEROS,10));
				usuario.setActivo(true);
				sControlUsuario.grabarUsuario(usuario, sMaestros);
				
				String str = "Proveedor registrado exitosamente";

				mostrarMensaje("Informacion", str, null, null, null, null);
				
				winRegistarProveedor.onClose();
			}
			else
				mostrarMensaje("Información", "Agregue al Menos una Marca y Una Clasificacion de Repuesto",
						null, null, null, null);
		}
	}
	
	@Command
	public void actualizarListaProveedores(){
		ejecutarGlobalCommand("cambiarProveedores", null);
		ejecutarGlobalCommand("consultarProveedores", null);
	}
	
	/**GETTERS Y SETTERS*/
	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

}
