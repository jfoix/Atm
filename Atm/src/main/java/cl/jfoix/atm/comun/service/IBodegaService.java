package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.ot.entity.Bodega;

public interface IBodegaService {

	public void guardarBodega(Bodega bodega) throws Exception;
	
	public List<Bodega> buscarTodasBodegas();

	public List<Bodega> buscarBodegasPorDescripcion(String descripcionBodega);
}
