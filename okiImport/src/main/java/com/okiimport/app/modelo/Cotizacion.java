package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="cotizacion")
@NamedQuery(name="Cotizacion.findAll", query="SELECT c FROM Cotizacion c")
public class Cotizacion implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cotizacion_id_seq")
	@SequenceGenerator(name="cotizacion_id_seq", sequenceName="cotizacion_id_seq", initialValue=1, allocationSize=1)
	private Integer idCotizacion;
	
	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name="fecha_vencimiento")
	private Date fechaVencimiento;
	
	private String estatus;
	
	private String mensaje;
	
	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedor;
	
	//bi-directional many-to-one association to DetalleCotizacion
	@OneToMany(mappedBy="cotizacion",fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<DetalleCotizacion> detalleCotizacions;

	public Cotizacion() {
	}

	public Cotizacion(Integer idCotizacion, Date fechaCreacion,
			Date fechaVencimiento, String estatus, String mensaje, Proveedor proveedor) {
		super();
		this.idCotizacion = idCotizacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaVencimiento = fechaVencimiento;
		this.estatus = estatus;
		this.mensaje = mensaje;
		this.proveedor = proveedor;
	}



	public Integer getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<DetalleCotizacion> getDetalleCotizacions() {
		return detalleCotizacions;
	}

	public void setDetalleCotizacions(List<DetalleCotizacion> detalleCotizacions) {
		this.detalleCotizacions = detalleCotizacions;
	}

}
