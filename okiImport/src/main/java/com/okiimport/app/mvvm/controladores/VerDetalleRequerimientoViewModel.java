package com.okiimport.app.mvvm.controladores;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;

import com.okiimport.app.model.Cliente;
import com.okiimport.app.model.MarcaVehiculo;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;
import com.okiimport.app.service.transaccion.STransaccion;

public class VerDetalleRequerimientoViewModel extends AbstractRequerimientoViewModel  {

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
