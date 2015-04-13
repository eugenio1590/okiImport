package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the marca_vehiculo database table.
 * 
 */
@Entity
@Table(name="marca_repuesto")
@NamedQuery(name="MarcaRepuesto.findAll", query="SELECT m FROM MarcaRepuesto m")
public class MarcaRepuesto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="marca_repuesto_id_seq")
	@SequenceGenerator(name="marca_repuesto_id_seq", sequenceName="marca_repuesto_id_seq", initialValue=1, allocationSize=1)
	private Integer idMarcaRepuesto;
	
	private String nombre;
	
	@ManyToMany(mappedBy="marcaRepuestos", fetch=FetchType.LAZY)
	private List<Proveedor> proveedors;
	
	public MarcaRepuesto() {
	}

	public Integer getIdMarcaRepuesto() {
		return idMarcaRepuesto;
	}

	public void setIdMarcaRepuesto(Integer idMarcaRepuesto) {
		this.idMarcaRepuesto = idMarcaRepuesto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Proveedor> getProveedors() {
		return proveedors;
	}

	public void setProveedors(List<Proveedor> proveedors) {
		this.proveedors = proveedors;
	}

}
