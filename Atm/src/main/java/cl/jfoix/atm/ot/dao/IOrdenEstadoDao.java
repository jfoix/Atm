package cl.jfoix.atm.ot.dao;

import cl.jfoix.atm.dbutil.dao.IComunDao;
import cl.jfoix.atm.ot.entity.OrdenEstado;

public interface IOrdenEstadoDao extends IComunDao<OrdenEstado, Integer> {

	OrdenEstado buscarUltimoEstadoOrden(Integer idOrden);
	
}