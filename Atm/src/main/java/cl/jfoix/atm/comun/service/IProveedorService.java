package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.ot.entity.Proveedor;

public interface IProveedorService {

	public void guardarProveedor(Proveedor proveedor) throws Exception;
	
	public List<Proveedor> buscarTodosProveedores();

	public List<Proveedor> buscarProveedoresPorDescripcion(String descripcionProveedor);
}
