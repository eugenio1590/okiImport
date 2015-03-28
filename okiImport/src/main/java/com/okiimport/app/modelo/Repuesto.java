package com.okiimport.app.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the repuesto database table.
 * 
 */
@Entity
@NamedQuery(name="Repuesto.findAll", query="SELECT r FROM Repuesto r")
public class Repuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String nombre;

	//bi-directional many-to-one association to Repuesto
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private Repuesto repuesto;

	//bi-directional many-to-one association to Repuesto
	@OneToMany(mappedBy="repuesto")
	private List<Repuesto> repuestos;

	public Repuesto() {
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

	public Repuesto getRepuesto() {
		return this.repuesto;
	}

	public void setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	public List<Repuesto> getRepuestos() {
		return this.repuestos;
	}

	public void setRepuestos(List<Repuesto> repuestos) {
		this.repuestos = repuestos;
	}

	public Repuesto addRepuesto(Repuesto repuesto) {
		getRepuestos().add(repuesto);
		repuesto.setRepuesto(this);

		return repuesto;
	}

	public Repuesto removeRepuesto(Repuesto repuesto) {
		getRepuestos().remove(repuesto);
		repuesto.setRepuesto(null);

		return repuesto;
	}

}