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
@PrimaryKeyJoinColumn(name="id_analista", columnDefinition="integer")
public class Analista extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Requerimiento
	@OneToMany(mappedBy="analista", fetch=FetchType.LAZY)
	private List<Requerimiento> requerimientos;

	public Analista() {
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

}