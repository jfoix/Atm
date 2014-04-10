package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrdenTrabajoUsuarioPK implements Serializable {

	private static final long serialVersionUID = 3452191222613712616L;

	@Column(name="nombreUsuario")
	private String nombreUsuario;
	
	@Column(name="idOrdenTrabajo")
	private Integer idOrdenTrabajo;

	public boolean equals(Object o) {
	    return ((o instanceof OrdenTrabajoUsuarioPK) && nombreUsuario.equals(((OrdenTrabajoUsuarioPK) o).getNombreUsuario()) && idOrdenTrabajo.equals(((OrdenTrabajoUsuarioPK) o).getIdOrdenTrabajo()));
	}

	public int hashCode() {
		return nombreUsuario.hashCode() + idOrdenTrabajo.hashCode();
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
	 * @return the idOrdenTrabajo
	 */
	public Integer getIdOrdenTrabajo() {
		return idOrdenTrabajo;
	}

	/**
	 * @param idOrdenTrabajo the idOrdenTrabajo to set
	 */
	public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
		this.idOrdenTrabajo = idOrdenTrabajo;
	}
}
