package com.okiimport.app.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the compra database table.
 * 
 */
@Entity
@Table(name="compra")
@NamedQuery(name="Compra.findAll", query="SELECT c FROM Compra c")
public class Compra {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="compra_id_seq")
	@SequenceGenerator(name="compra_id_seq", sequenceName="compra_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_compra")
	private Integer idCompra;
	
	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;
	
	@Column(name="precio_venta")
	private Float precioVenta;
	
	@Column(name="precio_flete")
	private Float precioFlete;
	
//	@Column(name="monto_iva")
//	private Float montoIva;
	
	//bi-directional many-to-one association to Requerimiento
	@ManyToOne
	@JoinColumn(name="id_requerimiento")
	private Requerimiento requerimiento;
	
	//bi-directional many-to-one association to HistoricoMoneda
	@ManyToOne
	@JoinColumn(name="id_historico_moneda")
	private HistoricoMoneda historicoMoneda;
	
	//bi-directional many-to-one association to DetalleOferta
	@OneToMany(mappedBy="compra", fetch=FetchType.LAZY)
	private List<DetalleOferta> detalleOfertas;

	public Compra() {
	}

	public Integer getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public HistoricoMoneda getHistoricoMoneda() {
		return historicoMoneda;
	}

	public void setHistoricoMoneda(HistoricoMoneda historicoMoneda) {
		this.historicoMoneda = historicoMoneda;
	}

	public List<DetalleOferta> getDetalleOfertas() {
		return detalleOfertas;
	}

	public void setDetalleOfertas(List<DetalleOferta> detalleOfertas) {
		this.detalleOfertas = detalleOfertas;
	}
	
	public DetalleOferta addDetalleOferta(DetalleOferta detalleOferta){
		getDetalleOfertas().add(detalleOferta);
		detalleOferta.setCompra(this);
		
		return detalleOferta;
	}
	
	public DetalleOferta removeDetalleOferta(DetalleOferta detalleOferta){
		getDetalleOfertas().remove(detalleOferta);
		detalleOferta.setCompra(null);
		
		return detalleOferta;
	}

}
