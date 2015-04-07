package com.okiimport.app.maestros.servicios.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.dao.AnalistaDAO;
import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.maestros.dao.ProveedorDAO;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;

public class SMaestrosImpl extends AbstractServiceImpl implements SMaestros {

	@Autowired
	@BeanInjector("marcaVehiculoDAO")
	private MarcaVehiculoDAO marcaVehiculoDAO;
	
	@Autowired
	@BeanInjector("clienteDAO")
    private ClienteDAO clienteDAO;
	
	@Autowired
	@BeanInjector("analistaDAO")
	private AnalistaDAO analistaDAO;
	
	@Autowired
	@BeanInjector("proveedorDAO")
	private ProveedorDAO proveedorDAO;
		
	//Marcas
	@Override
	public Map<String, Object> ConsultarMarca(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)marcaVehiculoDAO.countAll()).intValue());
		Parametros.put("marcas", marcaVehiculoDAO.findAll(page*limit, limit));
		return Parametros;
	}
	
	//Cliente
	@Override
	public Cliente registrarOActualizarCliente(Cliente cliente) {
		Cliente temp = clienteDAO.consultarPersona(new Cliente(cliente.getCedula()));
		if (temp==null)
			cliente=clienteDAO.save(cliente);
		else{
			cliente.setId(temp.getId());
			cliente=clienteDAO.update(cliente);
		}
		return cliente;
	}
	
	//Analista
	@Override
	public Map<String, Object> consultarAnalistasSinUsuarios(Persona personaF,  String fieldSort, Boolean sortDirection, 
			int pagina, int limit){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", analistaDAO.consultarAnalistasSinUsuarios(personaF, fieldSort, sortDirection, 0, -1).size());
		parametros.put("analistas", analistaDAO.consultarAnalistasSinUsuarios(personaF, fieldSort, sortDirection, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public Map<String, Object> consultarAdministradoresSinUsuarios(Persona personaF,  String fieldSort, Boolean sortDirection, 
			int pagina, int limit){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", analistaDAO.consultarAdministradoresSinUsuarios(personaF, fieldSort, sortDirection, 0, -1).size());
		parametros.put("administradores", analistaDAO.consultarAdministradoresSinUsuarios(personaF, fieldSort, sortDirection, pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int page, int limit){
		return analistaDAO.consultarCantRequerimientos(estatus, page, limit);
	}
	
	//Proveedores
	@Override
	public Map<String, Object> consultarProveedoresSinUsuarios(Persona personaF, String fieldSort, Boolean sortDirection,
			int pagina, int limit){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", proveedorDAO.consultarProveedoresSinUsuarios(personaF, fieldSort, sortDirection, 0, -1).size());
		parametros.put("proveedores", proveedorDAO.consultarProveedoresSinUsuarios(personaF, fieldSort, sortDirection, pagina*limit, limit));
		return parametros;
	}

	/**SETTERS Y GETTERS*/
	public MarcaVehiculoDAO getMarcaVehiculoDAO() {
		return marcaVehiculoDAO;
	}
	public void setMarcaVehiculoDAO(MarcaVehiculoDAO marcaVehiculoDAO) {
		this.marcaVehiculoDAO = marcaVehiculoDAO;
	}
	
	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public AnalistaDAO getAnalistaDAO() {
		return analistaDAO;
	}

	public void setAnalistaDAO(AnalistaDAO analistaDAO) {
		this.analistaDAO = analistaDAO;
	}

	public ProveedorDAO getProveedorDAO() {
		return proveedorDAO;
	}

	public void setProveedorDAO(ProveedorDAO proveedorDAO) {
		this.proveedorDAO = proveedorDAO;
	}
}
