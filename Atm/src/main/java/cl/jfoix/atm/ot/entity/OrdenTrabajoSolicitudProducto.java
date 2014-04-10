package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cl.jfoix.atm.comun.entity.Producto;

@Entity
@Table(name="orden_trabajo_solicitud_producto")
public class OrdenTrabajoSolicitudProducto implements Serializable{

	private static final long serialVersionUID = -8952356338377803176L;

	@EmbeddedId
	private OrdenTrabajoSolicitudProductoPK pk;
	
	@Column(name="cantidad")
	private Double cantidad;

	@Column(name="estado")
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name="idOrdenTrabajoSolicitud", insertable=false, updatable=false)
	private OrdenTrabajoSolicitud solicitud;
	
	@ManyToOne
	@JoinColumn(name="idProducto", insertable=false, updatable=false)
	private Producto producto;

	/**
	 * @return the pk
	 */
	public OrdenTrabajoSolicitudProductoPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(OrdenTrabajoSolicitudProductoPK pk) {
		this.pk = pk;
	}

	/**
	 * @return the cantidad
	 */
	public Double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the solicitud
	 */
	public OrdenTrabajoSolicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(OrdenTrabajoSolicitud solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
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