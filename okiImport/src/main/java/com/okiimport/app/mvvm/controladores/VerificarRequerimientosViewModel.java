package com.okiimport.app.mvvm.controladores;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;

import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class VerificarRequerimientosViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent> {
	
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	private List <Requerimiento> listaRequerimientos;
	
	//GUI
		@Wire("#gridRequerimientosCliente")
		private Listbox gridRequerimientosCliente;
		
		@Wire("#pagRequerimientosCliente")
		private Paging pagRequerimientosCliente;
		
		@Wire("#misolicitudes")
		private Div misolicitudes;
		
		
		//Atributos
		private static final int PAGE_SIZE = 3;
		
		private Date fechaCreacion;
		
		private Cliente cliente;
		
		private Requerimiento requerimientoFiltro;
		
		private List <ModeloCombo<Boolean>> listaTipoPersona;
		
		private ModeloCombo<Boolean> tipoPersona;
		
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
		{
			super.doAfterCompose(view);
			cliente = new Cliente();
			requerimientoFiltro = new Requerimiento();
			pagRequerimientosCliente.setPageSize(PAGE_SIZE);
			agregarGridSort(gridRequerimientosCliente);
			listaTipoPersona = llenarListaTipoPersona();
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
			Map<String, Object> parametros = sTransaccion.ConsultarRequerimientosCliente(requerimientoFiltro,fieldSort, sortDirection, cliente.getCedula(), page, PAGE_SIZE);
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
			if (this.cliente.getCedula()!= null && tipoPersona!=null)
			{
				String tipo = (this.tipoPersona.getValor())?"J":"V";
				cliente.setCedula(tipo+cliente.getCedula());
				cambiarRequerimientos(0,null, null);
				if (listaRequerimientos.size() > 0 )
					misolicitudes.setVisible(true);
				else
				{
					misolicitudes.setVisible(false);
					mostrarMensaje("Informacion Importante","No posee Solicitudes", null, null, null, null);
				}
			}
		}
		
		
		@Command
		public void verDetalleRequerimiento(@BindingParam("requerimiento") Requerimiento requerimiento){
			
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("requerimiento", requerimiento);
				llamarFormulario("verDetalleRequerimiento.zul", parametros);
			
		}
		
		
		@Command
		@NotifyChange("*")
		public void paginarLista(){
			
			int page=pagRequerimientosCliente.getActivePage();
			cambiarRequerimientos(page, null, null);
			
		} 
 
		@Command
		@NotifyChange("*")
		public void aplicarFiltro()
		{
			if(fechaCreacion!=null)
				this.requerimientoFiltro.setFechaCreacion(new Timestamp(fechaCreacion.getTime()));
			else
				this.requerimientoFiltro.setFechaCreacion(null);
			cambiarRequerimientos(0, null, null);
		}
		
		private void llamarFormulario(String ruta, Map<String, Object> parametros){
			
			crearModal("/WEB-INF/views/"+ruta, parametros);
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


		public Requerimiento getRequerimientoFiltro() {
			return requerimientoFiltro;
		}


		public void setRequerimientoFiltro(Requerimiento requerimientoFiltro) {
			this.requerimientoFiltro = requerimientoFiltro;
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

		public Date getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(Date fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}
		
		
}
