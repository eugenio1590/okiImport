package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Cotizacion;
import com.okiimport.app.model.DetalleCotizacion;
import com.okiimport.app.model.DetalleCotizacionInternacional;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;
import com.okiimport.app.service.mail.MailProveedor;
import com.okiimport.app.service.transaccion.STransaccion;

public class SeleccionarProveedoresViewModel extends AbstractRequerimientoViewModel implements EventListener<ClickEvent> {
	
	//Servicios
	@BeanInjector("sMaestros")
	private SMaestros sMaestros;
	
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("mailProveedor")
	private MailProveedor mailProveedor;
	
	//GUI
	@Wire("#winListProveedores")
	private Window winListProveedores;
	@Wire("#pagProveedores")
	private Paging pagProveedores;
	@Wire ("#btn_enviar")
	private Button btn_enviar;
	
	@Wire("#gridProveedores")
	private Listbox gridProveedores;
	@Wire("#gridProveedoresSeleccionados")
	private Listbox gridProveedoresSeleccionados;
	
	//Atributos
	private static final Comparator<Proveedor> COMPR_PROVEE = Proveedor.getComparator();
	
	private Proveedor proveedor;
	private Cotizacion cotizacion;
	private DetalleRequerimiento detalleRequerimiento;
	private List<Proveedor> listaProveedores;
	private List<Proveedor> proveedoresSeleccionados;
	private List<Proveedor> listaProveedoresSeleccionados1;
	private List<Proveedor> listaProveedoresSeleccionados2;
	private List<DetalleRequerimiento> listaDetalleRequerimientos;
	private List <Integer> idsClasificacionRepuesto;
	private Requerimiento requerimiento;
	private Boolean enviar;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: seleccionarProveedores.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("enviar") Boolean enviar,
			@ExecutionArgParam("repuestosseleccionados") List <DetalleRequerimiento> repuestosseleccionados){
		this.enviar = enviar;
		this.cotizacion = new Cotizacion("Estimado Proveedor le hacemos el envio de un nuevo requerimiento para su posterior revisión ", enviar);
		this.listaDetalleRequerimientos = repuestosseleccionados;
		listaProveedoresSeleccionados1 = new ArrayList<Proveedor>(); 
		super.doAfterCompose(view);
		limpiar();
		pagProveedores.setPageSize(pageSize=9);
		
	
		idsClasificacionRepuesto = new ArrayList<Integer>();
		for(DetalleRequerimiento detalle:repuestosseleccionados){
			requerimiento = detalle.getRequerimiento();
			idsClasificacionRepuesto.add(detalle.getClasificacionRepuesto().getIdClasificacionRepuesto());
		}
		consultarProveedores(0);
	}
	
	/**Interface*/
	@Override
	public void onEvent(ClickEvent event) throws Exception {
		// TODO Auto-generated method stub
		winListProveedores.detach();
		ejecutarGlobalCommand("removerSeleccionados", null);
	}
	
	
	/**GLOBAL COMMAND*/
	
	/**
	 * Descripcion: Permite consultar los proveedores existentes en la base de datos
	 * Parametros: @param view: seleccionarProveedores.zul
	 * Retorno: proveedores consultados y lista llena
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@NotifyChange({"listaProveedores"})
	public void consultarProveedores(@Default("0") int page){
		Map<String, Object> Parametros= sMaestros.ConsultarProveedoresListaClasificacionRepuesto(proveedor, null, null, requerimiento.getIdRequerimiento(), idsClasificacionRepuesto,page, pageSize);
		listaProveedores = (List<Proveedor>) Parametros.get("proveedores");
		Integer total = (Integer) Parametros.get("total");
		gridProveedores.setMultiple(true);
		gridProveedores.setCheckmark(true);
		gridProveedoresSeleccionados.setMultiple(true);
		gridProveedoresSeleccionados.setCheckmark(true);
		pagProveedores.setActivePage(page);
		pagProveedores.setTotalSize(total);
	}
	
	/**COMMAND*/
	
	/**
	 * Descripcion: Permite limpiar los campos del formulario Seleccionar Proveedores
	 * Parametros: @param view: seleccionarProveedores.zul
	 * Retorno: Campos Vacios 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"proveedor"})
	public void limpiar(){
		proveedor = new Proveedor();	
	}

	/**
	 * Descripcion: Permite seleccionar un proveedor para realizar una cotizacion
	 * Parametros: @param view: seleccionarProveedores.zul
	 * Retorno: Proveedor Seleccionado 
	 * Nota: Ninguna
	 * */
	@NotifyChange({"*"})
	@Command
	public void agregarProveedores(){
		super.moveSelection(listaProveedores,listaProveedoresSeleccionados1, proveedoresSeleccionados, 
				COMPR_PROVEE, false, "No se puede agregar Proveedor");
		
	}
	
	/**
	 * Descripcion: Permite eliminar un proveedor para no cotizarle
	 * Parametros: @param view: seleccionarProveedores.zul
	 * Retorno: Proveedor deseleccionado 
	 * Nota: Ninguna
	 * */
	@NotifyChange({"*"})
	@Command
	public void eliminarProveedores(){
		if(listaProveedoresSeleccionados2!=null && !listaProveedoresSeleccionados2.isEmpty())
			listaProveedoresSeleccionados1.removeAll(listaProveedoresSeleccionados2);
	}
	
	/**
	 * Descripcion: Cambia la paginacion de acuerdo a la pagina activa
	 * de Paging pagProveedores
	 * Parametros: @param view: seleccionarProveedores.zul
	 * Retorno: Posicionamiento en otra pagina activa del paging 
	 * Nota: Ninguna
	 * */
	@NotifyChange({"*"})
	@Command
	public void paginarLista(){
		consultarProveedores(pagProveedores.getActivePage());
	}
	
	/**
	 * Descripcion: Permite filtrar por los diferentes campos del formulario segun solicite el evento
	 * Parametros: @param view: seleccionarProveedores.zul
	 * Retorno: Campos filtrados segun sea el evento solicitado
	 * Nota: Ninguna
	 * */
	@NotifyChange({"*"})
	@Command
	public void aplicarFiltro(){
		consultarProveedores(0);
	}
	
	/**
	 * Descripcion: Permite enviar la solicitu de cotizacion a los proveedores
	 * Parametros: @param view: seleccionarProveedores.zul 
	 * Retorno: Solicitud de cotizacion enviada al proveedor para su posterior cotizacion
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaProveedoresSeleccionados1"})
	public void enviar(){
		if(!listaProveedoresSeleccionados1.isEmpty()){
			if(checkIsFormValid())
			{
				for(Proveedor proveedor:listaProveedoresSeleccionados1){

					Cotizacion cotizacion2 =  cotizacion.clon();
					cotizacion2.setProveedor(proveedor);
					List<DetalleCotizacion> detalleCotizacions = new ArrayList<DetalleCotizacion>();

					for(DetalleRequerimiento detalleRequerimiento:listaDetalleRequerimientos){
						DetalleCotizacion detalleCotizacion = (proveedor.getTipoProveedor()) ? new DetalleCotizacion() : new DetalleCotizacionInternacional();
						detalleCotizacion.setDetalleRequerimiento(detalleRequerimiento);
						detalleCotizacions.add(detalleCotizacion);
					}
					sTransaccion.registrarSolicitudCotizacion(cotizacion2, detalleCotizacions);

					if(enviar){
						//No es el servicio que se usara
						this.mailProveedor.registrarSolicitudProveedor(proveedor, mailService);
					}
				}
				btn_enviar.setDisabled(true);
			}

			mostrarMensaje("Informacion", "Cotizacion enviada Exitosamente ", null, null, this, null);
			winListProveedores.onClose();
		}
		else
			mostrarMensaje("Informacion", "Seleccione al menos un Proveedor ", null, null, null, null);

	}
	
	/**
	 * Descripcion: Permite registrar un proveedor llamando al modal formularioProveedor
	 * Parametros: @param view: seleccionarProveedores.zul 
	 * Retorno: Modal para registrar un nuevo proveedor
	 * Nota: Ninguna
	 * */
	@Command
	public void registrarProveedor(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cerrar", false);
		crearModal(BasePackagePortal+"formularioProveedor.zul", parametros);
	}
	
	/**
	 * Descripcion: Permite recargar la pagina
	 * Parametros: @param view: seleccionarProveedores.zul 
	 * Retorno: pagina recargada
	 * Nota: Ninguna
	 * */
	public void recargar() {
		redireccionar("/");
	}
	
    /**METODOS PROPIOS DE LA CLASE*/
	
	/**GETTERS Y SETTERS*/
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
	public MailProveedor getMailProveedor() {
		return mailProveedor;
	}

	public void setMailProveedor(MailProveedor mailProveedor) {
		this.mailProveedor = mailProveedor;
	}

	public List<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}


	public List<Proveedor> getProveedoresSeleccionados() {
		return proveedoresSeleccionados;
	}

	public void setProveedoresSeleccionados(List<Proveedor> proveedoresSeleccionados) {
		this.proveedoresSeleccionados = proveedoresSeleccionados;
	}

	public List<Integer> getIdsClasificacionRepuesto() {
		return idsClasificacionRepuesto;
	}

	public void setIdsClasificacionRepuesto(List<Integer> idsClasificacionRepuesto) {
		this.idsClasificacionRepuesto = idsClasificacionRepuesto;
	}

	public List<Proveedor> getListaProveedoresSeleccionados1() {
		return listaProveedoresSeleccionados1;
	}

	public void setListaProveedoresSeleccionados1(
			List<Proveedor> listaProveedoresSeleccionados1) {
		this.listaProveedoresSeleccionados1 = listaProveedoresSeleccionados1;
	}

	public List<Proveedor> getListaProveedoresSeleccionados2() {
		return listaProveedoresSeleccionados2;
	}

	public void setListaProveedoresSeleccionados2(
			List<Proveedor> listaProveedoresSeleccionados2) {
		this.listaProveedoresSeleccionados2 = listaProveedoresSeleccionados2;
	}

	public Listbox getGridProveedores() {
		return gridProveedores;
	}

	public void setGridProveedores(Listbox gridProveedores) {
		this.gridProveedores = gridProveedores;
	}

	public Listbox getGridProveedoresSeleccionados() {
		return gridProveedoresSeleccionados;
	}

	public void setGridProveedoresSeleccionados(Listbox gridProveedoresSeleccionados) {
		this.gridProveedoresSeleccionados = gridProveedoresSeleccionados;
	}

	public List<DetalleRequerimiento> getListaDetalleRequerimientos() {
		return listaDetalleRequerimientos;
	}

	public void setListaDetalleRequerimientos(
			List<DetalleRequerimiento> listaDetalleRequerimientos) {
		this.listaDetalleRequerimientos = listaDetalleRequerimientos;
	}
	
	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public DetalleRequerimiento getDetalleRequerimiento() {
		return detalleRequerimiento;
	}

	public void setDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
		this.detalleRequerimiento = detalleRequerimiento;
	}

	public Boolean getEnviar() {
		return enviar;
	}

	public void setEnviar(Boolean enviar) {
		this.enviar = enviar;
	}	
}
