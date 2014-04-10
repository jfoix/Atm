package cl.jfoix.atm.comun.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrabajoProductoPK implements Serializable {

	private static final long serialVersionUID = 6569093912063545446L;

	@Column(name="idTrabajo")
	private Integer idTrabajo;
	
	@Column(name="idProducto")
	private Integer idProducto;
	
	public boolean equals(Object o) {
	    return ((o instanceof TrabajoProductoPK) && idTrabajo.equals(((TrabajoProductoPK) o).getIdTrabajo()) && idProducto.equals(((TrabajoProductoPK) o).getIdProducto()));
	}

	public int hashCode() {
		return idTrabajo.hashCode() + idProducto.hashCode();
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
}
