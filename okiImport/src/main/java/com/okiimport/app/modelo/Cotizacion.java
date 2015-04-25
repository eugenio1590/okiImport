package com.okiimport.app.modelo;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="cotizacion")
public class Cotizacion {
	
	@Id
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedor;
	
	@OneToMany(mappedBy="cotizacion",fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<DetalleCotizacion> detalleCotizacions;

	public Cotizacion() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<DetalleCotizacion> getDetalleCotizacions() {
		return detalleCotizacions;
	}

	public void setDetalleCotizacions(List<DetalleCotizacion> detalleCotizacions) {
		this.detalleCotizacions = detalleCotizacions;
	}

}
