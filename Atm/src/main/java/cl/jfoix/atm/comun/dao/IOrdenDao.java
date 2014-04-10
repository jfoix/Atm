package cl.jfoix.atm.comun.dao;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.IComunDao;
import cl.jfoix.atm.ot.entity.Orden;

public interface IOrdenDao extends IComunDao<Orden, Integer> { 
	
	public List<Orden> buscarOrdenAdmin(Integer idOrden, String idMecanico, Date fechaInicio, Date fechaTermino, Integer idEstado, String patente) throws DaoException;
}
