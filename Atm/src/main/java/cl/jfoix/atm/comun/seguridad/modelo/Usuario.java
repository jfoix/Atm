/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.jfoix.atm.comun.seguridad.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Clase abstracta base para el usuario en el modelo de seguridad
 * 
 * @author César Abarza Suazo
 */
public abstract class Usuario implements Serializable {

	private static final long serialVersionUID = 3187200558224900530L;

	/**
	 * Método que debe implementar la clase que herede de esta y que retorna la lista de roles.
	 * @return lista de roles
	 */
    public abstract List<Rol> getRoles();    
    /**
     * Método que debe implementar la clase que herede de esta y que retorne el nombre de usuario según corresponda
     * @return String que representa el nombre del usuario único
     */
    public abstract String getNombreUsuarioSession();
    /**
     * Método que debe implementar la clase que herede de esta y que retorne la clave del usuario según corresponda
     * @return String que representa a la clave del usuario
     */
    public abstract String getClaveSession();
    
    
}
