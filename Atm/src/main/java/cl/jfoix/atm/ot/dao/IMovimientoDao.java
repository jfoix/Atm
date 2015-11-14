package cl.jfoix.atm.ot.dao;

import java.util.List;

import cl.jfoix.atm.dbutil.dao.IComunDao;
import cl.jfoix.atm.ot.entity.Movimiento;

public interface IMovimientoDao extends IComunDao<Movimiento, Integer> {

	List<Movimiento> buscarMovimientos(Integer idStock, Integer limit); }