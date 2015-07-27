package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

import com.okiimport.app.servicios.impl.AbstractServiceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private Integer annoV;

	private String estatus;

	@Column(name="fecha_cierre", columnDefinition="date")
	private Date fechaCierre;

	@Column(name="fecha_creacion", columnDefinition="date")
	private Timestamp fechaCreacion;
	
	@Column(name="fecha_solicitud")
	private Timestamp fechaSolicitud;

	@Column(name="fecha_vencimiento", columnDefinition="date")
	private Date fechaVencimiento;

	@Column(name="modelo_v")
	private String modeloV;

	@Column(name="serial_carroceria_v")
	private String serialCarroceriaV;

	@Column(name="transmision_v")
	private Boolean transmisionV;
	
	@Column(name="traccion_v")
	private Boolean traccionV;
	
	@Column(name="tipoRepuesto")
	private Boolean tipoRepuesto;
	
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
	
	//bi-directional one-to-many association to DetalleRequerimiento
	@OneToMany(mappedBy="requerimiento", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<DetalleRequerimiento> detalleRequerimientos;

	public Requerimiento() {
		detalleRequerimientos = new ArrayList<DetalleRequerimiento>();
	}
	
	public Requerimiento(Cliente cliente){
		this.cliente = cliente;
	}
	
	public Requerimiento(MarcaVehiculo marcaVehiculo){
		this.marcaVehiculo = marcaVehiculo;
	}
	
	public Requerimiento(Cliente cliente, MarcaVehiculo marcaVehiculo){
		this.cliente = cliente;
		this.marcaVehiculo = marcaVehiculo;
	}

	public Requerimiento(Integer idRequerimiento, String estatus, Date fechaCreacion, Date fechaVencimiento,
			String modeloV, Analista analista, Cliente cliente, MarcaVehiculo marcaVehiculo, Motor motor) {
		super();
		this.idRequerimiento = idRequerimiento;
		this.estatus = estatus;
		this.fechaCreacion = new Timestamp(fechaCreacion.getTime());
		this.fechaVencimiento = fechaVencimiento;
		this.modeloV = modeloV;
		this.analista = analista;
		this.cliente = cliente;
		this.marcaVehiculo = marcaVehiculo;
		this.motor = motor;
	}

	public Integer getIdRequerimiento() {
		return this.idRequerimiento;
	}

	public void setIdRequerimiento(Integer idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}

	public Integer getAnnoV() {
		return this.annoV;
	}

	public void setAnnoV(Integer annoV) {
		this.annoV = annoV;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Timestamp fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
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

	public Boolean getTipoRepuesto() {
		return tipoRepuesto;
	}

	public void setTipoRepuesto(Boolean tipoRepuesto) {
		this.tipoRepuesto = tipoRepuesto;
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
	
	/**METODOS PROPIOS DE LA CLASE*/
	public String determinarTransmision(){
		String texto = null;
		if(transmisionV!=null)
			texto = (transmisionV) ? "Automatico" : "Sincronico";
		return texto;
	}
	
	public String determinarTraccion(){
		String texto = null;
		if(traccionV!=null)
			texto = (traccionV) ? "4x2" : "4x4";
		return texto;
	}
	
	public String determinarTipoRepuesto(){
		String texto = null;
		if(tipoRepuesto!=null)
			texto = (tipoRepuesto) ? "Reemplazo" : "Original";
		else
			texto = "Indistinto";
		return texto;
	}
	
	public String determinarEstatus(){
		if(this.estatus.equalsIgnoreCase("CR"))
			return "Requerimiento Emitido";
		else if(this.estatus.equalsIgnoreCase("E"))
			return "Requerimiento Recibido y Editado por el Analista asignado";
		else if(this.estatus.equalsIgnoreCase("EP"))
			return "Requerimiento Enviado a Proveedores";
		else if(this.estatus.equalsIgnoreCase("CT"))
			return "Requerimiento con Cotizaciones Asignadas";
		else if(this.estatus.equalsIgnoreCase("O"))
			return "Requerimiento Ofertado";
		else if(this.estatus.equalsIgnoreCase("CC"))
			return "Requerimiento Concretado";
		return "";
	}

	public void especificarInformacionVehiculo(){
		String especificacion = "No Especificado";
		if (this.marcaVehiculo==null) 
			this.marcaVehiculo = new MarcaVehiculo(especificacion);
		
		if(this.modeloV==null)
			this.modeloV = especificacion;
		
		if(this.motor==null)
			this.motor = new Motor(especificacion);
	}
	
	public boolean editar(){
		return (this.estatus.equalsIgnoreCase("CR") || this.estatus.equalsIgnoreCase("E")) ? true : false;
	}
	
	public boolean editarCotizacion(){
		return (this.estatus.equalsIgnoreCase("EC") || this.estatus.equalsIgnoreCase("EP") || this.estatus.equalsIgnoreCase("CT"));
	}
	
	public boolean cerrarSolicitud(){
		boolean solicitud = false;
		if(this.fechaSolicitud!=null)
			solicitud = (AbstractServiceImpl.diferenciaHoras(fechaSolicitud, Calendar.getInstance().getTime()) >= 48) 
					? true : false;
		return solicitud;
	}
}
