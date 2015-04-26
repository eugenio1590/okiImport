package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
@PrimaryKeyJoinColumn(name="id_proveedor")
public class Proveedor extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	private String estatus;
	
	//bi-directional many-to-many association to Cotizacion
	@OneToMany(mappedBy="proveedor", fetch=FetchType.LAZY, orphanRemoval=true)
	private List<Cotizacion> cotizacions;
		
	//bi-directional many-to-many association to MarcaRepuesto
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="proveedor_marca_vehiculo"
		, joinColumns={
				@JoinColumn(name="id_proveedor")
		}
		, inverseJoinColumns={
				@JoinColumn(name="id_marca_vehiculo")
		}
	)
	private List<MarcaVehiculo> marcaVehiculos;


	//bi-directional many-to-many association to MarcaRepuesto
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="proveedor_clasificacion_repuesto"
		, joinColumns={
				@JoinColumn(name="id_proveedor")
		}
		, inverseJoinColumns={
				@JoinColumn(name="id_clasificacion_repuesto")
		}
	)
	private List<ClasificacionRepuesto> clasificacionRepuestos;
		
	public Proveedor() {
		marcaVehiculos = new ArrayList<MarcaVehiculo>();
		clasificacionRepuestos = new ArrayList<ClasificacionRepuesto>();
	}
	
	public Proveedor(Persona persona) {
		super(persona);
	}
	
	public Proveedor(String cedula) {
		super.cedula = cedula;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<Cotizacion> getCotizacions() {
		return cotizacions;
	}

	public void setCotizacions(List<Cotizacion> cotizacions) {
		this.cotizacions = cotizacions;
	}
	
	public Cotizacion addCotizacion(Cotizacion cotizacion) {
		getCotizacions().add(cotizacion);
		cotizacion.setProveedor(this);

		return cotizacion;
	}

	public Cotizacion removeDetalleRequerimiento(Cotizacion cotizacion) {
		getCotizacions().remove(cotizacion);
		cotizacion.setProveedor(null);

		return cotizacion;
	}

	public List<MarcaVehiculo> getMarcaVehiculos() {
		return marcaVehiculos;
	}

	public void setMarcaVehiculos(List<MarcaVehiculo> marcaVehiculos) {
		this.marcaVehiculos = marcaVehiculos;
	}

	public List<ClasificacionRepuesto> getClasificacionRepuestos() {
		return clasificacionRepuestos;
	}

	public void setClasificacionRepuestos(
			List<ClasificacionRepuesto> clasificacionRepuestos) {
		this.clasificacionRepuestos = clasificacionRepuestos;
	}
}