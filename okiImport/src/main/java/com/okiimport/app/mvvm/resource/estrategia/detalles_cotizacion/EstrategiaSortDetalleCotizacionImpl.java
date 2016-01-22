package com.okiimport.app.mvvm.resource.estrategia.detalles_cotizacion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.okiimport.app.model.DetalleCotizacion;

public abstract class EstrategiaSortDetalleCotizacionImpl<DC extends DetalleCotizacion> implements
		EstrategiaSortDetalleCotizacion<DC> {

	@Override
	public void sortDetalleCotizacion(List<DC> detallesCotizacion,
			final EstrategiaSortDetalleCotizacion<DC> estrategiaResolve) {
		if(!detallesCotizacion.isEmpty() && detallesCotizacion.size()>1){
			Collections.sort(detallesCotizacion, new Comparator<DC>(){

				@Override
				public int compare(DC object1, DC object2) {
					return comparatorResolve(object1, object2, estrategiaResolve);
				}
				
			});
		}
	}
}
