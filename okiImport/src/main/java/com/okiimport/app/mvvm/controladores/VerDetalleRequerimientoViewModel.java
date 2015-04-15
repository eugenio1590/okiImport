package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.controladores.seguridad.configuracion.EditarUsuarioViewModel;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class VerDetalleRequerimientoViewModel extends AbstractViewModel  {

	private Requerimiento requerimiento;
	private Cliente cliente;
	
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	private List <Requerimiento> listaRequerimientos;
	
	private List <MarcaVehiculo> listaMarcasVehiculo;
	
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("requerimiento") Requerimiento requerimiento)
	{
		super.doAfterCompose(view);
		//cliente = new Cliente();	
		//listaMarcasVehiculo = (List<MarcaVehiculo>) sMaestros.ConsultarMarca(0, -1).get("marcas");
		//requerimiento = (Requerimiento) cliente.getRequerimientos();
		//Usuario usuario = cliente.getUsuario();
		//this.cliente = cliente;
		this.requerimiento = requerimiento;
		
		
		
		
	}
	
	/*public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("usuario") Usuario usuario)
	{
		super.doAfterCompose(view);
		persona = usuario.getPersona();
		this.usuario = usuario;
		this.usuario.setPasword(null);
		username = this.usuario.getUsername();
		
		btnCambFoto.addEventListener("onUpload", this);
		
		validadorUsername = new AbstractValidator() {
			
			@Override
			public void validate(ValidationContext ctx) {
				// TODO Auto-generated method stub
				String username = (String) ctx.getProperty().getValue();
				txtUsername = (Textbox) ctx.getBindContext().getValidatorArg("txtUsername");
				if(username!=null)
					if(sControlUsuario.verificarUsername(username) && 
							!username.equalsIgnoreCase(EditarUsuarioViewModel.this.username)){
						String mensaje = "El Username "+username+" ya esta en uso!";
						mostrarNotification(mensaje, "error", 5000, true, txtUsername);
						addInvalidMessage(ctx, mensaje);
					}
			}
		};
	}*/
	
	
	
	
	
	
	
	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}
	
	public List<MarcaVehiculo> getListaMarcasVehiculo() {
		return listaMarcasVehiculo;
	}

	public void setListaMarcasVehiculo(List<MarcaVehiculo> listaMarcasVehiculo) {
		this.listaMarcasVehiculo = listaMarcasVehiculo;
	}
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
}
