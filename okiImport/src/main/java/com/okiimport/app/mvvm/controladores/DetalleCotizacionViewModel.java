package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class DetalleCotizacionViewModel extends AbstractViewModel  {

	//Servicios
		@BeanInjector("sTransaccion")
		private STransaccion sTransaccion;
		
		@BeanInjector("sControlUsuario")
		private SControlUsuario sControlUsuario;

		private List <DetalleCotizacion> listaDetalleCotizacion;
		
		//GUI
		@Wire("#gridDetalleCotizacion")
		private Listbox gridDetalleCotizacion;
		
		@Wire("#pagDetalleCotizacion")
		private Paging pagDetalleCotizacion;
		
		//Atributos
		private static final int PAGE_SIZE = 3;
		
		private Usuario usuario;
		private Requerimiento requerimiento;
		private Cotizacion cotizacion;
		private DetalleCotizacion detalleCotizacionFiltro;

		@AfterCompose
		public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
				@ExecutionArgParam("cotizacion")Cotizacion cotizacion){
			super.doAfterCompose(view);
			UserDetails user = this.getUser();
			
			detalleCotizacionFiltro = new DetalleCotizacion();
			this.cotizacion = cotizacion;
			System.out.println("Null cotizacion"+(cotizacion==null));
			ConsultarDetalleCotizacion(0, null, null);
			usuario = sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
			pagDetalleCotizacion.setPageSize(PAGE_SIZE);
		}
		
		@NotifyChange("*")
		private void ConsultarDetalleCotizacion(@Default("0") @BindingParam("page") int page, 
				@BindingParam("fieldSort") String fieldSort, 
				@BindingParam("sortDirection") Boolean sortDirection) {
		Map<String, Object> parametros = sTransaccion.ConsultarDetalleCotizacion(cotizacion.getIdCotizacion(), page, PAGE_SIZE);
		listaDetalleCotizacion = (List<DetalleCotizacion>) parametros.get("detalleCotizacion");
		Integer total = (Integer) parametros.get("total");
		gridDetalleCotizacion.setMultiple(true);
		gridDetalleCotizacion.setCheckmark(true);
		pagDetalleCotizacion.setActivePage(page);
		pagDetalleCotizacion.setTotalSize(total);
		}
		
		/**COMMAND*/
		/*
		 * Descripcion: permitira cambiar la paginacion de acuerdo a la pagina activa del Paging
		 * @param Ninguno
		 * Retorno: Ninguno
		 * */
		@Command
		@NotifyChange("*")
		public void paginarLista(){
			int page=pagDetalleCotizacion.getActivePage();
			ConsultarDetalleCotizacion(page, null, null);
		}


		/**SETTERS Y GETTERS*/
		public STransaccion getsTransaccion() {
			return sTransaccion;
		}

		public void setsTransaccion(STransaccion sTransaccion) {
			this.sTransaccion = sTransaccion;
		}


		public SControlUsuario getsControlUsuario() {
			return sControlUsuario;
		}

		public void setsControlUsuario(SControlUsuario sControlUsuario) {
			this.sControlUsuario = sControlUsuario;
		}


		public DetalleCotizacion getDetalleCotizacionFiltro() {
			return detalleCotizacionFiltro;
		}

		public void setDetalleCotizacionFiltro(DetalleCotizacion detalleCotizacionFiltro) {
			this.detalleCotizacionFiltro = detalleCotizacionFiltro;
		}

		public List<DetalleCotizacion> getListaDetalleCotizacion() {
			return listaDetalleCotizacion;
		}

		public void setListaDetalleCotizacion(
				List<DetalleCotizacion> listaDetalleCotizacion) {
			this.listaDetalleCotizacion = listaDetalleCotizacion;
		}
		
		
		

	}


