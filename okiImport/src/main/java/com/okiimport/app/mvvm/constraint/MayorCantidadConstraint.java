package com.okiimport.app.mvvm.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;

public class MayorCantidadConstraint extends CustomConstraint {
	
	//Atributos
	private Long cantidadRequerida;

	public MayorCantidadConstraint(Long cantidadRequerida, EConstraint... eConstraints) {
		// TODO Auto-generated constructor stub
		super(eConstraints);
		this.cantidadRequerida = cantidadRequerida;
	}

	@Override
	protected void validateCustom(Component comp, Object value) throws WrongValueException {
		// TODO Auto-generated method stub
		Integer cantidadOfrecida = (Integer) value;
		if(cantidadOfrecida!=null && cantidadRequerida!=null && cantidadOfrecida > cantidadRequerida){
			String mensaje = "No puede ser mayor que "+cantidadRequerida+" !";
			
			if(separator==null){
				separator = new Separator();
				separator.setWidth("100%");
				separator.setBar(true);
				separator.setStyle("color:red");
			}
			
			if(componentError==null){
				componentError = new Label();
				componentError.setWidth("100%");
				componentError.setStyle("color: red");
				
			}
			
			componentError.setValue(mensaje);
			//parent.appendChild(separator);
			parent.appendChild(componentError);
			//throw new WrongValueException(comp, null);
		}
		else {
			//parent.removeChild(separator);
			parent.removeChild(componentError);
		}
		
		parent.applyProperties();
			
	}

}
