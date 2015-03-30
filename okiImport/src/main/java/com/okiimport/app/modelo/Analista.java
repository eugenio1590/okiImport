package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the analista database table.
 * 
 */
@Entity
@NamedQuery(name="Analista.findAll", query="SELECT a FROM Analista a")
@AttributeOverride(name="id",column=@Column(name="id_analista", columnDefinition="serial"))
public class Analista extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Usuario
	@OneToOne(mappedBy="analista")
	private Usuario usuario;

	public Analista() {
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}