/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.jfoix.atm.comun.seguridad.modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Clase abstracta base para los roles en el modelo de seguridad
 * 
 * @author César Abarza S.
 */
public abstract class Rol implements Serializable, Comparable<Rol> {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8698667235994273130L;
	/**
	 * propiedad que representa al código único del rol
	 */
    private String codigo;

    /**
     * Constructor vacio de la clase
     */
    public Rol() {
    }

    /**
     * Constructor con parámetro
     * @param codigo del rol
     */
    public Rol(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Método para obtener el codigo del rol
     * @return el código único del rol
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para setear el código único del rol
     * @param codigo único del rol
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Método de comparación por el código</br>
     * Sirve para ordenar y buscar con las clases utilitarias de las colecciones
     * @see Collections
     * @see Arrays
     */
    @Override
    public int compareTo(Rol o) {
        return this.getCodigo().compareTo(o.getCodigo());
    }

    /**
     * Método abstracto que dese ser implementado por la clase que herede de esta y que debe
     * retornar una lista de usuario que contengan este rol
     * @return lista de usuarios
     */
    public abstract List<Usuario> getUsuarios();
}
