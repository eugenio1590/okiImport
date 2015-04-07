package com.okiimport.app.mvvm.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;

import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class VerificarRequerimientosViewModel extends AbstractViewModel {
	
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	private List <Requerimiento> listaRequerimientos;
	
	//GUI
		@Wire("#gridRequerimientosCliente")
		private Listbox gridRequerimientosCliente;
		
		@Wire("#pagRequerimientosCliente")
		private Paging pagRequerimientosCliente;
		
		
		//Atributos
		private static final int PAGE_SIZE = 3;
		
		private Cliente cliente;
		
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
		{
			super.doAfterCompose(view);
			cliente = new Cliente();
			pagRequerimientosCliente.setPageSize(PAGE_SIZE);
			
		}
		
		
	
		/**GLOBAL COMMAND*/
		@GlobalCommand
		@NotifyChange("listaRequerimientos")
		public void cambiarRequerimientos(@Default("0") @BindingParam("page") int page, 
				@BindingParam("fieldSort") String fieldSort, 
				@BindingParam("sortDirection") Boolean sortDirection){
			Map<String, Object> parametros = sTransaccion.ConsultarRequerimientosCliente(null, cliente.getCedula(), page, PAGE_SIZE);
			Integer total = (Integer) parametros.get("total");
			listaRequerimientos = (List<Requerimiento>) parametros.get("requerimientos");
			gridRequerimientosCliente.setMultiple(true);
			gridRequerimientosCliente.setCheckmark(true);
			pagRequerimientosCliente.setActivePage(page);
			pagRequerimientosCliente.setTotalSize(total);
		}

		// Comand 
		
		@Command
		@NotifyChange("listaRequerimientos")
		public void buscarCliente(){
			
			if (this.cliente.getCedula()!= null)
			{ 
				cambiarRequerimientos(0,null, null);
				if (listaRequerimientos.size() > 0 )
				{
					gridRequerimientosCliente.setVisible(true);
				}
				else
				{
					gridRequerimientosCliente.setVisible(false);
					//imprimo mensaje
					
				}
					
			}
					
		}

		// Gets y Sets
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


		public Cliente getCliente() {
			return cliente;
		}


		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}
		
	
	     
	
	
	
	
	
	
	
	
	
	
	
	

}
