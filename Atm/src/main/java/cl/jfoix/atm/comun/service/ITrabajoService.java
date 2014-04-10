package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.excepcion.view.ViewException;

public interface ITrabajoService {

	void guardarTrabajo(Trabajo trabajo) throws ViewException;
	
	List<Trabajo> buscarTrabajoPorDescripcionCodigo(String descripcion, String codigo, Integer idTrabajoTipo, Integer idTrabajoSubTipo);

	List<TrabajoProducto> buscarProductosPorIdTrabajo(Integer idTrabajo);

	Trabajo buscarTrabajoPorId(Integer idTrabajoTipo);
}
