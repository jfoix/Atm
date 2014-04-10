package cl.jfoix.atm.login.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cl.jfoix.atm.login.entity.Usuario;

@SessionScoped
@ManagedBean(name="userSessionMB")
public class UserMB implements Serializable {

	private static final long serialVersionUID = 3490019892698135216L;

	private Usuario usuario;

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
