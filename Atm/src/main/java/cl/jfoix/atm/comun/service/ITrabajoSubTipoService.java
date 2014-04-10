package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;

public interface ITrabajoSubTipoService {

	public void guardarTrabajoSubTipo(TrabajoSubTipo trabajoSubTipo) throws ViewException;
	
	public List<TrabajoSubTipo> buscarTodosTrabajosSubTipo();

	public List<TrabajoSubTipo> buscarTrabajoSubTipoPorDescripcionIdTrabajoTipo(String descripcion, Integer idTrabajoTipo);

	public List<TrabajoSubTipo> buscarTrabajoSubTipoPorIdTrabajoTipo(Integer idTrabajoTipo);

	public TrabajoSubTipo buscarTrabajoSubTipo(Integer idTrabajoSubTipo);
}
