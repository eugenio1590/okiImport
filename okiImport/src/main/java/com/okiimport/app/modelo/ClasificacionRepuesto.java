package com.okiimport.app.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the clasificacion_repuesto database table.
 * 
 */
@Entity
@Table(name="clasificacion_repuesto")
@NamedQuery(name="ClasificacionRepuesto.findAll", query="SELECT c FROM ClasificacionRepuesto c")
public class ClasificacionRepuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="clasificacion_repuesto_id_seq")
	@SequenceGenerator(name="clasificacion_repuesto_id_seq", sequenceName="clasificacion_repuesto_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_clasificacion_repuesto")
	private Integer idClasificacionRepuesto;

	private String descripcion;
	
	private String estatus;
	
	@OneToMany(mappedBy="clasificacionRepuesto", fetch=FetchType.LAZY)
	private List<DetalleRequerimiento> detalleRequerimientos;

	public ClasificacionRepuesto() {
	}

	public Integer getIdClasificacionRepuesto() {
		return idClasificacionRepuesto;
	}

	public void setIdClasificacionRepuesto(Integer idClasificacionRepuesto) {
		this.idClasificacionRepuesto = idClasificacionRepuesto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<DetalleRequerimiento> getDetalleRequerimientos() {
		return detalleRequerimientos;
	}

	public void setDetalleRequerimientos(
			List<DetalleRequerimiento> detalleRequerimientos) {
		this.detalleRequerimientos = detalleRequerimientos;
	}

	public DetalleRequerimiento addDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
		getDetalleRequerimientos().add(detalleRequerimiento);
		detalleRequerimiento.setClasificacionRepuesto(this);

		return detalleRequerimiento;
	}

	public DetalleRequerimiento removeDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
		getDetalleRequerimientos().remove(detalleRequerimiento);
		detalleRequerimiento.setClasificacionRepuesto(null);

		return detalleRequerimiento;
	}

}