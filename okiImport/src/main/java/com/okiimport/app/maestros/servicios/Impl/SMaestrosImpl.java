package com.okiimport.app.maestros.servicios.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.okiimport.app.maestros.dao.AnalistaDAO;
import com.okiimport.app.maestros.dao.CiudadDAO;
import com.okiimport.app.maestros.dao.ClasificacionRepuestoDAO;
import com.okiimport.app.maestros.dao.ClienteDAO;
import com.okiimport.app.maestros.dao.EstadoDAO;
import com.okiimport.app.maestros.dao.MarcaVehiculoDAO;
import com.okiimport.app.maestros.dao.MotorDAO;
import com.okiimport.app.maestros.dao.PersonaDAO;
import com.okiimport.app.maestros.dao.ProveedorDAO;
import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Analista;
import com.okiimport.app.modelo.ClasificacionRepuesto;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.MarcaVehiculo;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.servicios.impl.AbstractServiceImpl;

public class SMaestrosImpl extends AbstractServiceImpl implements SMaestros {

	@Autowired
	@BeanInjector("marcaVehiculoDAO")
	private MarcaVehiculoDAO marcaVehiculoDAO;
	
	@Autowired
	@BeanInjector("estadoDAO")
	private EstadoDAO estadoDAO;
	
	@Autowired
	@BeanInjector("ciudadDAO")
	private CiudadDAO ciudadDAO;
	
	@Autowired
	@BeanInjector("clienteDAO")
    private ClienteDAO clienteDAO;
	
	@Autowired
	@BeanInjector("analistaDAO")
	private AnalistaDAO analistaDAO;
	
	@Autowired
	@BeanInjector("proveedorDAO")
	private ProveedorDAO proveedorDAO;
	
	@Autowired
	@BeanInjector("clasificacionRepuestoDAO")
	private ClasificacionRepuestoDAO clasificacionRepuestoDAO;
	
	@Autowired
	@BeanInjector("motorDAO")
	private MotorDAO motorDAO;
		
	//Marcas
	@Override
	public Map<String, Object> ConsultarMarca(Integer page, Integer limit) {
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", marcaVehiculoDAO.listaMarcasVehiculosActivas(0, -1).size());
		Parametros.put("marcas", marcaVehiculoDAO.listaMarcasVehiculosActivas(page*limit, limit));
		return Parametros;
	}
	
	//Estados
	@Override
	public Map<String, Object> ConsultarEstado(Integer page, Integer limit) {
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", estadoDAO.countAll());
		Parametros.put("estados", estadoDAO.findAll());
		return Parametros;
	}

	//Ciudades
	@Override
	public Map<String, Object> ConsultarCiudad(Integer idEstado,Integer page, Integer limit) {
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ciudadDAO.listaCiudadesEstado(idEstado, 0, -1).size());
		Parametros.put("ciudades", ciudadDAO.listaCiudadesEstado(idEstado,page*limit, limit));
		return Parametros;
	}
	
	//Persona
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Persona> T acutalizarPersona(T persona){
		PersonaDAO dao = determinarPersonaDAO(persona.getClass());
		if(dao!=null){
			if(persona.getId()!=null)
				persona = (T) dao.update(persona);
			else
				persona = (T) dao.save(persona);
		}
		return persona;
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
	
	@Override
	public Cliente consultarCliente(Cliente cliente) {
		return clienteDAO.consultarPersona(cliente);
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
	
	@Override
	public Map<String, Object> ConsultarClasificacionRepuesto(Integer page, Integer limit) {
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)clasificacionRepuestoDAO.countAll()).intValue());
		Parametros.put("clasificacionRepuesto", clasificacionRepuestoDAO.findAll(page*limit, limit));
		return Parametros;
	}
	
	@Override
	public Proveedor registrarProveedor(Proveedor proveedor) {
		for(ClasificacionRepuesto clasificacion : proveedor.getClasificacionRepuestos())
			clasificacion.getProveedores().add(proveedor);
		for(MarcaVehiculo marca : proveedor.getMarcaVehiculos())
			marca.getProveedores().add(proveedor);
	   return proveedorDAO.save(proveedor);
	}
	
	
	@Override
	public Map<String, Object> ConsultarMotor(Integer page, Integer limit) {
		Map<String, Object> Parametros= new HashMap<String, Object>();
		Parametros.put("total", ((Long)motorDAO.countAll()).intValue());
		Parametros.put("motor", motorDAO.findAll(page*limit, limit));
		return Parametros;
	}
	
	@Override
	public Map<String, Object> ConsultarProveedoresListaClasificacionRepuesto(Persona persona, String fieldSort, Boolean sortDirection,
			List<Integer> idsClasificacionRepuesto, int start, int limit){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("total", proveedorDAO.consultarProveedoresListaClasificacionRepuesto(persona, fieldSort, sortDirection, idsClasificacionRepuesto, 0, -1).size());
		parametros.put("proveedores", proveedorDAO.consultarProveedoresListaClasificacionRepuesto(persona, fieldSort, sortDirection,idsClasificacionRepuesto, start*limit, limit));
		return parametros;
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private <T extends PersonaDAO, Y extends Persona> T determinarPersonaDAO(Class<Y> clase){
		T dao = null;
		if(clase != null){
			if(clase.equals(Analista.class))
				dao = (T) this.analistaDAO;
			else if(clase.equals(Proveedor.class))
				dao = (T) this.proveedorDAO;
			else if(clase.equals(Cliente.class))
				dao = (T) this.clienteDAO;
		}
		return dao;
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

	public ClasificacionRepuestoDAO getClasificacionRepuestoDAO() {
		return clasificacionRepuestoDAO;
	}

	public void setClasificacionRepuestoDAO(
			ClasificacionRepuestoDAO clasificacionRepuestoDAO) {
		this.clasificacionRepuestoDAO = clasificacionRepuestoDAO;
	}

	public MotorDAO getMotorDAO() {
		return motorDAO;
	}

	public void setMotorDAO(MotorDAO motorDAO) {
		this.motorDAO = motorDAO;
	}
}
