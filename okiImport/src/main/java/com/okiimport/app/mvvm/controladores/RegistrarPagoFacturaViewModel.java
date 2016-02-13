package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
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
import org.zkoss.zul.Messagebox;

import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.model.ModeloCombo;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class RegistrarPagoFacturaViewModel extends AbstractRequerimientoViewModel{
	
	//Servicios
		@BeanInjector("sTransaccion")
		private STransaccion sTransaccion;
		
	private boolean cerrar = false;
		
	private List<ModeloCombo<Boolean>> listaTipoDocumentos;
	private List<ModeloCombo<Boolean>> listaNacionalidad;
	private List<ModeloCombo<Boolean>> listaTipoTarjeta;
    private ModeloCombo<Boolean> tipoDocumento;
    private ModeloCombo<Boolean> nacionalidad;
    private ModeloCombo<Boolean> tipoTarjeta;
    
    
		/**
		 * Descripcion: Llama a inicializar la clase 
		 * Parametros: @param view: verDetalleOferta.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
				@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
				@ExecutionArgParam("oferta") Oferta oferta)
		{
			super.doAfterCompose(view);
			llenarTiposDocumento();
			llenarLiteralDocumentos();
			llenarTiposTarjeta();
		}
		
		
		/** METODOS PROPIOS DE LA CLASE */
		private void llenarTiposDocumento(){
			listaTipoDocumentos = new ArrayList<ModeloCombo<Boolean>>();
			listaTipoDocumentos.add(new ModeloCombo<Boolean>("Seleccione", false));
			listaTipoDocumentos.add(new ModeloCombo<Boolean>("Cédula", false));
			listaTipoDocumentos.add(new ModeloCombo<Boolean>("Pasaporte", true));
		}
		
		private void llenarLiteralDocumentos(){
			listaNacionalidad = new ArrayList<ModeloCombo<Boolean>>();
//			listaNacionalidad.add(new ModeloCombo<Boolean>("Seleccione", false));
			listaNacionalidad.add(new ModeloCombo<Boolean>("V", false));
			listaNacionalidad.add(new ModeloCombo<Boolean>("E", true));
			listaNacionalidad.add(new ModeloCombo<Boolean>("J", true));
		}
		
		private void llenarTiposTarjeta(){
			listaTipoTarjeta = new ArrayList<ModeloCombo<Boolean>>();
			listaTipoTarjeta.add(new ModeloCombo<Boolean>("Seleccione", false));
			listaTipoTarjeta.add(new ModeloCombo<Boolean>("Visa", true));
			listaTipoTarjeta.add(new ModeloCombo<Boolean>("Mastercad", true));
		}
		
		/**COMMAND*/
		/**
		 * Descripcion: Permite limpiar los campos del formulario 
		 * Parametros: @param view: formularioSolicituddePedido.zul 
		 * Retorno: Ninguno 
		 * Nota: Ninguna
		 * */
		@Command
		@NotifyChange({"nacionalidad","tipoDocumento", "tipoTarjeta"})
		public void limpiar(){
			this.nacionalidad = listaNacionalidad.get(0);
			this.tipoDocumento = listaTipoDocumentos.get(0);
			this.tipoTarjeta = listaTipoTarjeta.get(0);
			super.cleanConstraintForm();
		}

		/**
		 * Descripcion: Evento que se ejecuta al cerrar la ventana 
		 * Parametros: Ninguno
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		@Command
		@Override
		public void closeModal(){
			if(!cerrar){
				super.mostrarMensaje("Informaci\u00F3n", "Si cierra la ventana el proceso realizado se perdera, �Desea continuar?", null, 
						new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){
					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.Button button = (Messagebox.Button) event.getData();
						if (button == Messagebox.Button.YES) {
//							ejecutarGlobalCommand("cambiarRequerimientos", null);
							cerrarVentana();
						}
					}
				}, null);
			}
			else {
				ejecutarGlobalCommand("cambiarRequerimientos", null);
				super.closeModal();
			}
		}
		
		
		/**
		 * Descripcion: metodo que actualiza la variable cerrar y llama al comman respectivo al cerrar la ventana.
		 * Parametros: Ninguno
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
		private void cerrarVentana(){
			cerrar = true;
			closeModal();
		}
		
		/**GETTERS Y SETTERS*/
		public STransaccion getsTransaccion() {
			return sTransaccion;
		}

		public void setsTransaccion(STransaccion sTransaccion) {
			this.sTransaccion = sTransaccion;
		}


		public List<ModeloCombo<Boolean>> getListaTipoDocumentos() {
			return listaTipoDocumentos;
		}


		public void setListaTipoDocumentos(List<ModeloCombo<Boolean>> listaTipoDocumentos) {
			this.listaTipoDocumentos = listaTipoDocumentos;
		}


		public List<ModeloCombo<Boolean>> getListaNacionalidad() {
			return listaNacionalidad;
		}


		public void setListaNacionalidad(List<ModeloCombo<Boolean>> listaNacionalidad) {
			this.listaNacionalidad = listaNacionalidad;
		}


		public List<ModeloCombo<Boolean>> getListaTipoTarjeta() {
			return listaTipoTarjeta;
		}


		public void setListaTipoTarjeta(List<ModeloCombo<Boolean>> listaTipoTarjeta) {
			this.listaTipoTarjeta = listaTipoTarjeta;
		}


		public ModeloCombo<Boolean> getTipoDocumento() {
			return tipoDocumento;
		}


		public void setTipoDocumento(ModeloCombo<Boolean> tipoDocumento) {
			this.tipoDocumento = tipoDocumento;
		}


		public ModeloCombo<Boolean> getNacionalidad() {
			return nacionalidad;
		}


		public void setNacionalidad(ModeloCombo<Boolean> nacionalidad) {
			this.nacionalidad = nacionalidad;
		}


		public ModeloCombo<Boolean> getTipoTarjeta() {
			return tipoTarjeta;
		}


		public void setTipoTarjeta(ModeloCombo<Boolean> tipoTarjeta) {
			this.tipoTarjeta = tipoTarjeta;
		}
		
		
}
