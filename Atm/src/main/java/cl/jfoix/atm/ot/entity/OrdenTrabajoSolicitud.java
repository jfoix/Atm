package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.jfoix.atm.login.entity.Usuario;

@Entity
@Table(name="orden_trabajo_solicitud")
public class OrdenTrabajoSolicitud implements Serializable{

	private static final long serialVersionUID = -8952356338377803176L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenTrabajoSolicitud")
	private Integer idOrdenTrabajoSolicitud;
	
	@ManyToOne
	@JoinColumn(name="idOrdenTrabajo")
	private OrdenTrabajo ordenTrabajo;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="fechaSolicitud")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSolicitud;
	
	@Column(name="fechaTermino")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaTermino;
	
	@ManyToOne
	@JoinColumn(name="nombreUsuario")
	private Usuario usuario;

	@Column(name="estado")
	private Boolean estado;
	
	@OneToMany(mappedBy="solicitud")
	private List<OrdenTrabajoSolicitudProducto> productos;

	/**
	 * @return the idOrdenTrabajoSolicitud
	 */
	public Integer getIdOrdenTrabajoSolicitud() {
		return idOrdenTrabajoSolicitud;
	}

	/**
	 * @param idOrdenTrabajoSolicitud the idOrdenTrabajoSolicitud to set
	 */
	public void setIdOrdenTrabajoSolicitud(Integer idOrdenTrabajoSolicitud) {
		this.idOrdenTrabajoSolicitud = idOrdenTrabajoSolicitud;
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
	 * @return the fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
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
	 * @return the productos
	 */
	public List<OrdenTrabajoSolicitudProducto> getProductos() {
		return productos;
	}

	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<OrdenTrabajoSolicitudProducto> productos) {
		this.productos = productos;
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
	 * @return the estado
	 */
	public Boolean getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}