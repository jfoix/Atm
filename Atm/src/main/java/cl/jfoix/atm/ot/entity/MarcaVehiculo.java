package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="marca_vehiculo")
public class MarcaVehiculo implements Serializable {

	private static final long serialVersionUID = -8344902913568353303L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMarcaVehiculo")
	private Integer idMarcaVehiculo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	/**
	 * @return the idMarcaVehiculo
	 */
	public Integer getIdMarcaVehiculo() {
		return idMarcaVehiculo;
	}

	/**
	 * @param idMarcaVehiculo the idMarcaVehiculo to set
	 */
	public void setIdMarcaVehiculo(Integer idMarcaVehiculo) {
		this.idMarcaVehiculo = idMarcaVehiculo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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