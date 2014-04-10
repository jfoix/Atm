package cl.jfoix.atm.ot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="forma_pago")
public class FormaPago implements Serializable {

	private static final long serialVersionUID = 6791460835870381545L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idFormaPago")
	private Integer idFormaPago;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	/**
	 * @return the idFormaPago
	 */
	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	/**
	 * @param idFormaPago the idFormaPago to set
	 */
	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
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
