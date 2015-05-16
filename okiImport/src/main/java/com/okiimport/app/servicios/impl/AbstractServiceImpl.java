package com.okiimport.app.servicios.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class AbstractServiceImpl {
	
	//Atributos
	protected Calendar calendar = GregorianCalendar.getInstance();

	public AbstractServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**SUMA Y RESTAS PARA FECHAS*/
	private static Date sumarORestarFecha(Date fecha, int field, int value){
		Calendar calendar = GregorianCalendar.getInstance();
		Date fechaTemp = calendar.getTime();
		if(fecha!=null){
			calendar.setTime(fecha);
			calendar.add(field, value);
			fecha = calendar.getTime();
			calendar.setTime(fechaTemp);
		}
		return fecha;
	}
	
	public static Date sumarORestarFDia(Date fecha, int dias){
		return sumarORestarFecha(fecha, Calendar.DAY_OF_YEAR, dias);
	}
	
	public static Date sumarORestarFMes(Date fecha, int meses){
		return sumarORestarFecha(fecha, Calendar.MONTH, meses);
	}
	
	public static Date sumarORestarFAnno(Date fecha, int annos){
		return sumarORestarFecha(fecha, Calendar.YEAR, annos);
	}

}
