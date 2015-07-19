package com.okiimport.app.mvvm.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.SimpleConstraint;

public abstract class CustomConstraint implements Constraint {
	
	public enum EConstraint {
		NO_POSITIVE(SimpleConstraint.NO_POSITIVE),
		NO_NEGATIVE(SimpleConstraint.NO_NEGATIVE),
		NO_ZERO(SimpleConstraint.NO_ZERO),
		NO_EMPTY(SimpleConstraint.NO_EMPTY),
		STRICT(SimpleConstraint.STRICT),
		SERVER(SimpleConstraint.SERVER),
		NO_FUTURE(SimpleConstraint.NO_FUTURE),
		NO_PAST(SimpleConstraint.NO_PAST),
		NO_TODAY(SimpleConstraint.NO_TODAY),
		BEFORE_START(SimpleConstraint.BEFORE_START),
		BEFORE_END(SimpleConstraint.BEFORE_END),
		END_BEFORE(SimpleConstraint.END_BEFORE),
		END_AFTER(SimpleConstraint.END_AFTER),
		AFTER_END(SimpleConstraint.AFTER_END),
		AFTER_START(SimpleConstraint.AFTER_START),
		START_AFTER(SimpleConstraint.START_AFTER),
		START_BEFORE(SimpleConstraint.START_BEFORE),
		OVERLAP(SimpleConstraint.OVERLAP),
		OVERLAP_END(SimpleConstraint.OVERLAP_END),
		OVERLAP_BEFORE(SimpleConstraint.OVERLAP_BEFORE),
		OVERLAP_AFTER(SimpleConstraint.OVERLAP_AFTER),
		AT_POINTER(SimpleConstraint.AT_POINTER),
		AFTER_POINTER(SimpleConstraint.AFTER_POINTER);
		
		private int value;
		
		private EConstraint(int value) {
			// TODO Auto-generated constructor stub
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
	}
	
	private EConstraint[] eConstraints;
	
	public CustomConstraint(EConstraint... eConstraints){
		this.eConstraints = eConstraints;
	}

	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		// TODO Auto-generated method stub
		
	}

	

}
