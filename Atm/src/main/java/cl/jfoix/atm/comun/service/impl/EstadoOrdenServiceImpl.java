package cl.jfoix.atm.comun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.jfoix.atm.comun.dao.IEstadoOrdenDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.service.IEstadoOrdenService;
import cl.jfoix.atm.ot.entity.EstadoOrden;

@Service("estadoOrdenService")
public class EstadoOrdenServiceImpl implements IEstadoOrdenService {

	@Autowired
	private IEstadoOrdenDao estadoOrdenDao;

	@Override
	public List<EstadoOrden> buscarTodosEstadosOrden() {
		
		try {
			return estadoOrdenDao.buscarTodos();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	} 
}
