package com.okiimport.app.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the oferta database table.
 * 
 */
@Entity
@Table(name="oferta")
@NamedQuery(name="Oferta.findAll", query="SELECT o FROM Oferta o")
public class Oferta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="oferta_id_seq")
	@SequenceGenerator(name="oferta_id_seq", sequenceName="oferta_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_oferta")
	private Integer idOferta;
	
	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;
	
	private String observacion;
	
	private String estatus;
	
	@OneToMany(mappedBy="oferta", fetch=FetchType.LAZY)
	private List<DetalleOferta> detalleOfertas;

	public Oferta() {
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	public List<DetalleOferta> getDetalleOfertas() {
		return detalleOfertas;
	}

	public void setDetalleOfertas(List<DetalleOferta> detalleOfertas) {
		this.detalleOfertas = detalleOfertas;
	}
	
	public DetalleOferta addDetalleOferta(DetalleOferta detalleOferta){
		getDetalleOfertas().add(detalleOferta);
		detalleOferta.setOferta(this);
		
		return detalleOferta;
	}
	
	public DetalleOferta removeDetalleOferta(DetalleOferta detalleOferta){
		getDetalleOfertas().remove(detalleOferta);
		detalleOferta.setOferta(null);
		
		return detalleOferta;
	}

}
