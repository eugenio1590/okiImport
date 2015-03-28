package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;


/**
 * The persistent class for the analista database table.
 * 
 */
@Entity
@NamedQuery(name="Analista.findAll", query="SELECT a FROM Analista a")
public class Analista extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Generated(GenerationTime.INSERT)
	@Column(name="id_analista", columnDefinition = "serial")
	private Integer idAnalista;
	
	@OneToOne(mappedBy="analista")
	private Usuario usuario;

	public Analista() {
	}

	public Integer getIdAnalista() {
		return this.idAnalista;
	}

	public void setIdAnalista(Integer idAnalista) {
		this.idAnalista = idAnalista;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}