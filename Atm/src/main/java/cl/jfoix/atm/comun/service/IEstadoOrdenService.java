package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.ot.entity.EstadoOrden;

public interface IEstadoOrdenService {

	public List<EstadoOrden> buscarTodosEstadosOrden();
}
