package com.okiimport.app.modelo;

import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the banco database table.
 * 
 */
@Entity
@Table(name="banco")
@NamedQuery(name="Banco.findAll", query="SELECT b FROM Banco b")
public class Banco {
	
	@Id
	private Integer idBanco;
	
	private String nombre;
	
	private String estatus;
	
	//bi-directional one-to-many association to PagoCompra
	@OneToMany(mappedBy="banco", fetch=FetchType.LAZY)
	private List<PagoCompra> pagoCompras;

	public Banco() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<PagoCompra> getPagoCompras() {
		return pagoCompras;
	}

	public void setPagoCompras(List<PagoCompra> pagoCompras) {
		this.pagoCompras = pagoCompras;
	}
	
	public PagoCompra addPagoCompra(PagoCompra pagoCompra){
		getPagoCompras().add(pagoCompra);
		pagoCompra.setBanco(this);
		
		return pagoCompra;
	}
	
	public PagoCompra removePagoCompra(PagoCompra pagoCompra){
		getPagoCompras().remove(pagoCompra);
		pagoCompra.setBanco(null);
		
		return pagoCompra;
	}
}
