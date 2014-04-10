package cl.jfoix.atm.comun.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MantencionProgramadaTrabajoPK implements Serializable {

	private static final long serialVersionUID = -2776716534439110309L;

	@Column(name="idMantencionProgramada")
	private Integer idMantencionProgramada;
	
	@Column(name="idTrabajo")
	private Integer idTrabajo;

	/**
	 * @return the idMantencionProgramada
	 */
	public Integer getIdMantencionProgramada() {
		return idMantencionProgramada;
	}

	/**
	 * @param idMantencionProgramada the idMantencionProgramada to set
	 */
	public void setIdMantencionProgramada(Integer idMantencionProgramada) {
		this.idMantencionProgramada = idMantencionProgramada;
	}

	/**
	 * @return the idTrabajo
	 */
	public Integer getIdTrabajo() {
		return idTrabajo;
	}

	/**
	 * @param idTrabajo the idTrabajo to set
	 */
	public void setIdTrabajo(Integer idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idMantencionProgramada == null) ? 0
						: idMantencionProgramada.hashCode());
		result = prime * result
				+ ((idTrabajo == null) ? 0 : idTrabajo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MantencionProgramadaTrabajoPK other = (MantencionProgramadaTrabajoPK) obj;
		if (idMantencionProgramada == null) {
			if (other.idMantencionProgramada != null)
				return false;
		} else if (!idMantencionProgramada.equals(other.idMantencionProgramada))
			return false;
		if (idTrabajo == null) {
			if (other.idTrabajo != null)
				return false;
		} else if (!idTrabajo.equals(other.idTrabajo))
			return false;
		return true;
	}
}
