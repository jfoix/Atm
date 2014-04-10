package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estado_orden")
public class EstadoOrden implements Serializable {

	private static final long serialVersionUID = -4039183107661656372L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idEstadoOrden")
	private Integer idEstadoOrden;

	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	/**
	 * @return the idEstadoOrden
	 */
	public Integer getIdEstadoOrden() {
		return idEstadoOrden;
	}

	/**
	 * @param idEstadoOrden the idEstadoOrden to set
	 */
	public void setIdEstadoOrden(Integer idEstadoOrden) {
		this.idEstadoOrden = idEstadoOrden;
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
