package com.okiimport.app.modelo;

import javax.persistence.*;

@Entity
@Table(name="detalle_cotizacion")
public class DetalleCotizacion {
	
	@Id
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_cotizacion")
	private Cotizacion cotizacion;
	
	@ManyToOne
	@JoinColumn(name="id_detalle_requerimiento")
	private DetalleRequerimiento detalleRequerimiento;

	public DetalleCotizacion() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
