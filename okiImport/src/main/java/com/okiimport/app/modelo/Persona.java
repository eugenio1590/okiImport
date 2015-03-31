package com.okiimport.app.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@Table(name="persona")
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="person_type")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", columnDefinition = "serial", unique=true, nullable=false)
	private Integer id;

	private String apellido;

	private String cedula;

	private String correo;

	private String direccion;

	private String nombre;
	
	private String telefono;
	
	//bi-directional many-to-one association to Usuario (Relacion Poliformica)
	@OneToOne(mappedBy="persona")
	private Usuario usuario;

	public Persona() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}