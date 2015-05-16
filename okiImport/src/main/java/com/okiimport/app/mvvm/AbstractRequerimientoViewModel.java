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
	
	protected static List <ModeloCombo<Boolean>> llenarListaTipoPersona(){
		List <ModeloCombo<Boolean>> listaTipoPersona = new ArrayList<ModeloCombo<Boolean>>();
		listaTipoPersona.add(new ModeloCombo<Boolean>("J", true));
		listaTipoPersona.add(new ModeloCombo<Boolean>("V", false));
		return listaTipoPersona;
	}
	
	protected static List<ModeloCombo<String>> llenarListaEstatus(){
		List<ModeloCombo<String>> listaEstatus = new ArrayList<ModeloCombo<String>>();
		listaEstatus.add(new ModeloCombo<String>("Emitido", "CR"));
		listaEstatus.add(new ModeloCombo<String>("Recibido y Editado", "E"));
		listaEstatus.add(new ModeloCombo<String>("Enviado a Proveedores", "EP"));
		listaEstatus.add(new ModeloCombo<String>("Con Cotizaciones Asignadas", "CT"));
		listaEstatus.add(new ModeloCombo<String>("Ofertado", "O"));
		listaEstatus.add(new ModeloCombo<String>("Concretado", "CC"));
		return listaEstatus;
	}

}
