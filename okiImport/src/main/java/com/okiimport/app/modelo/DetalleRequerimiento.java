package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the detalle_requerimiento database table.
 * 
 */
@Entity
@Table(name="detalle_requerimiento")
@NamedQuery(name="DetalleRequerimiento.findAll", query="SELECT d FROM DetalleRequerimiento d")
public class DetalleRequerimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_detalle_requerimiento")
	private Integer idDetalleRequerimiento;

	private Long cantidad;

	@Column(name="codigo_oem")
	private String codigoOem;

	private String estatus;

	private byte[] foto;

	//bi-directional many-to-one association to ClasificacionRepuesto
	@ManyToOne
	@JoinColumn(name="id_clasificacion_repuesto", columnDefinition="integer")
	private ClasificacionRepuesto clasificacionRepuesto;

	//bi-directional many-to-one association to Requerimiento
	@ManyToOne
	@JoinColumn(name="id_requerimiento")
	private Requerimiento requerimiento;

	private String nombre;

	public DetalleRequerimiento() {
	}

	public Integer getIdDetalleRequerimiento() {
		return this.idDetalleRequerimiento;
	}

	public void setIdDetalleRequerimiento(Integer idDetalleRequerimiento) {
		this.idDetalleRequerimiento = idDetalleRequerimiento;
	}

	public Long getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigoOem() {
		return this.codigoOem;
	}

	public void setCodigoOem(String codigoOem) {
		this.codigoOem = codigoOem;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ClasificacionRepuesto getClasificacionRepuesto() {
		return clasificacionRepuesto;
	}

	public void setClasificacionRepuesto(ClasificacionRepuesto clasificacionRepuesto) {
		this.clasificacionRepuesto = clasificacionRepuesto;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

}