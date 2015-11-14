package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;

import cl.jfoix.atm.comun.entity.Producto;

@Entity
@Table(name="stock")
public class Stock implements Serializable {

	private static final long serialVersionUID = 4478435075024236789L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idStock")
	private Integer idStock;

	@ManyToOne
	@JoinColumn(name="idProducto")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="idBodega")
	private Bodega bodega;
	
	@Column(name="cantidad")
	private Double cantidad;

	@OneToMany(mappedBy="stock")
	private List<Movimiento> movimientos;

	@Column(name="coordBodega")
	private String coordBodega;
	
	@Transient
	private Integer mayorPrecioCompra;
	
	@Transient
	private Integer precioVenta;
	
	@Transient
	private Integer ultimoPrecioVenta;
	
	/**
	 * @return the idStock
	 */
	public Integer getIdStock() {
		return idStock;
	}

	/**
	 * @param idStock the idStock to set
	 */
	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
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
	 * @return the bodega
	 */
	public Bodega getBodega() {
		return bodega;
	}

	/**
	 * @param bodega the bodega to set
	 */
	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
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
	 * @return the movimientos
	 */
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	/**
	 * @param movimientos the movimientos to set
	 */
	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	/**
	 * @return the ultimoPrecioVenta
	 */
	public Integer getUltimoPrecioVenta() {
		return ultimoPrecioVenta;
	}

	/**
	 * @param ultimoPrecioVenta the ultimoPrecioVenta to set
	 */
	public void setUltimoPrecioVenta(Integer ultimoPrecioVenta) {
		this.ultimoPrecioVenta = ultimoPrecioVenta;
	}

	/**
	 * @return the mayorPrecioCompra
	 */
	public Integer getMayorPrecioCompra() {
		return mayorPrecioCompra;
	}

	/**
	 * @param mayorPrecioCompra the mayorPrecioCompra to set
	 */
	public void setMayorPrecioCompra(Integer mayorPrecioCompra) {
		this.mayorPrecioCompra = mayorPrecioCompra;
	}

	/**
	 * @return the coordBodega
	 */
	public String getCoordBodega() {
		return coordBodega;
	}

	/**
	 * @param coordBodega the coordBodega to set
	 */
	public void setCoordBodega(String coordBodega) {
		this.coordBodega = coordBodega;
	}

	/**
	 * @return the precioVenta
	 */
	public Integer getPrecioVenta() {
		return precioVenta;
	}

	/**
	 * @param precioVenta the precioVenta to set
	 */
	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}
}
