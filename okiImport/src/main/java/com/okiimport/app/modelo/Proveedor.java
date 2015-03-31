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
@PrimaryKeyJoinColumn(name="id_proveedor", columnDefinition="integer")
/*@AttributeOverrides(value = {
		//@AttributeOverride(name="cedula", column=@Column(name="rif"))
})*/
public class Proveedor extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	private String estatus;

	private String rif; //Atributo Override con Cedula

	public Proveedor() {
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getRif() {
		return this.rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	

}