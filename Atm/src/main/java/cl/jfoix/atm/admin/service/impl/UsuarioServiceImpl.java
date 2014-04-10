package cl.jfoix.atm.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.admin.dao.IUsuarioDao;
import cl.jfoix.atm.admin.service.IUsuarioService;
import cl.jfoix.atm.admin.util.EstadoUsuarioEnum;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.login.entity.Usuario;

@Service("usuarioService")
public class UsuarioServiceImpl implements IUsuarioService {

	private static Logger log = Logger.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private IUsuarioDao usuarioDao;

	@Transactional
	@Override
	public void guardarUsuario(Usuario usuario) throws ViewException {
		try{
			if(usuario.getNombreUsuario() == null){
				usuarioDao.guardar(usuario);
			} else {
				usuarioDao.modificar(usuario);
			}
		}catch(DaoException e){
			log.error("Erro al guardar el usuario", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Transactional
	@Override
	public List<Usuario> buscarTodosUsuarios() {
		try {
			List<Usuario> usuarios = usuarioDao.buscarTodos();
			
			if(usuarios != null && usuarios.size() > 0){
				for(Usuario usuario : usuarios){
					usuario.getUsuarioPermisos().size();
					usuario.setClaveRep(usuario.getClave());
				}
			}
			
			return usuarios;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Usuario buscarUsuario(String nombreUsuario) {
		
		try {
			return usuarioDao.buscarPorId(nombreUsuario);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Usuario buscarUsuarioActivo(String nombreUsuario) {
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			filtros.add(new Filtro("nombreUsuario", TipoOperacionFiltroEnum.LIKE_COMPLETO, nombreUsuario));
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, EstadoUsuarioEnum.ACTIVO));

			List<Usuario> usuarios = usuarioDao.buscarPorFiltros(filtros, null);
			
			if(usuarios != null && usuarios.size() > 0){
				return usuarios.get(0);
			}
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Usuario> buscarUsuariosMecanicos() {
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, EstadoUsuarioEnum.ACTIVO));
			filtros.add(new Filtro("up.permiso.perfil.idPerfil", TipoOperacionFiltroEnum.EQUAL, 3));

			return usuarioDao.buscarPorFiltros(filtros, null, "JOIN c.usuarioPermisos up");
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Transactional
	@Override
	public List<Usuario> buscarUsuarios(String nombreUsuario, EstadoUsuarioEnum... states) {
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!nombreUsuario.equals("")){
				filtros.add(new Filtro("nombreUsuario", TipoOperacionFiltroEnum.LIKE_COMPLETO, nombreUsuario));
			}
			
			if(states != null && states.length > 0){
				for(EstadoUsuarioEnum estado : states){
					filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, estado));
				}
			}

			List<Usuario> usuarios = usuarioDao.buscarPorFiltros(filtros, null);
			
			if(usuarios != null && usuarios.size() > 0){
				for(Usuario usuario : usuarios){
					usuario.getUsuarioPermisos().size();
					usuario.setClaveRep(usuario.getClave());
				}
			}
			
			return usuarios;
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
