package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the requerimiento database table.
 * 
 */
@Entity
@Table(name="requerimiento")
@NamedQuery(name="Requerimiento.findAll", query="SELECT r FROM Requerimiento r")
public class Requerimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="requerimiento_id_seq")
	@SequenceGenerator(name="requerimiento_id_seq", sequenceName="requerimiento_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_requerimiento")
	private Integer idRequerimiento;

	@Column(name="anno_v")
	private Timestamp annoV;

	private String estatus;

	@Column(name="fecha_cierre", columnDefinition="date")
	private Timestamp fechaCierre;

	@Column(name="fecha_creacion", columnDefinition="date")
	private Timestamp fechaCreacion;

	@Column(name="fecha_vencimiento", columnDefinition="date")
	private Timestamp fechaVencimiento;

	@Column(name="modelo_v")
	private String modeloV;

	@Column(name="serial_carroceria_v")
	private String serialCarroceriaV;

	@Column(name="transmision_v")
	private Boolean transmisionV;
	
	@Column(name="traccion_v")
	private Boolean traccionV;
	
	//bi-directional many-to-one association to Analista
	@ManyToOne
	@JoinColumn(name="id_analista")
	private Analista analista;
	
	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to MarcaVehiculo
	@ManyToOne
	@JoinColumn(name="id_marca_v")
	private MarcaVehiculo marcaVehiculo;

	//bi-directional many-to-one association to Motor
	@ManyToOne
	@JoinColumn(name="id_motor_v")
	private Motor motor;
	
	//bi-directional many-to-one association to DetalleRequerimiento
	@OneToMany(mappedBy="requerimiento", fetch=FetchType.LAZY)
	private List<DetalleRequerimiento> detalleRequerimientos;

	public Requerimiento() {
		detalleRequerimientos = new ArrayList<DetalleRequerimiento>();
	}

	public Integer getIdRequerimiento() {
		return this.idRequerimiento;
	}

	public void setIdRequerimiento(Integer idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
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

	public Analista getAnalista() {
		return analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setTransmisionV(Boolean transmisionV) {
		this.transmisionV = transmisionV;
	}

	public MarcaVehiculo getMarcaVehiculo() {
		return this.marcaVehiculo;
	}

	public Boolean getTraccionV() {
		return traccionV;
	}

	public void setTraccionV(Boolean traccionV) {
		this.traccionV = traccionV;
	}

	public void setMarcaVehiculo(MarcaVehiculo marcaVehiculo) {
		this.marcaVehiculo = marcaVehiculo;
	}

	public Motor getMotor() {
		return this.motor;
	}

	public void setMotor(Motor motor) {
		this.motor = motor;
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
		detalleRequerimiento.setRequerimiento(this);

		return detalleRequerimiento;
	}

	public DetalleRequerimiento removeDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
		getDetalleRequerimientos().remove(detalleRequerimiento);
		detalleRequerimiento.setRequerimiento(null);

		return detalleRequerimiento;
	}

}