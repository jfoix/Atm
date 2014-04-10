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

@Entity
@Table(name="movimiento_ingreso")
public class MovimientoIngreso implements Serializable {

	private static final long serialVersionUID = 4478435075024236789L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMovimientoIngreso")
	private Integer idMovimientoIngreso;

	@ManyToOne
	@JoinColumn(name="idMovimiento")
	private Movimiento movimiento;

	@Column(name="cantidad")
	private Double cantidad;
	
	@Column(name="valorVenta")
	private Integer valorVenta;

	/**
	 * @return the idMovimientoIngreso
	 */
	public Integer getIdMovimientoIngreso() {
		return idMovimientoIngreso;
	}

	/**
	 * @param idMovimientoIngreso the idMovimientoIngreso to set
	 */
	public void setIdMovimientoIngreso(Integer idMovimientoIngreso) {
		this.idMovimientoIngreso = idMovimientoIngreso;
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
	 * @return the valorVenta
	 */
	public Integer getValorVenta() {
		return valorVenta;
	}

	/**
	 * @param valorVenta the valorVenta to set
	 */
	public void setValorVenta(Integer valorVenta) {
		this.valorVenta = valorVenta;
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
}