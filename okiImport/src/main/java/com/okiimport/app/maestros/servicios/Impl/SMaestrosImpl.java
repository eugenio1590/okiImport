package com.okiimport.app.maestros.servicios.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;

public class SMaestrosImpl extends AbstractServiceImpl implements SMaestros {

	@Autowired
	@BeanInjector("marcaVehiculoDAO")
	private MarcaVehiculoDAO marcaVehiculoDAO;
	@BeanInjector("clienteDAO")
    private ClienteDAO clienteDAO;
	
	
	@Override
	public Map<String, Object> ConsultarMarca(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)marcaVehiculoDAO.countAll()).intValue());
		Parametros.put("marcas", marcaVehiculoDAO.findAll(page*limit, limit));
		return Parametros;
	}

	public MarcaVehiculoDAO getMarcaVehiculoDAO() {
		return marcaVehiculoDAO;
	}
	public void setMarcaVehiculoDAO(MarcaVehiculoDAO marcaVehiculoDAO) {
		this.marcaVehiculoDAO = marcaVehiculoDAO;
	}

	@Override
	public Cliente registrarCliente(Cliente cliente) {
		//cliente.setIdPersona((cliente.getIdPersona()==null)?-1:cliente.getIdPersona());
		//if (clienteDAO.findByPrimaryKey(cliente.getIdPersona())== null)
	   return clienteDAO.save(cliente);
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
}
