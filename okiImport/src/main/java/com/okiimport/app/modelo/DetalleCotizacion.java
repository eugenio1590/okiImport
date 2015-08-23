package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the detalle_cotizacion database table.
 * 
 */
@Entity
@Table(name="detalle_cotizacion")
@Inheritance(strategy=InheritanceType.JOINED)
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
	private Float precioVenta = new Float(0);
	
	@Column(name="precio_flete", scale=2)
	private Float precioFlete = new Float(0);
	
	private Long cantidad;
	
	private String estatus;
	
	//bi-directional many-to-one association to Cotizacion
	@ManyToOne
	@JoinColumn(name="id_cotizacion")
	private Cotizacion cotizacion;
	
	//bi-directional many-to-one association to DetalleRequerimiento
	@ManyToOne//(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REFRESH})
	@JoinColumn(name="id_detalle_requerimiento")
	private DetalleRequerimiento detalleRequerimiento;
	
	//bi-directional many-to-one association to DetalleOferta
	@OneToMany(mappedBy="detalleCotizacion", fetch=FetchType.LAZY)
	private List<DetalleOferta> detalleOfertas;

	public DetalleCotizacion() {
	}
	
	public DetalleCotizacion(Integer idDetalleCotizacion, String marcaRepuesto,
			Float precioVenta, Float precioFlete, Long cantidad,
			Cotizacion cotizacion, DetalleRequerimiento detalleRequerimiento) {
		super();
		this.idDetalleCotizacion = idDetalleCotizacion;
		this.marcaRepuesto = marcaRepuesto;
		this.precioVenta = precioVenta;
		this.precioFlete = precioFlete;
		this.cantidad = cantidad;
		this.cotizacion = cotizacion;
		this.detalleRequerimiento = detalleRequerimiento;
	}

	public DetalleCotizacion(Cotizacion cotizacion, DetalleRequerimiento detalleRequerimiento){
		this.cotizacion = cotizacion;
		this.detalleRequerimiento = detalleRequerimiento;
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
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
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
	
	public List<DetalleOferta> getDetalleOfertas() {
		return detalleOfertas;
	}

	public void setDetalleOfertas(List<DetalleOferta> detalleOfertas) {
		this.detalleOfertas = detalleOfertas;
	}
	
	public DetalleOferta addDetalleOferta(DetalleOferta detalleOferta){
		getDetalleOfertas().add(detalleOferta);
		detalleOferta.setDetalleCotizacion(this);
		
		return detalleOferta;
	}
	
	public DetalleOferta removeDetalleOferta(DetalleOferta detalleOferta){
		getDetalleOfertas().remove(detalleOferta);
		detalleOferta.setDetalleCotizacion(null);
		
		return detalleOferta;
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	public Float calcularTotal(){
		if(this.precioFlete!=null)
			return calcularCosto()+this.precioFlete;
		else
			return this.precioVenta;
	}
	
	public Float calcularCosto(){
		return this.precioVenta*this.cantidad;
	}
	
	public void eliminarPrecios(){
		this.setPrecioVenta(null);
		this.setPrecioFlete(null);
	}
}
