package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.jfoix.atm.comun.entity.EstadoTrabajo;

@Entity
@Table(name="orden_trabajo_estado")
public class OrdenTrabajoEstado implements Serializable{

	private static final long serialVersionUID = -8952356338377803176L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenTrabajoEstado")
	private Integer idOrdenTrabajoEstado;
	
	@ManyToOne
	@JoinColumn(name="idOrdenTrabajo")
	private OrdenTrabajo ordenTrabajo;
	
	@ManyToOne
	@JoinColumn(name="idEstadoTrabajo")
	private EstadoTrabajo estadoTrabajo;
	
	@Column(name="fechaInicio")
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Column(name="fechaTermino")
	@Temporal(TemporalType.DATE)
	private Date fechaTermino;

	/**
	 * @return the idOrdenTrabajoEstado
	 */
	public Integer getIdOrdenTrabajoEstado() {
		return idOrdenTrabajoEstado;
	}

	/**
	 * @param idOrdenTrabajoEstado the idOrdenTrabajoEstado to set
	 */
	public void setIdOrdenTrabajoEstado(Integer idOrdenTrabajoEstado) {
		this.idOrdenTrabajoEstado = idOrdenTrabajoEstado;
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

	/**
	 * @return the estadoTrabajo
	 */
	public EstadoTrabajo getEstadoTrabajo() {
		return estadoTrabajo;
	}

	/**
	 * @param estadoTrabajo the estadoTrabajo to set
	 */
	public void setEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
		this.estadoTrabajo = estadoTrabajo;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaTermino
	 */
	public Date getFechaTermino() {
		return fechaTermino;
	}

	/**
	 * @param fechaTermino the fechaTermino to set
	 */
	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}
}
