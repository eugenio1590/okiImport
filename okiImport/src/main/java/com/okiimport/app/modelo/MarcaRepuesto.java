package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the marca_repuesto database table.
 * 
 */
@Entity
@Table(name="marca_repuesto")
@NamedQuery(name="MarcaRepuesto.findAll", query="SELECT m FROM MarcaRepuesto m")
public class MarcaRepuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="marca_repuesto_id_seq")
	@SequenceGenerator(name="marca_repuesto_id_seq", sequenceName="marca_repuesto_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_marca_repuesto")
	private Integer idMarcaRepuesto;

	private String nombre;

	public MarcaRepuesto() {
	}

	public Integer getIdMarcaRepuesto() {
		return this.idMarcaRepuesto;
	}

	public void setIdMarcaRepuesto(Integer idMarcaRepuesto) {
		this.idMarcaRepuesto = idMarcaRepuesto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}