package com.okiimport.app.modelo;

import com.okiimport.app.modelo.DetalleCotizacion;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: DetalleCotizacionInternacional
 *
 */
@Entity
@NamedQuery(name="DetalleCotizacionInternacional.findAll", query="SELECT d FROM DetalleCotizacionInternacional d")
@PrimaryKeyJoinColumn(name="id_detalle_cotizacion_internacional")
public class DetalleCotizacionInternacional extends DetalleCotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long largo;

	private Long ancho;

	private Long alto;

	private Long peso;

	public DetalleCotizacionInternacional() {
		super();
	}

	public Long getLargo() {
		return largo;
	}

	public void setLargo(Long largo) {
		this.largo = largo;
	}

	public Long getAncho() {
		return ancho;
	}

	public void setAncho(Long ancho) {
		this.ancho = ancho;
	}

	public Long getAlto() {
		return alto;
	}

	public void setAlto(Long alto) {
		this.alto = alto;
	}

	public Long getPeso() {
		return peso;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	public Long volumen(){
		return largo*ancho*alto;
	}
	
	public Float calcularPesoVolumetrico(){
		Float pesoV = volumen()/new Float(1.66);
		return (pesoV>peso) ? pesoV : peso;
	}
	
	public Float calcularPesoDeCubicaje(){
		Float pesoC = volumen()/new Float(1728);
		return (pesoC<5) ? 5 : pesoC;
	}
}
