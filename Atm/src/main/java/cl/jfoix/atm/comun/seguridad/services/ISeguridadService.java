package cl.jfoix.atm.comun.seguridad.services;

import cl.jfoix.atm.comun.seguridad.modelo.Usuario;

/**
 * Interfaz que debe implementar el servicio encargado de la administración de usuarios</br>
 * de tal forma que el modelo comun de seguridad funcione.
 * 
 * @author César Abarza S.
 * @version 1.0
 */
public interface ISeguridadService {
	
	/**
	 * Método encargado de retornar un usuario
	 * 
	 * @param usuario nombre del usuario
	 * @param clave del usuario
	 * @return
	 */
	public Usuario obtenerUsuario(String usuario, String clave);

}
