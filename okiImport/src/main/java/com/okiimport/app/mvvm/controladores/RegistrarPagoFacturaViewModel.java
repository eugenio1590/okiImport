package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

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
		
	//GUI
	@Wire("#winPagoFactura")
	private Window winPagoFactura;	
	
	private boolean cerrar = false;
		
	private List<ModeloCombo<Boolean>> listaTipoDocumentos;
    private ModeloCombo<Boolean> tipoDocumento;
    private ModeloCombo<Boolean> tipoTarjeta;
    
    @Wire
    private Textbox txtemail;
    @Wire
    private Textbox txtTarjeta;
    @Wire
    private Textbox txtCodigo;
    @Wire
    private Textbox txtMesVence;
    @Wire
    private Textbox txtAnoVence;
    @Wire
    private Textbox txtTitular;
    @Wire
    private Textbox txtNroDoc;
    
    Float totalFactura;
    
		/**
		 * Descripcion: Llama a inicializar la clase 
		 * Parametros: @param view: verDetalleOferta.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
    	@NotifyChange({"totalFactura"})
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
				@ExecutionArgParam("totalFactura")  Float totalF)
		{
			super.doAfterCompose(view);
			llenarTiposDocumento();
			this.totalFactura = totalF;
		}
		
		
		/** METODOS PROPIOS DE LA CLASE */
		private void llenarTiposDocumento(){
			listaTipoDocumentos = new ArrayList<ModeloCombo<Boolean>>();
			listaTipoDocumentos.add(new ModeloCombo<Boolean>("Seleccione", false));
			listaTipoDocumentos.add(new ModeloCombo<Boolean>("Cédula", false));
			listaTipoDocumentos.add(new ModeloCombo<Boolean>("Pasaporte", true));
		}
		/**COMMAND*/
		@Command
		public void registrarPago(Map<String, Object> paramets){
			super.mostrarMensaje("Informaci\u00F3n", "Desea efectuar el pago de la factura de productos?", null, 
					new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){
				
				@Override
				public void onEvent(Event event) throws Exception {
					Messagebox.Button button = (Messagebox.Button) event.getData();
					if (button == Messagebox.Button.YES) {
						//SIMULACION DE PAGO
						mostrarMensaje("Informaci\u00F3n", "¡Operacion registrada exitosamente!", Messagebox.INFORMATION, null, null, null);
						winPagoFactura.detach();
					}else
						closeModal();
				}
			}, null);
		}
		/**
		 * Descripcion: Permite limpiar los campos del formulario 
		 * Parametros: @param view: formularioSolicituddePedido.zul 
		 * Retorno: Ninguno 
		 * Nota: Ninguna
		 * */
		@Command
		@NotifyChange({"tipoDocumento"})
		public void limpiar(){
			this.tipoDocumento = listaTipoDocumentos.get(0);
			this.txtAnoVence.setValue("");
			this.txtCodigo.setValue("");
			this.txtemail.setValue("");
			this.txtMesVence.setValue("");
			this.txtNroDoc.setValue("");
			this.txtTarjeta.setValue("");
			this.txtTitular.setValue("");
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
							cerrarVentana();
						}
					}
				}, null);
			}
			else {
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

		public ModeloCombo<Boolean> getTipoDocumento() {
			return tipoDocumento;
		}

		public void setTipoDocumento(ModeloCombo<Boolean> tipoDocumento) {
			this.tipoDocumento = tipoDocumento;
		}

		public ModeloCombo<Boolean> getTipoTarjeta() {
			return tipoTarjeta;
		}

		public void setTipoTarjeta(ModeloCombo<Boolean> tipoTarjeta) {
			this.tipoTarjeta = tipoTarjeta;
		}


		public Float getTotalFactura() {
			return totalFactura;
		}


		public void setTotalFactura(Float totalFactura) {
			this.totalFactura = totalFactura;
		}
		
		
		
}
