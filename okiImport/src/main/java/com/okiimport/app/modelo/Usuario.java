package com.okiimport.app.modelo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.okiimport.app.mvvm.AbstractViewModel;

import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Generated(GenerationTime.INSERT)
	@Column(columnDefinition="serial", unique=true, nullable=false)
	private Integer id;

	@Column
	@Basic(fetch=FetchType.LAZY)
	private byte[] foto;
	
	@Column(length=20, unique=true)
	private String username;

	@Column(length=100)
	private String pasword;
	
	@Column(nullable=false)
	private Boolean activo;

	//bi-directional many-to-one association to PersistentLogin
	@OneToMany(mappedBy="usuario",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<PersistentLogin> persistentLogins;
	
	//bi-directional many-to-one association to HistoryLogin
	@OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)
	private List<HistoryLogin> historyLogins;
	
	/**USUARIOS ESPECIFICOS*/
	@Transient
	public static final String[] USUARIOS_ESPECIFICOS = new String[]{"analista", "proveedor"};
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_analista", referencedColumnName="id_analista", columnDefinition="integer",
		nullable=true)
	private Analista analista;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proveedor", referencedColumnName="id_proveedor", columnDefinition="integer",
		nullable=true)
	private Proveedor proveedor;

	public Usuario() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasword() {
		return this.pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<PersistentLogin> getPersistentLogins() {
		return this.persistentLogins;
	}

	public void setPersistentLogins(List<PersistentLogin> persistentLogins) {
		this.persistentLogins = persistentLogins;
	}

	public PersistentLogin addPersistentLogin(PersistentLogin persistentLogin) {
		getPersistentLogins().add(persistentLogin);
		persistentLogin.setUsuario(this);

		return persistentLogin;
	}

	public PersistentLogin removePersistentLogin(PersistentLogin persistentLogin) {
		getPersistentLogins().remove(persistentLogin);
		persistentLogin.setUsuario(null);

		return persistentLogin;
	}
	
	public Analista getAnalista() {
		return analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	/**METODOS PROPIOS DE LA CLASE*/
	public String getFoto64(){
		if(this.foto!=null)
			return AbstractViewModel.decodificarImagen(foto);
		else
			return null;
	}
	
	public String isActivo(){
		return (this.activo) ? "Activo" : "Inactivo";
	}
	
	public void asignarUsuario(Persona persona){
		if(persona instanceof Analista)
			this.setAnalista((Analista) persona);
		else if(persona instanceof Proveedor)
			this.setProveedor((Proveedor) persona);
	}
	
	public Persona obtenerUsuario(){
		if(this.analista!=null)
			return this.getAnalista();
		else if(this.proveedor!=null)
			return this.getProveedor();
		
		return null;
	}

}