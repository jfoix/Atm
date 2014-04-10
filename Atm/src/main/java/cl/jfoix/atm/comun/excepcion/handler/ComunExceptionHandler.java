package cl.jfoix.atm.comun.excepcion.handler;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;

import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.mensaje.ComunMensajeHandler;

/**
 * Clase Handler encargada de manejar las excepciones de tipo <code>ViewException</code></br>
 * Esta clase intercepta todas las excepciones de tipo <code>ViewException</code> para luego transformala</br>
 * en un mensaje entendible para JSF.</br>
 * Si el mensaje esta internacionalizado, entonces se realiza la conversión según el Locale correspondiente.
 * 
 * @author Jangenni Foix
 * @author César Abarza S.
 * @version 1.1
 *
 * @see ViewException
 * @see ExceptionHandlerWrapper
 * 
 */
public class ComunExceptionHandler extends ExceptionHandlerWrapper {
	/**
	 * Representa al exception handler del framework 
	 */
	private ExceptionHandler wrapped;
	private static final Logger log = Logger.getLogger(ComunExceptionHandler.class);
	/**
	 * Método que retorna el resource bundle correspondiente.
	 * @return ResourceBundleMessageSource, representa al resourceBundle de Spring, que maneja los mensajes configurados con i18n
	 * @deprecated Se eliminará en la siguiente versión  ya que se ha creado una clase para la administraciín de los mensajes
	 * @see ComunMensajeHandler
	 */
	public static ResourceBundleMessageSource getMessageSource(){
        WebApplicationContext webAppCtx = (WebApplicationContext) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ResourceBundleMessageSource messages = (ResourceBundleMessageSource) webAppCtx.getBean("messageSource");
        return messages;
	}
	
	/**
	 * Constructor de la clase
	 * @param exception
	 */
	public ComunExceptionHandler(ExceptionHandler exception){
		wrapped = exception;
	}
	
	/**
	 * retorna el exceptionHandler encapsulado.
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}
	/**
	 * Metodo sobreescrito que se encarga de administrar las excepciones.</br>
	 * Es en este método en donde se discrimina si es un <code>ViewException</code> u otro tipo de excepción.
	 */
	@Override
    public void handle() throws FacesException {

        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
        	ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable excepcion = context.getException();
            int viewIntents = 3;
            int index = 0;
            
            while(excepcion != null && !(excepcion instanceof ViewException) && index <= viewIntents){
            	excepcion = excepcion.getCause();
            	index++;
            }
            if(excepcion instanceof ViewException){
            	try {

            		ViewException excepcionView = (ViewException) excepcion;
            		for(String mensaje : excepcionView.getMensajes()){
            			ComunMensajeHandler.getInstance().generarMensajeError(mensaje);
            		}
            		for(String propiedad: excepcionView.getPropiedades()){
            			ComunMensajeHandler.getInstance().generarMensajeError(propiedad, "*");
            		}
            	}catch (Exception e) {
            		log.error("[handle] Error al controlar la excepción.", e);
	            } finally {
	                i.remove();
	            }
            }
        }
        getWrapped().handle();
    }
}
