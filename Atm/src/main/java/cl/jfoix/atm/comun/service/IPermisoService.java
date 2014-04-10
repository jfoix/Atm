package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.login.entity.Permiso;

public interface IPermisoService {

	public List<Permiso> obtenerPermisosPorIdPerfil(Integer idPerfil);
}
