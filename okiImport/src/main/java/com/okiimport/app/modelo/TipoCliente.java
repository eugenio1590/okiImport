package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the tipo_cliente database table.
 * 
 */
@Entity
@Table(name="tipo_cliente")
@NamedQuery(name="TipoCliente.findAll", query="SELECT t FROM TipoCliente t")
public class TipoCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_cliente")
	private Integer idTipoCliente;

	private String nombre;
	
	@OneToMany(mappedBy="tipoCliente", fetch=FetchType.LAZY)
	private List<Cliente> clientes;

	public TipoCliente() {
	}

	public Integer getIdTipoCliente() {
		return this.idTipoCliente;
	}

	public void setIdTipoCliente(Integer idTipoCliente) {
		this.idTipoCliente = idTipoCliente;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}