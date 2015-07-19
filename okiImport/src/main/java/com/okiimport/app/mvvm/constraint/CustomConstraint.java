package com.okiimport.app.mvvm.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.SimpleConstraint;

public abstract class CustomConstraint implements Constraint {
	
	/**ENUM OF CONSTRAINT POSIBLE*/
	public enum EConstraint {
		NO_POSITIVE(SimpleConstraint.NO_POSITIVE, null),
		NO_NEGATIVE(SimpleConstraint.NO_NEGATIVE, null),
		NO_ZERO(SimpleConstraint.NO_ZERO, null),
		NO_EMPTY(SimpleConstraint.NO_EMPTY, null),
		STRICT(SimpleConstraint.STRICT, null),
		SERVER(SimpleConstraint.SERVER, null),
		NO_FUTURE(SimpleConstraint.NO_FUTURE, null),
		NO_PAST(SimpleConstraint.NO_PAST, null),
		NO_TODAY(SimpleConstraint.NO_TODAY, null),
		BEFORE_START(SimpleConstraint.BEFORE_START, null),
		BEFORE_END(SimpleConstraint.BEFORE_END, null),
		END_BEFORE(SimpleConstraint.END_BEFORE, null),
		END_AFTER(SimpleConstraint.END_AFTER, null),
		AFTER_END(SimpleConstraint.AFTER_END, null),
		AFTER_START(SimpleConstraint.AFTER_START, null),
		START_AFTER(SimpleConstraint.START_AFTER, null),
		START_BEFORE(SimpleConstraint.START_BEFORE, null),
		OVERLAP(SimpleConstraint.OVERLAP, null),
		OVERLAP_END(SimpleConstraint.OVERLAP_END, null),
		OVERLAP_BEFORE(SimpleConstraint.OVERLAP_BEFORE, null),
		OVERLAP_AFTER(SimpleConstraint.OVERLAP_AFTER, null),
		AT_POINTER(SimpleConstraint.AT_POINTER, null),
		AFTER_POINTER(SimpleConstraint.AFTER_POINTER, null),
		CUSTOM(-1, null);
		
		//ATRIBUTOS
		private int value;
		private String mensaje;
		
		/**Constructor*/
		private EConstraint(int value, String mensaje) {
			// TODO Auto-generated constructor stub
			this.value = value;
			this.mensaje = mensaje;
		}
		
		/**GETTERS Y SETTERS*/
		public int getValue(){
			return this.value;
		}
		
		public String getMensaje(){
			return this.mensaje;
		}
		
		/**METODOS ESTATICOS DE LA CLASE*/
		public static final boolean checkCustomValue(EConstraint... eConstraints){
			for(EConstraint constraint : eConstraints)
				if(constraint.equals(EConstraint.CUSTOM))
					return true;
			
			return false;
		}
	}
	
	//GUI
	protected Component parent;
	protected Separator separator;
	protected Label componentError;
	
	//ATRIBUTOS
	private EConstraint[] eConstraints;
	
	/**Constructor*/
	public CustomConstraint(EConstraint... eConstraints){
		if(!EConstraint.checkCustomValue(eConstraints))
			eConstraints[eConstraints.length]=EConstraint.CUSTOM;
		
		this.eConstraints = eConstraints;
	}

	/**METODOS OVERRIDE*/
	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		// TODO Auto-generated method stub
		SimpleConstraint simpleCostraint;
		for(EConstraint constraint : eConstraints){
			if(parent==null && comp!=null)
				parent = comp.getParent();
			
			if(constraint.equals(EConstraint.CUSTOM)){
				validateCustom(comp, value);
			}
			else {
				if(parent!=null /*&& separator!=null*/ && componentError!=null){
					//parent.removeChild(separator);
					parent.removeChild(componentError);
				}
				simpleCostraint = new SimpleConstraint(constraint.getValue());
				simpleCostraint.validate(comp, value);
			}
		}
	}
	
	/**METODOS ABSTRACTOS DE LA CLASE*/
	protected abstract void validateCustom(Component comp, Object value) throws WrongValueException;
}
