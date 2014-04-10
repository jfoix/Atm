package cl.jfoix.atm.ot.dao;

import java.util.List;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.IComunDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;

public interface IOrdenTrabajoProductoDao extends IComunDao<OrdenTrabajoProducto, Integer> {

	List<OrdenTrabajoProducto> buscarOrdenTrabajosPorProductoEnTrabajo(Integer idProducto) throws DaoException;

	List<OrdenTrabajoProducto> buscarOrdenTrabajosPorProductoIdOrdenEnTrabajo(Integer idOrden, Integer idProducto) throws DaoException;
	
}