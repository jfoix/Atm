package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrdenTrabajoSolicitudProductoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4352234098831696736L;

	@Column(name="idOrdenTrabajoSolicitud")
	private Integer idOrdenTrabajoSolicitud;
	
	@Column(name="idProducto")
	private Integer idProducto;

	/**
	 * @return the idOrdenTrabajoSolicitudProducto
	 */
	public Integer getIdOrdenTrabajoSolicitud() {
		return idOrdenTrabajoSolicitud;
	}

	/**
	 * @param idOrdenTrabajoSolicitudProducto the idOrdenTrabajoSolicitudProducto to set
	 */
	public void setIdOrdenTrabajoSolicitud(
			Integer idOrdenTrabajoSolicitud) {
		this.idOrdenTrabajoSolicitud = idOrdenTrabajoSolicitud;
	}

	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
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
				+ ((idOrdenTrabajoSolicitud == null) ? 0
						: idOrdenTrabajoSolicitud.hashCode());
		result = prime * result
				+ ((idProducto == null) ? 0 : idProducto.hashCode());
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
		OrdenTrabajoSolicitudProductoPK other = (OrdenTrabajoSolicitudProductoPK) obj;
		if (idOrdenTrabajoSolicitud == null) {
			if (other.getIdOrdenTrabajoSolicitud() != null)
				return false;
		} else if (!idOrdenTrabajoSolicitud
				.equals(other.getIdOrdenTrabajoSolicitud()))
			return false;
		if (idProducto == null) {
			if (other.getIdProducto() != null)
				return false;
		} else if (!idProducto.equals(other.getIdProducto()))
			return false;
		return true;
	}
}
