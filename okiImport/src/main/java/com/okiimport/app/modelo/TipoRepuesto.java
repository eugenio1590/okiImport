package com.okiimport.app.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipo_repuesto database table.
 * 
 */
@Entity
@Table(name="tipo_repuesto")
@NamedQuery(name="TipoRepuesto.findAll", query="SELECT t FROM TipoRepuesto t")
public class TipoRepuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String nombre;

	public TipoRepuesto() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}