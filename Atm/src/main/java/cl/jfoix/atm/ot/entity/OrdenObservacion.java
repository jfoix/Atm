package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.jfoix.atm.ot.util.TipoOrdenObservacionEnum;

@Entity
@Table(name="orden_observacion")
public class OrdenObservacion implements Serializable {

	private static final long serialVersionUID = -4039183107661656372L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenObservacion")
	private Integer idOrdenObservacion;
	
	@ManyToOne
	@JoinColumn(name="idOrden")
	private Orden orden;
	
	@Column(name="fechaRegistro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="tipoObservacion")
	@Enumerated(EnumType.ORDINAL)
	private TipoOrdenObservacionEnum tipoObservacion;

	/**
	 * @return the idOrdenObservacion
	 */
	public Integer getIdOrdenObservacion() {
		return idOrdenObservacion;
	}

	/**
	 * @param idOrdenObservacion the idOrdenObservacion to set
	 */
	public void setIdOrdenObservacion(Integer idOrdenObservacion) {
		this.idOrdenObservacion = idOrdenObservacion;
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
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	/**
	 * @return the tipoObservacion
	 */
	public TipoOrdenObservacionEnum getTipoObservacion() {
		return tipoObservacion;
	}

	/**
	 * @param tipoObservacion the tipoObservacion to set
	 */
	public void setTipoObservacion(TipoOrdenObservacionEnum tipoObservacion) {
		this.tipoObservacion = tipoObservacion;
	}

}
