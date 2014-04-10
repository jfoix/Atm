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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="orden_estado")
public class OrdenEstado implements Serializable {

	private static final long serialVersionUID = -4039183107661656372L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenEstado")
	private Integer idOrdenEstado;

	@OneToOne
	@JoinColumn(name="idEstadoOrden")
	private EstadoOrden estadoOrden;
	
	@ManyToOne
	@JoinColumn(name="idOrden")
	private Orden orden;

	@Column(name="fechaInicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@Column(name="fechaTermino")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaTermino;
	
	@Column(name="observacion")
	private String observacion;

	/**
	 * @return the idOrdenEstado
	 */
	public Integer getIdOrdenEstado() {
		return idOrdenEstado;
	}

	/**
	 * @param idOrdenEstado the idOrdenEstado to set
	 */
	public void setIdOrdenEstado(Integer idOrdenEstado) {
		this.idOrdenEstado = idOrdenEstado;
	}

	/**
	 * @return the estadoOrden
	 */
	public EstadoOrden getEstadoOrden() {
		return estadoOrden;
	}

	/**
	 * @param estadoOrden the estadoOrden to set
	 */
	public void setEstadoOrden(EstadoOrden estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	/**
	 * @return the orden
	 */
	public Orden getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Orden orden) {
		this.orden = orden;
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

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}