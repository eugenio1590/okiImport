package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
@PrimaryKeyJoinColumn(name="id_cliente", columnDefinition="integer")
public class Cliente extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Boolean juridico;
	
	private String estatus;
	
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY)
	private List<Requerimiento> requerimientos;

	public Cliente() {
	}
	
	public Cliente(Persona persona) {
		super(persona);
	}
	
	public Cliente(String cedula){
		super.cedula = cedula;
	}
	
	public Boolean getJuridico() {
		return juridico;
	}

	public void setJuridico(Boolean juridico) {
		this.juridico = juridico;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}
	
	public Requerimiento addRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().add(requerimiento);
		requerimiento.setCliente(this);

		return requerimiento;
	}

	public Requerimiento removeRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().remove(requerimiento);
		requerimiento.setCliente(null);

		return requerimiento;
	}

}