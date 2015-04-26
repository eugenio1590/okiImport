package com.okiimport.app.mvvm;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRequerimientoViewModel extends AbstractViewModel {

	protected static List <ModeloCombo<Boolean>> llenarListaTraccion(){
		List <ModeloCombo<Boolean>> listaTraccion = new ArrayList<ModeloCombo<Boolean>>();
		listaTraccion.add(new ModeloCombo<Boolean>("4x2", true));
		listaTraccion.add(new ModeloCombo<Boolean>("4x4", false));
		return listaTraccion;
	}
	
	protected static List <ModeloCombo<Boolean>> llenarListaTransmision(){
		List <ModeloCombo<Boolean>> listaTransmision = new ArrayList<ModeloCombo<Boolean>>();
		listaTransmision.add(new ModeloCombo<Boolean>("Automatico", true));
		listaTransmision.add(new ModeloCombo<Boolean>("Sincronico", false));
		return listaTransmision;
	}

}
