package cl.jfoix.atm.comun.lifecycle;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cl.jfoix.atm.comun.seguridad.view.bean.AuthenticationBean;

//import cl.siseek.comun.seguridad.view.bean.AuthenticationBean;
/**
 * Clase encargada de controlar el acceso a la aplicación sea solo con sesión activa
 *  
 * @author Jangenni Foix
 * @author César Abarza S.
 * @version 1.0
 *
 */
public class SessionPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 3878265327812578699L;
	private static Logger log = Logger.getLogger(SessionPhaseListener.class);
	/**
	 * Método invocado después de la fase
	 */
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String viewid = context.getViewRoot() == null ? "" : context.getViewRoot().getViewId();
		log.debug("[afterPhase] viewId= " + viewid);
		if(!loggedIn(context) && (!viewid.equals("/login.xhtml") && !viewid.equals("/login.jsf") && (!viewid.equals("/login_lm.xhtml") && !viewid.equals("/login_lm.jsf")))){
			String loginUrl = context.getExternalContext().encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/login.xhtml"));
			try {
				context.getExternalContext().redirect(loginUrl);
			} catch (IOException e) {
				log.error("[afterPhase] Error al redireccionar a la URL de login", e);
			}
		} else if(loggedIn(context) && (viewid.equals("/login.xhtml") || viewid.equals("/login.jsf"))){			
			String indexUrl = context.getExternalContext().encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/index.xhtml"));
			try {
				context.getExternalContext().redirect(indexUrl);
			} catch (IOException e) {
				log.error("[afterPhase] Error al redireccionar a la URL de inicio", e);
			}
		}
	}
	
	/**
	 * Método que valida si se encuentra logueado con una sesión válida
	 * 
	 * @param cont representa al contexto
	 * @return retorna si la sesión se encuentra activa y es válida
	 */
	private boolean loggedIn(FacesContext cont) {
		AuthenticationBean authenticationBean = (AuthenticationBean)((HttpServletRequest)(cont.getExternalContext().getRequest())).getSession().getAttribute("authenticationBean");
		if(authenticationBean != null && authenticationBean.getAuth() != null){
			return true;
		}
		return false;
	}

	/**
	 * Método invocado antes de la fase
	 */
	public void beforePhase(PhaseEvent arg0) {
	}

	/**
	 * Método que devuelve el id de la fase
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}


