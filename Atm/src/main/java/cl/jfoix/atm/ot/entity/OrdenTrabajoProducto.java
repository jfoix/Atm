package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cl.jfoix.atm.comun.entity.Producto;

@Entity
@Table(name="orden_trabajo_producto")
public class OrdenTrabajoProducto implements Serializable{

	private static final long serialVersionUID = -8952356338377803176L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenTrabajoProducto")
	private Integer idOrdenTrabajoProducto;
	
	@ManyToOne
	@JoinColumn(name="idOrdenTrabajo")
	private OrdenTrabajo ordenTrabajo;
	
	@ManyToOne
	@JoinColumn(name="idProducto")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="idMovimiento")
	private Movimiento movimiento;
	
	@Column(name="traidoCliente")
	private Boolean traidoCliente;
	
	@Column(name="cantidad")
	private Double cantidad;
	
	@Column(name="valor")
	private Integer valor;
	
	@Transient
	private boolean tieneStock;

	/**
	 * @return the idOrdenTrabajoProducto
	 */
	public Integer getIdOrdenTrabajoProducto() {
		return idOrdenTrabajoProducto;
	}

	/**
	 * @param idOrdenTrabajoProducto the idOrdenTrabajoProducto to set
	 */
	public void setIdOrdenTrabajoProducto(Integer idOrdenTrabajoProducto) {
		this.idOrdenTrabajoProducto = idOrdenTrabajoProducto;
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
	 * @return the movimiento
	 */
	public Movimiento getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento the movimiento to set
	 */
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * @return the traidoCliente
	 */
	public Boolean getTraidoCliente() {
		return traidoCliente;
	}

	/**
	 * @param traidoCliente the traidoCliente to set
	 */
	public void setTraidoCliente(Boolean traidoCliente) {
		this.traidoCliente = traidoCliente;
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
	 * @return the valor
	 */
	public Integer getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
	}

	/**
	 * @return the tieneStock
	 */
	public boolean isTieneStock() {
		return tieneStock;
	}

	/**
	 * @param tieneStock the tieneStock to set
	 */
	public void setTieneStock(boolean tieneStock) {
		this.tieneStock = tieneStock;
	}
}
