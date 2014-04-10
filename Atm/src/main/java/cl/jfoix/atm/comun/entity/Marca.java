package cl.jfoix.atm.comun.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="marca")
public class Marca implements Serializable {

	private static final long serialVersionUID = -2776716534439110309L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMarca")
	private Integer idMarca;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	/**
	 * @return the idMarca
	 */
	public Integer getIdMarca() {
		return idMarca;
	}

	/**
	 * @param idMarca the idMarca to set
	 */
	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
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
