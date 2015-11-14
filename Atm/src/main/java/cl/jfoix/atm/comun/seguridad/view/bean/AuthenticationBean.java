package cl.jfoix.atm.comun.seguridad.view.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cl.jfoix.atm.comun.seguridad.proveedor.UsuarioAtenticacion;

@ManagedBean(name = "authenticationBean")
@SessionScoped
@Component
@Scope("session")
public class AuthenticationBean implements Serializable {
	
	private static final long serialVersionUID = -2757923903320691489L;

	private Authentication auth;

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}
	
	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	private String username;
	private String password;
	
	public AuthenticationBean() {

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	
	/**
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public String login() throws IOException, ServletException {
		String forward = null;
		try {			
			Authentication request = new UsernamePasswordAuthenticationToken(this.username, this.password);

			auth = authenticationManager.authenticate(request);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			
			String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			
			String indexUrl = "";
			
			if(viewId.equals("/login_lm.xhtml")){
				indexUrl = context.encodeActionURL(FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/pages/lm_mecanico/lmMecanico.xhtml"));
			} else {
				indexUrl = context.encodeActionURL(FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/index.xhtml"));
			}
			
			try {
				context.redirect(indexUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			return null;			
			
		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al autenticarse", "Error al autenticarse"));
			forward = "accessDenied";
		}
		return forward;
	}

	public String logout() throws IOException {
		this.username = "";
		this.password = "";
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		((HttpServletRequest)context.getRequest()).getSession().invalidate();
		
		String loginUrl = context.encodeActionURL(FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/login.xhtml"));
		try {
			context.redirect(loginUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return null;
	}
	
	
	public boolean tieneRol(String role){
		boolean resp = false;
		if(auth!=null){
			resp = ((UsuarioAtenticacion)auth).isAlgunRolHabilitado(role);
		}
		return resp;
	}
}
