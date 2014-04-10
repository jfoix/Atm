package cl.jfoix.atm.comun.seguridad.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.admin.dao.IUsuarioDao;
import cl.jfoix.atm.admin.util.EstadoUsuarioEnum;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.seguridad.modelo.Usuario;
import cl.jfoix.atm.comun.seguridad.services.ISeguridadService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("seguridadService")
public class SeguridadServiceImpl implements ISeguridadService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Transactional
	@Override
	public Usuario obtenerUsuario(String usuario, String clave) {
		
		try {
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, EstadoUsuarioEnum.ACTIVO));
			filtros.add(new Filtro("nombreUsuario", TipoOperacionFiltroEnum.EQUAL, usuario));
			filtros.add(new Filtro("clave", TipoOperacionFiltroEnum.EQUAL, clave));

			List<?> usuarios = usuarioDao.buscarPorFiltros(filtros, null);
			
			if(usuarios != null && usuarios.size() > 0){
				cl.jfoix.atm.login.entity.Usuario usu = (cl.jfoix.atm.login.entity.Usuario)usuarios.get(0);
				usu.getUsuarioPermisos().size();
				return usu;
			}
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
