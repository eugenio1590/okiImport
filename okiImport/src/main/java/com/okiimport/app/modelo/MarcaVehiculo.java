package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the marca_vehiculo database table.
 * 
 */
@Entity
@Table(name="marca_vehiculo")
@NamedQuery(name="MarcaVehiculo.findAll", query="SELECT m FROM MarcaVehiculo m")
public class MarcaVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="marca_vehiculo_id_seq")
	@SequenceGenerator(name="marca_vehiculo_id_seq", sequenceName="marca_vehiculo_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_marca_vehiculo")
	private Integer idMarcaVehiculo;

	private String nombre;

	//bi-directional many-to-one association to Requerimiento
	@OneToMany(mappedBy="marcaVehiculo")
	private List<Requerimiento> requerimientos;

	public MarcaVehiculo() {
	}

	public Integer getIdMarcaVehiculo() {
		return this.idMarcaVehiculo;
	}

	public void setIdMarcaVehiculo(Integer idMarcaVehiculo) {
		this.idMarcaVehiculo = idMarcaVehiculo;
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
		requerimiento.setMarcaVehiculo(this);

		return requerimiento;
	}

	public Requerimiento removeRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().remove(requerimiento);
		requerimiento.setMarcaVehiculo(null);

		return requerimiento;
	}

}