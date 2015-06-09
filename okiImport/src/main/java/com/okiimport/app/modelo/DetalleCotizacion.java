package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="detalle_cotizacion")
@NamedQuery(name="DetalleCotizacion.findAll", query="SELECT d FROM DetalleCotizacion d")
public class DetalleCotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="detalle_cotizacion_id_seq")
	@SequenceGenerator(name="detalle_cotizacion_id_seq", sequenceName="detalle_cotizacion_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_detalle_cotizacion", nullable=false, unique=true)
	private Integer idDetalleCotizacion;
	
	@Column(name="marca_repuesto")
	private String marcaRepuesto;
	
	@Column(name="precio_venta", scale=2)
	private Float precioVenta;
	
	@Column(name="precio_flete", scale=2)
	private Float precioFlete;
	
	private Long cantidad;
	
	private Long largo;
	
	private Long ancho;
	
	private Long alto;
	
	private Long peso;
	
	//bi-directional many-to-one association to Cotizacion
	@ManyToOne
	@JoinColumn(name="id_cotizacion")
	private Cotizacion cotizacion;
	
	//bi-directional many-to-one association to DetalleRequerimiento
	@ManyToOne//(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REFRESH})
	@JoinColumn(name="id_detalle_requerimiento")
	private DetalleRequerimiento detalleRequerimiento;

	public DetalleCotizacion() {
	}
	
	public DetalleCotizacion(Cotizacion cotizacion){
		this.cotizacion = cotizacion;
	}

	public Integer getIdDetalleCotizacion() {
		return idDetalleCotizacion;
	}

	public void setIdDetalleCotizacion(Integer idDetalleCotizacion) {
		this.idDetalleCotizacion = idDetalleCotizacion;
	}

	public String getMarcaRepuesto() {
		return marcaRepuesto;
	}

	public void setMarcaRepuesto(String marcaRepuesto) {
		this.marcaRepuesto = marcaRepuesto;
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

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getLargo() {
		return largo;
	}

	public void setLargo(Long largo) {
		this.largo = largo;
	}

	public Long getAncho() {
		return ancho;
	}

	public void setAncho(Long ancho) {
		this.ancho = ancho;
	}

	public Long getAlto() {
		return alto;
	}

	public void setAlto(Long alto) {
		this.alto = alto;
	}

	public Long getPeso() {
		return peso;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
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
	
	/**METODOS PROPIOS DE LA CLASE*/
	public Long volumen(){
		return largo*ancho*alto;
	}
	
	public Long calcularPesoVolumetrico(Long libra){
		Long pesoV = (libra!=null && libra!=new Long(0)) ? volumen()/libra : 0;
		return (pesoV>peso) ? pesoV : peso;
	}
	
	public Long calcularPesoDeCubicaje(Long pieCubico, Long minPieCubico){
		Long pesoC = (pieCubico!=null && pieCubico!=new Long(0)) ? volumen()/pieCubico : 0;
		return (minPieCubico!=null && pesoC<minPieCubico) ? minPieCubico : pesoC;
	}
}
