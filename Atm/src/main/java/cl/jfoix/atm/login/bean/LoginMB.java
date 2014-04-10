package cl.jfoix.atm.login.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.login.service.ILoginService;

@ManagedBean
@ViewScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 6534122083935719792L;
	
	private String userName;
	private String password;

	@ManagedProperty(value="#{loginService}")
	private ILoginService loginService;
	
	public String login(){
		
		String returnNav = null;
		
		Usuario user = loginService.login(this.userName, this.password);
		
		if(user != null){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userMB", user);
			returnNav = "home";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje Ingreso", "El usuario y clave son incorrectos"));
		}
		
		return returnNav;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginService
	 */
	public ILoginService getLoginService() {
		return loginService;
	}

	/**
	 * @param loginService the loginService to set
	 */
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}
}
