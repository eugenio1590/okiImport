package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;




//import org.omg.CORBA.Environment;
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

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Request;
import com.okiimport.app.model.Compra;
import com.okiimport.app.model.Deposito;
import com.okiimport.app.model.FormaPago;
import com.okiimport.app.model.PagoCliente;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
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
    Compra compra = new Compra();
    FormaPago forma = new FormaPago();
    String clientToken;
    
    private CustomConstraint constraintMensaje;
    
		/**
		 * Descripcion: Llama a inicializar la clase 
		 * Parametros: @param view: verDetalleOferta.zul 
		 * Retorno: Ninguno
		 * Nota: Ninguna
		 * */
    	@NotifyChange({"totalFactura"})
		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
				@ExecutionArgParam("totalFactura")  Float totalF,
				@ExecutionArgParam("compra")  Compra compra,
				@ExecutionArgParam("formaPago")  FormaPago forma)
		{
			super.doAfterCompose(view);
			llenarTiposDocumento();
			this.totalFactura = totalF;
			this.compra = compra;
			this.forma = forma;
			
			//Braintree
			 BraintreeGateway gateway = new BraintreeGateway(		
					  Environment.SANDBOX,
					  "382dm4xp72sqgpjk",
					  "wcwgq6pytzjs4q8c",
					  "aec72812f66e5c89d2f404f51d6dac7a"
			);
			
			ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
			clientToken = gateway.clientToken().generate(clientTokenRequest);
			System.out.println("clientToken en el VM: "+clientToken);
			
			//Send a client token to your client
			/*post(new Route("/client_token") {
				  @Override
				  public Object handle(Request request, Response response) {
				    return gateway.clientToken().generate();
				  }
				});*/
		}
    	
    	
    	public PagoCliente llenarPago(){
    		PagoCliente p = new PagoCliente();
    		p.setCompra(this.compra);
    		p.setFechaPago(new Date());
    		p.setMonto(totalFactura);
    		p.setEstatus("Pagado");
    		p.setDescripcion("Descripcion Hardcore");
    		p.setFormaPago(forma);
    		//CORREGIR
    		p.setBanco(null);
    		//CORREGIR
    		List<Deposito> depositos = new ArrayList<Deposito>();
    		p.setDepositos(depositos);
    		return p;
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
						// ACA GUARDAR EL PAGO
						sTransaccion.registrarPagoFactura(llenarPago());
						sTransaccion.guardarOrdenCompra(compra, sControlConfiguracion);
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
		
		public CustomConstraint getConstraintMensaje() {
			return constraintMensaje;
		}

		public void setConstraintMensaje(CustomConstraint constraintMensaje) {
			this.constraintMensaje = constraintMensaje;
		}


		public String getClientToken() {
			return clientToken;
		}


		public void setClientToken(String clientToken) {
			this.clientToken = clientToken;
		}
		
}
