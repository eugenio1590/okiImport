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

	//bi-directional one-to-one association to TipoCliente
	@ManyToOne
	@JoinColumn(name="id_tipo_cliente")
	private TipoCliente tipoCliente;
	
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY)
	private List<Requerimiento> requerimientos;

	public Cliente() {
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
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