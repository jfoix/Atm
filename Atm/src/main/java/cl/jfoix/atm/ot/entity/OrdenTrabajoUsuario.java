package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cl.jfoix.atm.login.entity.Usuario;

@Entity
@Table(name="orden_trabajo_usuario")
public class OrdenTrabajoUsuario implements Serializable {

	private static final long serialVersionUID = 8020332083618221978L;

	@EmbeddedId
	private OrdenTrabajoUsuarioPK pk;
	
	@ManyToOne
	@JoinColumn(name="nombreUsuario", insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="idOrdenTrabajo", insertable=false, updatable=false)
	private OrdenTrabajo ordenTrabajo;

	/**
	 * @return the pk
	 */
	public OrdenTrabajoUsuarioPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(OrdenTrabajoUsuarioPK pk) {
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
	 * @return the ordenTrabajo
	 */
	public OrdenTrabajo getOrdenTrabajo() {
		return ordenTrabajo;
	}

	/**
	 * @param ordenTrabajo the ordenTrabajo to set
	 */
	public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
}
