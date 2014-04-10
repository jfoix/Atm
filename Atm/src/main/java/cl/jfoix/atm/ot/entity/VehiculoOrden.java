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
@Table(name="vehiculo_orden")
public class VehiculoOrden implements Serializable {

	private static final long serialVersionUID = -8344902913568353303L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idVehiculoOrden")
	private Integer idVehiculoOrden;

	@ManyToOne
	@JoinColumn(name="idVehiculo")
	private Vehiculo vehiculo;
	
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;
	
	@Column(name="kilometraje")
	private Integer kilometraje;

	/**
	 * @return the idVehiculoOrden
	 */
	public Integer getIdVehiculoOrden() {
		return idVehiculoOrden;
	}

	/**
	 * @param idVehiculoOrden the idVehiculoOrden to set
	 */
	public void setIdVehiculoOrden(Integer idVehiculoOrden) {
		this.idVehiculoOrden = idVehiculoOrden;
	}

	/**
	 * @return the vehiculo
	 */
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	/**
	 * @param vehiculo the vehiculo to set
	 */
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the kilometraje
	 */
	public Integer getKilometraje() {
		return kilometraje;
	}

	/**
	 * @param kilometraje the kilometraje to set
	 */
	public void setKilometraje(Integer kilometraje) {
		this.kilometraje = kilometraje;
	}
}