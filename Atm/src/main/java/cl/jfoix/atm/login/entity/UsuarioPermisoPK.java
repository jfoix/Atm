package cl.jfoix.atm.login.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioPermisoPK implements Serializable {

	private static final long serialVersionUID = -957131223940485902L;

	@Column(name="nombreUsuario")
	private String nombreUsuario;
	
	@Column(name="idPermiso")
	private Integer idPermiso;

	public boolean equals(Object o) {
	    return ((o instanceof UsuarioPermisoPK) && nombreUsuario.equals(((UsuarioPermisoPK) o).getNombreUsuario()) && idPermiso.equals(((UsuarioPermisoPK) o).getIdPermiso()));
	}

	public int hashCode() {
		return nombreUsuario.hashCode() + idPermiso.hashCode();
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the idPermiso
	 */
	public Integer getIdPermiso() {
		return idPermiso;
	}

	/**
	 * @param idPermiso the idPermiso to set
	 */
	public void setIdPermiso(Integer idPermiso) {
		this.idPermiso = idPermiso;
	}
}
