package cl.jfoix.atm.comun.excepcion.view;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase que representa a las excepciones ocurridas en la capa de servicios</br>
 * Esta excepción es la que se debe utilizar cuando ocurre algún error en la capa de servicios, </br>
 * 
 * Al generar esta excepción automaticamente genera la traza de log con el logger entregado.</br>
 * 
 * Si se desea agregar un mensaje específico para una propiedad estos quedarán en la misma posición de las listas.
 * 
 * @author Jangenni Foix
 * @author César Abarza S.
 * @version 1.0
 *
 */
public class ViewException extends Exception {
	
	private static final long serialVersionUID = 1399461353889814945L;
	
	/**
	 * Representa a la lista de mensajes
	 */
	private List<String> mensajes;
	/**
	 * Representa a la lista de propiedades
	 */
	private List<String> propiedades;
	/**
	 * Constructor vacio de la excepción
	 */
	public ViewException(){
		super();
		this.mensajes = new ArrayList<String>();
		this.propiedades= new ArrayList<String>();
	}
	/**
	 * Constructor de la excepción que recibe como parámetro un mensaje de error
	 * @param mensaje representa un mensaje de error
	 */
	public ViewException(String mensaje){
		super(mensaje);
		this.propiedades= new ArrayList<String>();
		this.mensajes = new ArrayList<String>();
		this.mensajes.add(mensaje);
	}
	/**
	 * Constructor de la excepción que recibe como parámetro un mensaje de error 
	 * y la propiedad a la que corresponde
	 * @param mensaje representa un mensaje de error
	 * @param propiedad que representa a la propiedad a la que se asociará el error
	 */
	public ViewException(String mensaje, String propiedad){
		super(mensaje);
		this.propiedades= new ArrayList<String>();
		this.mensajes = new ArrayList<String>();
		this.mensajes.add(mensaje);
		this.propiedades.add(propiedad);
	}
	/**
	 * Constructor de la excepción que recibe como parámetro una lista de mensajes de error
	 * @param mensajes representa la una lista de mensajes de error
	 */
	public ViewException(List<String> mensajes){
		this.mensajes = mensajes;
		this.propiedades= new ArrayList<String>();
	}
	/**
	 * Constructor de la excepción que recibe como parámetro una lista de mensajes de error
	 * y una lista de propiedades a las que se relacionan esos mensajes
	 * @param mensajes representa a una lista de mensajes de error
	 * @param propiedades representa a una lista de propiedades a las que se asociaran los mensajes
	 */
	public ViewException(List<String> mensajes, List<String> propiedades){
		this.mensajes = mensajes;
		this.propiedades= propiedades;
	}
	/**
	 * Método que permite agregar un mensaje a la lista de mensajes de error
	 * @param mensaje representa un mensaje de error
	 */
	public void agregarMensaje(String mensaje){
		this.mensajes.add(mensaje);
	}
	/**
	 * Método que permite agregar una propiedad a la lista de propiedades
	 * @param propiedad que representa a la propiedad a la que se asociará el error
	 */
	public void agregarPropiedad(String propiedad){
		this.propiedades.add(propiedad);
	}
	/**
	 * Método que permite agregar un mensaje y la propiedad a la que esta relacionado
	 * @param mensaje representa un mensaje de error
	 * @param propiedad que representa a la propiedad a la que se asociará el error
	 */
	public void agregarMensajeYPropiedad(String mensaje, String propiedad){
		this.agregarMensaje(mensaje);
		this.agregarPropiedad(propiedad);
	}

	/**
	 * @return la lista de mensajes
	 */
	public List<String> getMensajes() {
		return mensajes;
	}

	/**
	 * Setea la lista de mensajes
	 * @param lista de mensajes
	 */
	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}
	
	/**
	 * @return la lista de propiedades
	 */
	public List<String> getPropiedades() {
		return propiedades;
	}
	/**
	 * Setea la lista de propiedades
	 * @param lista de propiedades
	 */
	public void setPropiedades(List<String> propiedades) {
		this.propiedades = propiedades;
	}
	/**
	 * Método que indica si la excepción tiene mensajes o no.
	 * @return verdadero en caso de que la excepción tenga mensajes
	 */
	public boolean tieneMensajes(){
		return !mensajes.isEmpty();
	}
}
