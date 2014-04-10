package cl.jfoix.atm.comun.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mantencion_programada_trabajo")
public class MantencionProgramadaTrabajo implements Serializable {

	private static final long serialVersionUID = -2776716534439110309L;

	@EmbeddedId
	private MantencionProgramadaTrabajoPK pk;
	
	@ManyToOne
	@JoinColumn(name="idMantencionProgramada", insertable=false, updatable=false)
	private MantencionProgramada mantencionProgramada;
	
	@ManyToOne
	@JoinColumn(name="idTrabajo", insertable=false, updatable=false)
	private Trabajo trabajo;

	/**
	 * @return the pk
	 */
	public MantencionProgramadaTrabajoPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(MantencionProgramadaTrabajoPK pk) {
		this.pk = pk;
	}

	/**
	 * @return the mantencionProgramada
	 */
	public MantencionProgramada getMantencionProgramada() {
		return mantencionProgramada;
	}

	/**
	 * @param mantencionProgramada the mantencionProgramada to set
	 */
	public void setMantencionProgramada(MantencionProgramada mantencionProgramada) {
		this.mantencionProgramada = mantencionProgramada;
	}

	/**
	 * @return the trabajo
	 */
	public Trabajo getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}
}
