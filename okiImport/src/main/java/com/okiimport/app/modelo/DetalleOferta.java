package com.okiimport.app.modelo;

import javax.persistence.*;

/**
 * The persistent class for the detalle_oferta database table.
 * 
 */
@Entity
@Table(name="detalle_oferta")
@NamedQuery(name="DetalleOferta.findAll", query="SELECT d FROM DetalleOferta d")
public class DetalleOferta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="detalle_oferta_id_seq")
	@SequenceGenerator(name="detalle_oferta_id_seq", sequenceName="detalle_oferta_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_detalle_oferta")
	private Integer idDetalleOferta;
	
	@Column(name="precio_venta")
	private Float precioVenta;
	
	@Column(name="precio_flete")
	private Float precioFlete;
	
	private Boolean aprobado;
	
	private String estatus;
	
	//bi-directional many-to-one association to Oferta
	@ManyToOne
	@JoinColumn(name="id_oferta")
	private Oferta oferta;
	
	//bi-directional many-to-one association to Compra
	@ManyToOne
	@JoinColumn(name="id_compra")
	private Compra compra;
	
	//bi-directional many-to-one association to DetalleCotizacion
	@ManyToOne
	@JoinColumn(name="id_detalle_cotizacion")
	private DetalleCotizacion detalleCotizacion;

	public DetalleOferta() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdDetalleOferta() {
		return idDetalleOferta;
	}

	public void setIdDetalleOferta(Integer idDetalleOferta) {
		this.idDetalleOferta = idDetalleOferta;
	}

	public Float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Float getPrecioFlete() {
		return precioFlete;
	}

	public void setPrecioFlete(Float precioFlete) {
		this.precioFlete = precioFlete;
	}

	public Boolean getAprobado() {
		return aprobado;
	}

	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}
	
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public DetalleCotizacion getDetalleCotizacion() {
		return detalleCotizacion;
	}

	public void setDetalleCotizacion(DetalleCotizacion detalleCotizacion) {
		this.detalleCotizacion = detalleCotizacion;
	}
}
