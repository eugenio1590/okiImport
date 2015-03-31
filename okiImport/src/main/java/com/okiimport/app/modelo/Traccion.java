package com.okiimport.app.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the traccion database table.
 * 
 */
@Entity
@NamedQuery(name="Traccion.findAll", query="SELECT t FROM Traccion t")
public class Traccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_traccion")
	private Integer idTraccion;

	private String nombre;

	//bi-directional many-to-one association to Requerimiento
	@OneToMany(mappedBy="traccion")
	private List<Requerimiento> requerimientos;

	public Traccion() {
	}

	public Integer getIdTraccion() {
		return this.idTraccion;
	}

	public void setIdTraccion(Integer idTraccion) {
		this.idTraccion = idTraccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Requerimiento> getRequerimientos() {
		return this.requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}

	public Requerimiento addRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().add(requerimiento);
		requerimiento.setTraccion(this);

		return requerimiento;
	}

	public Requerimiento removeRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().remove(requerimiento);
		requerimiento.setTraccion(null);

		return requerimiento;
	}

}