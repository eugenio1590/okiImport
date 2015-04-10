package com.okiimport.app.maestros.servicios.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.dao.ClasificacionRepuestoDAO;
import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.maestros.dao.MotorDAO;
import com.okiimport.app.maestros.dao.ProveedorDAO;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;

public class SMaestrosImpl extends AbstractServiceImpl implements SMaestros {

	@Autowired
	@BeanInjector("marcaVehiculoDAO")
	private MarcaVehiculoDAO marcaVehiculoDAO;
	
	@Autowired
	@BeanInjector("clasificacionRepuestoDAO")
	private ClasificacionRepuestoDAO clasificacionRepuestoDAO;
	
	

	@Autowired
	@BeanInjector("clienteDAO")
    private ClienteDAO clienteDAO;
	
	@Autowired
	@BeanInjector("motorDAO")
	private MotorDAO motorDAO;
	
	@Autowired
	@BeanInjector("proveedorDAO")
	private ProveedorDAO proveedorDAO;

	
	
	@Override
	public Map<String, Object> ConsultarMarca(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", marcaVehiculoDAO.listaMarcasVehiculosActivas(0, -1).size());
		Parametros.put("marcas", marcaVehiculoDAO.listaMarcasVehiculosActivas(page*limit, limit));
		return Parametros;
	}
	
	@Override
	public Map<String, Object> ConsultarClasificacionRepuesto(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)clasificacionRepuestoDAO.countAll()).intValue());
		Parametros.put("clasificacionRepuesto", clasificacionRepuestoDAO.findAll(page*limit, limit));
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
	
	@Override
	public Proveedor registrarProveedor(Proveedor proveedor) {
	   return proveedorDAO.save(proveedor);
	}
	
	
	@Override
	public Map<String, Object> ConsultarMotor(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)motorDAO.countAll()).intValue());
		Parametros.put("motor", motorDAO.findAll(page*limit, limit));
		return Parametros;
	}


	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	public ClasificacionRepuestoDAO getClasificacionRepuestoDAO() {
		return clasificacionRepuestoDAO;
	}

	public void setClasificacionRepuestoDAO(
			ClasificacionRepuestoDAO clasificacionRepuestoDAO) {
		this.clasificacionRepuestoDAO = clasificacionRepuestoDAO;
	}
}
