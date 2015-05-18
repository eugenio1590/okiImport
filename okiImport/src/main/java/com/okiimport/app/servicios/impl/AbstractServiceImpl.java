package com.okiimport.app.servicios.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.mail.MailService;
import com.okiimport.app.mvvm.BeanInjector;

public abstract class AbstractServiceImpl {
	
	//Atributos
	protected Calendar calendar = GregorianCalendar.getInstance();

	@Autowired
	@BeanInjector("mailService")
	protected MailService mailService;

	public AbstractServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**SETTERS Y GETTERS*/
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
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
