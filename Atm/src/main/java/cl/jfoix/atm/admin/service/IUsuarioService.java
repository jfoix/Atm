package cl.jfoix.atm.admin.service;

import java.util.List;

import cl.jfoix.atm.admin.util.EstadoUsuarioEnum;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.login.entity.Usuario;

public interface IUsuarioService {

	public void guardarUsuario(Usuario usuario) throws ViewException;

	public List<Usuario> buscarTodosUsuarios();

	public Usuario buscarUsuario(String nombreUsuario);

	public Usuario buscarUsuarioActivo(String nombreUsuario);

	public List<Usuario> buscarUsuarios(String nombreUsuario, EstadoUsuarioEnum...states);

	public List<Usuario> buscarUsuariosMecanicos();
}
