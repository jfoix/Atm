package cl.jfoix.atm.comun.mensaje;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
/**
 * Clase utilitaria para generar mensajes para el contexto web
 * para así enviar información al usuario.
 * 
 * Todo mensaje que requiera i18n se podrá generar con esta clase
 * 
 * @author César Abarza S.
 * @version 1.0
 */
public class ComunMensajeHandler {
	private MessageSource source = null;

	private static ComunMensajeHandler instance;
	/**
	 * Método que obtiene el resource bundle correspondiente.
	 */
	private void cargaMessageSource(){
        WebApplicationContext webAppCtx = (WebApplicationContext) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if(webAppCtx.getBean("messageSource") instanceof ResourceBundleMessageSource){
        	source = (ResourceBundleMessageSource) webAppCtx.getBean("messageSource");
        }else if(webAppCtx.getBean("messageSource") instanceof DelegatingMessageSource){
        	source = ((DelegatingMessageSource) webAppCtx.getBean("messageSource")).getParentMessageSource();
        }
	}
	
	/**
	 * Constructor de la instancia, privado para generar un elemento con patrón singleton
	 */
	private ComunMensajeHandler(){
		cargaMessageSource();
	}
	
	/**
	 * Obtiene la instancia única de la clase.
	 * @return ComunMensajeHandler
	 */
	public static ComunMensajeHandler getInstance(){
		if(instance==null){
			instance = new ComunMensajeHandler();
		}
		return instance;
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes.
	 * Si el mensaje es de tipo Info, no se actualizará el parametro de callbak messageOccured, por lo que no permitirá esta validación 
	 * @param mensaje representa al mensaje en i18n o directo
	 * @param parametros Un arreglo de objetos para el mensaje
	 * @param severity representa a la severidad del mensaje
	 * 
	 */
	private void generarMensaje(String propiedad, String mensaje, Object[] parametros, Severity severity){
		FacesContext fc = FacesContext.getCurrentInstance();
		RequestContext context = RequestContext.getCurrentInstance();
		try{
			fc.addMessage(propiedad, new FacesMessage(severity, source.getMessage(mensaje, parametros, fc.getViewRoot().getLocale()), source.getMessage(mensaje, parametros, fc.getViewRoot().getLocale())));
		} catch(NoSuchMessageException e){
			fc.addMessage(propiedad, new FacesMessage(severity, "Mensaje", mensaje));
		}
		context.update("mensajes");
		if(propiedad!=null){
			context.update(propiedad);
		}
		if(!severity.equals(FacesMessage.SEVERITY_INFO)){
			RequestContext.getCurrentInstance().addCallbackParam("messageOccured", true);
		}
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes informativos
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeInformativo(String mensaje){
		this.generarMensajeInformativo(null, mensaje);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes informativos
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeInformativo(String propiedad, String mensaje){
		this.generarMensajeInformativo(propiedad, mensaje, new Object[0]);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes informativos
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 * @param parametros Un arreglo de objetos para el mensaje
	 */
	public void generarMensajeInformativo(String propiedad, String mensaje, Object[] parametros){
		this.generarMensaje(propiedad, mensaje, parametros, FacesMessage.SEVERITY_INFO);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes de alerta
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeAlerta(String mensaje){
		this.generarMensajeAlerta(null, mensaje);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes de alerta
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeAlerta(String propiedad, String mensaje){
		this.generarMensajeAlerta(propiedad, mensaje, new Object[0]);
	}
	/**
	 * Método private que sirve para la generación de los mensajes de alerta
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 * @param parametros Un arreglo de objetos para el mensaje
	 */
	public void generarMensajeAlerta(String propiedad, String mensaje, Object[] parametros){
		this.generarMensaje(propiedad, mensaje, parametros, FacesMessage.SEVERITY_WARN);
	}
	/**
	 * Método private que sirve para la generación de los mensajes de error
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeError(String mensaje){
		this.generarMensajeError(null, mensaje);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes de error
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeError(String propiedad, String mensaje){
		this.generarMensajeError(propiedad, mensaje, new Object[0]);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes de error
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 * @param parametros Un arreglo de objetos para el mensaje
	 */
	public void generarMensajeError(String propiedad, String mensaje, Object[] parametros){
		this.generarMensaje(propiedad, mensaje, parametros, FacesMessage.SEVERITY_ERROR);
	}
	/**
	 * Método private que sirve para la generación de los mensajes de errores graves
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeFatal(String mensaje){
		this.generarMensajeFatal(null, mensaje);
	}
	
	/**
	 * Método private que sirve para la generación de los mensajes de errores graves
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 */
	public void generarMensajeFatal(String propiedad, String mensaje){
		this.generarMensajeFatal(propiedad, mensaje, new Object[0]);
	}
	/**
	 * Método private que sirve para la generación de los mensajes de errores graves
	 * @param propiedad representa a la propiedad que hace referencia el mensaje
	 * @param mensaje representa al mensaje en i18n o directo
	 * @param parametros Un arreglo de objetos para el mensaje
	 */
	public void generarMensajeFatal(String propiedad, String mensaje, Object[] parametros){
		this.generarMensaje(propiedad, mensaje, parametros, FacesMessage.SEVERITY_FATAL);
	}
	
	/**
	 * Método que devuelve el recurso de los mensajes
	 * @return
	 */
	public MessageSource getMessageSource() {
		return source;
	}
	
	/**
	 * Método que devuelve un String internacionalizado con los parámetros entregados
	 * @param mensaje clave a internacionalizar
	 * @param parametros arreglo de parámetros
	 * @return
	 */
	public String obtenerMensajeInternacionalizacion(String mensaje, Object[] parametros){
		return source.getMessage(mensaje, parametros, FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}
	/**
	 * Método que devuelve un String internacionalizado
	 * @param mensaje clave a internacionalizar
	 * @return
	 */
	public String obtenerMensajeInternacionalizacion(String mensaje){
		return this.obtenerMensajeInternacionalizacion(mensaje, new Object[0]);
	}
}
