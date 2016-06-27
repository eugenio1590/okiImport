package com.okiimport.app.mvvm.controladores;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Ciudad;
import com.okiimport.app.model.Cliente;
import com.okiimport.app.model.Estado;
import com.okiimport.app.model.Persona;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Usuario;
import com.okiimport.app.model.factory.persona.EstatusCliente;
import com.okiimport.app.model.factory.persona.EstatusProveedorFactory;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.mail.MailProveedor;
import com.okiimport.app.service.mail.MailUsuario;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarUsuarioViewModel extends AbstractRequerimientoViewModel {
	
	//Servicios
		@BeanInjector("sTransaccion")
		private STransaccion sTransaccion;
		
		@BeanInjector("mailUsuario")
		private MailUsuario mailUsuario;
		

		//GUI
	
		@Wire("#cmbEstado")
		private Combobox cmbEstado;
		
		@Wire("#cmbCiudad")
		private Combobox cmbCiudad;
		
		@Wire("#comboTipoPersona")
		private Combobox comboTipoPersona;
		
		@Wire("#btnLimpiar")
		private Button btnLimpiar;
		
		@Wire("#idRif")
		public Textbox idRif;
		
		//Atributos
		private CustomConstraint constrEstado = null;
		private CustomConstraint constrCiudad = null;
		private List<Estado> listaEstados;
		private List<Ciudad> listaCiudad;
		private ModeloCombo<Boolean>  tipoPersona;
		private List<ModeloCombo<Boolean>> listaTipoPersona;
		private Estado estadoSeleccionado;
		private boolean makeAsReadOnly;
		protected Usuario usuario;
		protected Persona persona;
		protected Cliente cliente;
		
		
		/**
		 * Descripcion: Llama a inicializar la clase 
		 * Parametros: @param view: formularioProveedor.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
				@ExecutionArgParam("persona") Persona persona) {
			super.doAfterCompose(view);
			
			listaEstados = llenarListaEstados();
			listaTipoPersona = llenarListaTipoPersona();
			tipoPersona = listaTipoPersona.get(1);
			//tipoPersona=consultarTipoPersona(this.cliente.getCedula(),listaTipoPersona);
		}
		
		/**
		 * Descripcion: Permite limpiar los campos del formulario registrar Proveedor
		 * Parametros: @param view: formularioRegistroUsuario.zul 
		 * Retorno: Ninguno 
		 * Nota: Ninguna
		 * */
		@Command
		@NotifyChange({ "proveedor",  "estado", "constrEstado", "constrCiudad" })
		public void limpiar() {
			cliente = new Cliente();
			usuario = new Usuario();
			limpiarEstadoYCiudad();
		}
		
		/**
		 * Descripcion: Permite Consultar el tipo de persona
		 * Parametros: @param view: formularioProveedor.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		private ModeloCombo<Boolean> consultarTipoPersona(String cedula, List <ModeloCombo<Boolean>> listaTipoPersona){
			if (cedula!=null){
				String tipoPersona = cedula.substring(0, 1);
				for(ModeloCombo<Boolean> tipoPersonal: listaTipoPersona )
					if (tipoPersonal.getNombre().equalsIgnoreCase(tipoPersona))
						return tipoPersonal;
			}
			return this.tipoPersona;
		}
		
		/**
		 * Descripcion: Permite limpiar las variables que se encargan de las variables de ciudad y estado
		 * Parametros: Ninguno
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		private void limpiarEstadoYCiudad(){
			if(constrEstado!=null)
				constrEstado.hideComponentError();
			if(constrCiudad!=null)
				constrCiudad.hideComponentError();
			constrEstado = null;
			constrCiudad = null;
			estado = null;
			cliente.setCiudad(null);
		}
		
		/**
		 * Descripcion: Permite registrar un proveedor en el sistema
		 * Parametros: @param view: formularioProveedor.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@Command
		protected void registrarUsuario(){
			
			cliente.setCedula(getCedulaComleta());
			cliente.setEstatus(EstatusCliente.getEstatusActivo().getValue());

			cliente = sMaestros.registrarOActualizarCliente(cliente);
		
			
		}
		
		
		/**
		 * Descripcion: Permitira obtener la cedula completa del proveedor
		 * Parametros: Ninguno
		 * Retorno Ninguno
		 * Nota: Ninguna
		 * */
		private String getCedulaComleta(){
			return this.tipoPersona.getNombre() + cliente.getCedula();
		}


		public List<Estado> getListaEstados() {
			return listaEstados;
		}


		public void setListaEstados(List<Estado> listaEstados) {
			this.listaEstados = listaEstados;
		}


		public List<Ciudad> getListaCiudad() {
			return listaCiudad;
		}


		public void setListaCiudad(List<Ciudad> listaCiudad) {
			this.listaCiudad = listaCiudad;
		}


		public List<ModeloCombo<Boolean>> getListaTipoPersona() {
			return listaTipoPersona;
		}


		public void setListaTipoPersona(List<ModeloCombo<Boolean>> listaTipoPersona) {
			this.listaTipoPersona = listaTipoPersona;
		}


		public Usuario getUsuario() {
			return usuario;
		}


		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}


		public Persona getPersona() {
			return persona;
		}


		public void setPersona(Persona persona) {
			this.persona = persona;
		}


		public STransaccion getsTransaccion() {
			return sTransaccion;
		}


		public void setsTransaccion(STransaccion sTransaccion) {
			this.sTransaccion = sTransaccion;
		}


		public Estado getEstadoSeleccionado() {
			return estadoSeleccionado;
		}


		public void setEstadoSeleccionado(Estado estadoSeleccionado) {
			this.estadoSeleccionado = estadoSeleccionado;
		}


		public boolean isMakeAsReadOnly() {
			return makeAsReadOnly;
		}


		public void setMakeAsReadOnly(boolean makeAsReadOnly) {
			this.makeAsReadOnly = makeAsReadOnly;
		}


		public MailUsuario getMailUsuario() {
			return mailUsuario;
		}


		public void setMailUsuario(MailUsuario mailUsuario) {
			this.mailUsuario = mailUsuario;
		}


		public Cliente getCliente() {
			return cliente;
		}


		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}


		public Combobox getCmbEstado() {
			return cmbEstado;
		}


		public void setCmbEstado(Combobox cmbEstado) {
			this.cmbEstado = cmbEstado;
		}


		public Combobox getCmbCiudad() {
			return cmbCiudad;
		}


		public void setCmbCiudad(Combobox cmbCiudad) {
			this.cmbCiudad = cmbCiudad;
		}


		public ModeloCombo<Boolean> getTipoPersona() {
			return tipoPersona;
		}


		public void setTipoPersona(ModeloCombo<Boolean> tipoPersona) {
			this.tipoPersona = tipoPersona;
		}
	
}
