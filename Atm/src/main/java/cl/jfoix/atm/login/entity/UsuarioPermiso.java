package cl.jfoix.atm.login.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario_permiso")
public class UsuarioPermiso implements Serializable {

	private static final long serialVersionUID = 229086932476709987L;

	@EmbeddedId
	private UsuarioPermisoPK pk;
	
	@ManyToOne
	@JoinColumn(name="nombreUsuario", insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="idPermiso", insertable=false, updatable=false)
	private Permiso permiso;

	/**
	 * @return the pk
	 */
	public UsuarioPermisoPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(UsuarioPermisoPK pk) {
		this.pk = pk;
	}

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

	/**
	 * @return the permiso
	 */
	public Permiso getPermiso() {
		return permiso;
	}

	/**
	 * @param permiso the permiso to set
	 */
	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

}
