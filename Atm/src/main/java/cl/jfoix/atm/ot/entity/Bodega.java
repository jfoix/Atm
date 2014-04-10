package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bodega")
public class Bodega implements Serializable {

	private static final long serialVersionUID = 746734402982542767L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idBodega")
	private Integer idBodega;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="principal")
	private Boolean principal;
	
	@Column(name="estado")
	private Boolean estado;

	/**
	 * @return the idBodega
	 */
	public Integer getIdBodega() {
		return idBodega;
	}

	/**
	 * @param idBodega the idBodega to set
	 */
	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
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
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the principal
	 */
	public Boolean getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
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
