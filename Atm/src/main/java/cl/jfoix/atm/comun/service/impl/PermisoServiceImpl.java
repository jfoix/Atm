package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.jfoix.atm.comun.dao.IPermisoDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.service.IPermisoService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.login.entity.Permiso;

@Service("permisoService")
public class PermisoServiceImpl implements IPermisoService {

//	private static Logger log = Logger.getLogger(PerfilServiceImpl.class);

	@Autowired
	private IPermisoDao permisoDao;

	@Override
	public List<Permiso> obtenerPermisosPorIdPerfil(Integer idPerfil) {
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			filtros.add(new Filtro("perfil.idPerfil", TipoOperacionFiltroEnum.EQUAL, idPerfil));

			return permisoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
