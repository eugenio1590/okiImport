package com.okiimport.app.modelo;

import com.okiimport.app.modelo.DetalleCotizacion;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQuery(name="DetalleCotizacionInternacional.findAll", query="SELECT d FROM DetalleCotizacionInternacional d")
@PrimaryKeyJoinColumn(name="id_detalle_cotizacion_internacional")
public class DetalleCotizacionInternacional extends DetalleCotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(scale=2)
	private Float largo;

	@Column(scale=2)
	private Float ancho;

	@Column(scale=2)
	private Float alto;

	@Column(scale=2)
	private Float peso;
	
	private Boolean tipoFlete;
	
	private Boolean formaEnvio;
	
	@Transient
	private Float precioTotal;

	public DetalleCotizacionInternacional() {
		super();
	}

	public Float getLargo() {
		return largo;
	}

	public void setLargo(Float largo) {
		this.largo = largo;
	}

	public Float getAncho() {
		return ancho;
	}

	public void setAncho(Float ancho) {
		this.ancho = ancho;
	}

	public Float getAlto() {
		return alto;
	}

	public void setAlto(Float alto) {
		this.alto = alto;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}
	
	public Boolean getTipoFlete() {
		return tipoFlete;
	}

	public void setTipoFlete(Boolean tipoFlete) {
		this.tipoFlete = tipoFlete;
	}
	
	public Boolean getFormaEnvio() {
		return formaEnvio;
	}

	public void setFormaEnvio(Boolean formaEnvio) {
		this.formaEnvio = formaEnvio;
	}

	//Transient
	public Float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Float precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	public Float volumen(){
		return largo*ancho*alto;
	}

	public Float calcularPesoVolumetrico(){
		Float pesoV = volumen()/new Float(1.66);
		return (verificarCondPeso() && pesoV>peso) ? pesoV : peso;
	}
	
	public Float calcularPesoDeCubicaje(){
		Float pesoC = volumen()/new Float(1728);
		return (pesoC<5) ? 5 : pesoC;
	}
	
	public boolean verificarCondFlete(){
		return (largo!=null && ancho!=null && alto!=null);
	}
	
	public boolean verificarCondPeso(){
		return (this.peso!=null);
	}
	
	public Float calcularTotal(boolean conversion){
		precioTotal = new Float(0);
		
		if(this.tipoFlete!=null && this.tipoFlete) //CIF
			precioTotal = this.getPrecioFlete();
		else if(formaEnvio!=null && verificarCondFlete() && verificarCondPeso()){ //FOB
			Float pesoTotal = (formaEnvio) ?  /*Aereo*/ calcularPesoVolumetrico() : /*Maritimo*/ calcularPesoDeCubicaje();
			precioTotal = 5*pesoTotal; //Falta el Valor de la Libra = 5
		}
		
		Cotizacion cotizacion = this.getCotizacion();
		HistoricoMoneda hMoneda = null;
		if(cotizacion!=null && (hMoneda=cotizacion.getHistoricoMoneda())!=null && precioTotal!=null && conversion)
			precioTotal = precioTotal*hMoneda.getMontoConversion();
		else if(conversion)
			precioTotal = new Float(0);
			
		return precioTotal;
	}
	
}