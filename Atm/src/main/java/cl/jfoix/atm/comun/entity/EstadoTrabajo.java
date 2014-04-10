package cl.jfoix.atm.comun.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estado_trabajo")
public class EstadoTrabajo implements Serializable{

	private static final long serialVersionUID = 452654085699865997L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idEstadoTrabajo")
	private Integer idEstadoTrabajo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	public EstadoTrabajo(){
	}
	
	public EstadoTrabajo(Integer idEstadoTrabajo){
		this.idEstadoTrabajo = idEstadoTrabajo;
	}
	
	/**
	 * @return the idEstadoTrabajo
	 */
	public Integer getIdEstadoTrabajo() {
		return idEstadoTrabajo;
	}

	/**
	 * @param idEstadoTrabajo the idEstadoTrabajo to set
	 */
	public void setIdEstadoTrabajo(Integer idEstadoTrabajo) {
		this.idEstadoTrabajo = idEstadoTrabajo;
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
