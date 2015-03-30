package com.okiimport.app.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipo_repuesto database table.
 * 
 */
@Entity
@Table(name="clasificacion_repuesto")
@NamedQuery(name="ClasificacionRepuesto.findAll", query="SELECT c FROM ClasificacionRepuesto c")
public class ClasificacionRepuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String nombre;

	public ClasificacionRepuesto() {
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