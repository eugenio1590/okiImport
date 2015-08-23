package com.okiimport.app.modelo;

import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the forma_pago database table.
 * 
 */
@Entity
@Table(name="forma_pago")
@NamedQuery(name="FormaPago.findAll", query="SELECT f FROM FormaPago f")
public class FormaPago {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="forma_pago_id_seq")
	@SequenceGenerator(name="forma_pago_id_seq", sequenceName="forma_pago_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_forma_pago")
	private Integer idFormaPago;
	
	private String nombre;
	
	//bi-directional one-to-many association to PagoCompra
	@OneToMany(mappedBy="formaPago", fetch=FetchType.LAZY)
	private List<PagoCompra> pagoCompras;

	public FormaPago() {
		
	}

	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PagoCompra> getPagoCompras() {
		return pagoCompras;
	}

	public void setPagoCompras(List<PagoCompra> pagoCompras) {
		this.pagoCompras = pagoCompras;
	}
	
	public PagoCompra addPagoCompra(PagoCompra pagoCompra){
		getPagoCompras().add(pagoCompra);
		pagoCompra.setFormaPago(this);
		
		return pagoCompra;
	}
	
	public PagoCompra removePagoCompra(PagoCompra pagoCompra){
		getPagoCompras().remove(pagoCompra);
		pagoCompra.setFormaPago(null);
		
		return pagoCompra;
	}
}