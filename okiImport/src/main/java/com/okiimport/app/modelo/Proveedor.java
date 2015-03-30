package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
@AttributeOverrides(value = { 
		@AttributeOverride(name="id",column=@Column(name="id_proveedor", columnDefinition="serial")),
		//@AttributeOverride(name="cedula", column=@Column(name="rif"))
})
public class Proveedor extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="rif", unique=true)
	private String rif; //Atributo Override con Cedula
	
	@Column(name="estatus")
	private String estatus;
	
	//bi-directional many-to-one association to Usuario
	@OneToOne(mappedBy="proveedor")
	private Usuario usuario;

	public Proveedor() {
	}

	public String getRif() {
		return this.rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}
	
	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}