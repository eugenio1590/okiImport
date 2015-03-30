package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;


/**
 * The persistent class for the requerimiento database table.
 * 
 */
@Entity
@NamedQuery(name="Requerimiento.findAll", query="SELECT r FROM Requerimiento r")
public class Requerimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="anno_v")
	private Timestamp annoV;

	private String estatus;

	@Column(name="fecha_cierre")
	private Timestamp fechaCierre;

	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;

	@Column(name="fecha_vencimiento")
	private Timestamp fechaVencimiento;

	@Column(name="id_analista")
	private Integer idAnalista;

	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	@Column(name="id_marca_v")
	private Integer idMarcaV;

	@Column(name="id_motor_v")
	private Integer idMotorV;

    @Id
    //@Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_requerimiento", columnDefinition = "serial")
	private Integer idRequerimiento;

	@Column(name="id_traccion_v")
	private Integer idTraccionV;

	@Column(name="modelo_v")
	private String modeloV;

	@Column(name="serial_carroceria_v")
	private String serialCarroceriaV;

	@Column(name="transmision_v")
	private Boolean transmisionV;

	public Requerimiento() {
	}

	public Timestamp getAnnoV() {
		return this.annoV;
	}

	public void setAnnoV(Timestamp annoV) {
		this.annoV = annoV;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Timestamp getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Integer getIdAnalista() {
		return this.idAnalista;
	}

	public void setIdAnalista(Integer idAnalista) {
		this.idAnalista = idAnalista;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getIdMarcaV() {
		return this.idMarcaV;
	}

	public void setIdMarcaV(Integer idMarcaV) {
		this.idMarcaV = idMarcaV;
	}

	public Integer getIdMotorV() {
		return this.idMotorV;
	}

	public void setIdMotorV(Integer idMotorV) {
		this.idMotorV = idMotorV;
	}

	public Integer getIdRequerimiento() {
		return this.idRequerimiento;
	}

	public void setIdRequerimiento(Integer idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}

	public Integer getIdTraccionV() {
		return this.idTraccionV;
	}

	public void setIdTraccionV(Integer idTraccionV) {
		this.idTraccionV = idTraccionV;
	}

	public String getModeloV() {
		return this.modeloV;
	}

	public void setModeloV(String modeloV) {
		this.modeloV = modeloV;
	}

	public String getSerialCarroceriaV() {
		return this.serialCarroceriaV;
	}

	public void setSerialCarroceriaV(String serialCarroceriaV) {
		this.serialCarroceriaV = serialCarroceriaV;
	}

	public Boolean getTransmisionV() {
		return this.transmisionV;
	}

	public void setTransmisionV(Boolean transmisionV) {
		this.transmisionV = transmisionV;
	}

}