package cl.jfoix.atm.comun.excepcion.dao;

import org.apache.log4j.Logger;

/**
 * Clase que representa a las excepciones ocurridas en la capa de acceso a datos</br>
 * Esta excepción es la que se debe utilizar cuando ocurre algún error en el acceso a los datos, </br>
 * luego ser enviada a la capa de servicios para que este la capture y vuelva a encapsular.</br>
 * 
 * Al generar esta excepción automaticamente genera la traza de log con el logger entregado.
 * 
 * @author Jangenni Foix
 * @author César Abarza S.
 * @version 1.0
 *
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = -5964549914192097851L;
	/**
	 * Campo que almacena el mensaje de error como String.
	 */
	public String error;
	/**
	 * Constructor que debe ser utilizado para generar una excepción de la capa de datos
	 * @param e, representa a la excepcón capturada y encapsulada en esta excepción controlada por la aplicación
	 * @param log, representa al log de la clase en donde ocurre la excepción
	 * @param metodo, representa al nombre del método en donde ocurre el error para que se genere la traza.
	 */
	public DaoException(Throwable e , Logger log, String metodo){
		super(e);
		log.error("Error Acceso Datos : " + metodo, e);
		error = e.getMessage();
	}
	/**
	 * Constructor que debe ser utilizado para generar una excepción de la capa de datos
	 * @param e, representa a la excepcón capturada y encapsulada en esta excepción controlada por la aplicación
	 * @param log, representa al log de la clase en donde ocurre la excepción
	 * @param metodo, representa al nombre del método en donde ocurre el error para que se genere la traza.
	 * @param params, representa a los parámetros del método en donde ocurre el error para que se genere la traza.
	 */
	public DaoException(Throwable e , Logger log, String metodo, String params){
		super(e);
		log.error("Error Acceso Datos : " + metodo + " : " + params, e);
		error = e.getMessage();
	}

	/**
	 * @return el mensaje de error
	 */
	public String getError() {
		return error;
	}
}
