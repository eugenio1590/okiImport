package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the analista database table.
 * 
 */
@Entity
@NamedQuery(name="Analista.findAll", query="SELECT a FROM Analista a")
@PrimaryKeyJoinColumn(name="id_analista")
public class Analista extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(length=50)
	private String estatus;
	
	@Column
	private Boolean administrador;
	
	@Transient
	private Long cantRequerimientos; 
	
	//bi-directional many-to-one association to Requerimiento
	@OneToMany(mappedBy="analista", fetch=FetchType.LAZY)
	private List<Requerimiento> requerimientos;

	public Analista() {
	}
	
	public Analista(Persona persona) {
		super(persona);
	}
	
	public Analista(String cedula) {
		this.cedula = cedula;
	}
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Analista(Long cantRequerimientos, Integer id) {
		super();
		this.cantRequerimientos = cantRequerimientos;
		this.id = id;
	}
	
	public List<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}
	
	public Requerimiento addRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().add(requerimiento);
		requerimiento.setAnalista(this);

		return requerimiento;
	}

	public Requerimiento removeRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().remove(requerimiento);
		requerimiento.setAnalista(null);

		return requerimiento;
	}

	//@Transient
	public Long getCantRequerimientos() {
		return cantRequerimientos;
	}

	public void setCantRequerimientos(Long cantRequerimientos) {
		this.cantRequerimientos = cantRequerimientos;
	}
	
}