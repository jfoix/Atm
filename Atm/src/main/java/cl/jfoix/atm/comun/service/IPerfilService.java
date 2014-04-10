package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.login.entity.Perfil;

public interface IPerfilService {

	public List<Perfil> buscarTodosPerfiles();

	public List<Perfil> buscarPerfilesPorNombreUsuario(String nombreUsuario);
}
