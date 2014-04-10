package cl.jfoix.atm.comun.excepcion.handler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
/**
 * Clase factory encargada de retornar el handler correspondiente a la configuración utilizada.
 * 
 * @author Jangenni Foix
 * @author César Abarza S.
 * @version 1.0
 * @see ComunExceptionHandler
 */
public class ComunExceptionHandlerFactory extends ExceptionHandlerFactory {

	/**
	 * representa al factory del framework
	 */
	private ExceptionHandlerFactory parent;
	/**
	 * Constructor de la clase
	 * @param parent, factory del framework
	 */
	public ComunExceptionHandlerFactory(ExceptionHandlerFactory parent){
		this.parent = parent;
	}
	
	/**
	 * Método sobreescrito que se encarga de retornar el administrador de excepciones configurado.
	 * @return retorna <code>ComunExceptionHandler</code>
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = new ComunExceptionHandler(parent.getExceptionHandler());
        return handler;
	}
}
