package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
@PrimaryKeyJoinColumn(name="id_proveedor")
public class Proveedor extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	private String estatus;
	
	//bi-directional many-to-many association to MarcaRepuesto
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinTable(
		name="proveedor_marca_repuesto"
		, joinColumns={
				@JoinColumn(name="id_proveedor")
		}
		, inverseJoinColumns={
				@JoinColumn(name="id_marca_repuesto")
		}
	)
	private List<MarcaRepuesto> marcaRepuestos;

	public Proveedor() {
	}
	
	public Proveedor(Persona persona) {
		super(persona);
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<MarcaRepuesto> getMarcaRepuestos() {
		return marcaRepuestos;
	}

	public void setMarcaRepuestos(List<MarcaRepuesto> marcaRepuestos) {
		this.marcaRepuestos = marcaRepuestos;
	}
}