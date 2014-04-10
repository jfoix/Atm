package cl.jfoix.atm.ot.service;

import cl.jfoix.atm.ot.entity.Cliente;


public interface IClienteService {

	public Cliente buscarClientePorRut(String rut) throws Exception;
	
	public void guardarCliente(Cliente cliente) throws Exception;
}
