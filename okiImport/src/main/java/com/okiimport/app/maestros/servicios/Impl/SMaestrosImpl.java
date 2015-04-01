package com.okiimport.app.maestros.servicios.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.dao.AnalistaDAO;
import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.Cliente;
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
		
	@Override
	public Map<String, Object> ConsultarMarca(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)marcaVehiculoDAO.countAll()).intValue());
		Parametros.put("marcas", marcaVehiculoDAO.findAll(page*limit, limit));
		return Parametros;
	}
	
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
	
	@Override
	public Map<String, Object> consultarAnalistasSinUsuarios(int pagina, int limit){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", analistaDAO.consultarAnalistasSinUsuarios(0, -1).size());
		parametros.put("analistas", analistaDAO.consultarAnalistasSinUsuarios(pagina*limit, limit));
		return parametros;
	}
	
	@Override
	public List<Analista> consultarCantRequerimientos(List<String> estatus, int page, int limit){
		return analistaDAO.consultarCantRequerimientos(estatus, page, limit);
	}

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
}
