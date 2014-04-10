package cl.jfoix.atm.comun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.jfoix.atm.comun.dao.IPerfilDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.service.IPerfilService;
import cl.jfoix.atm.login.entity.Perfil;

@Service("perfilService")
public class PerfilServiceImpl implements IPerfilService {

//	private static Logger log = Logger.getLogger(PerfilServiceImpl.class);

	@Autowired
	private IPerfilDao perfilDao;

	@Override
	public List<Perfil> buscarTodosPerfiles() {
		try {
			return perfilDao.buscarTodos();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Perfil> buscarPerfilesPorNombreUsuario(String nombreUsuario) {
		// TODO Auto-generated method stub
		return null;
	}
}
