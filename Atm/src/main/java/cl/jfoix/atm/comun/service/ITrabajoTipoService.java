package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;

public interface ITrabajoTipoService {

	public void guardarTrabajoTipo(TrabajoTipo trabajoTipo) throws ViewException;
	
	public List<TrabajoTipo> buscarTodosTrabajosTipo();

	public List<TrabajoTipo> buscarTrabajoTipoPorDescripcion(String descripcion);
}
