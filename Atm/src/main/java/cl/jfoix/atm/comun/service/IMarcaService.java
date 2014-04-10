package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.Marca;

public interface IMarcaService {

	public void guardarMarca(Marca marca) throws Exception;
	
	public List<Marca> buscarTodasMarcas();

	public List<Marca> buscarMarcasPorDescripcion(String descripcionMarca);
}
