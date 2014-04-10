package cl.jfoix.atm.comun.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="trabajo_producto")
public class TrabajoProducto implements Serializable {

	private static final long serialVersionUID = 7090332670890501976L;
	
	@EmbeddedId
	private TrabajoProductoPK pk;
	
	@ManyToOne
	@JoinColumn(name="idTrabajo", insertable=false, updatable=false)
	private Trabajo trabajo;
	
	@ManyToOne
	@JoinColumn(name="idProducto", insertable=false, updatable=false)
	private Producto producto;
	
	@Column(name="cantidadProducto")
	private Double cantidadProducto;

	@Transient
	private boolean seleccionado;

	/**
	 * @return the pk
	 */
	public TrabajoProductoPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(TrabajoProductoPK pk) {
		this.pk = pk;
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
	 * @return the cantidadProducto
	 */
	public Double getCantidadProducto() {
		return cantidadProducto;
	}

	/**
	 * @param cantidadProducto the cantidadProducto to set
	 */
	public void setCantidadProducto(Double cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	/**
	 * @return the seleccionado
	 */
	public boolean isSeleccionado() {
		return seleccionado;
	}

	/**
	 * @param seleccionado the seleccionado to set
	 */
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
}
