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
@Table(name="movimiento_documento")
public class MovimientoDocumento implements Serializable {

	private static final long serialVersionUID = 4478435075024236789L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMovimientoDocumento")
	private Integer idMovimientoDocumento;

	@ManyToOne
	@JoinColumn(name="idMovimiento")
	private Movimiento movimiento;
	
	@Column(name="rutaNombre")
	private String rutaNombre;

	/**
	 * @return the idMovimientoDocumento
	 */
	public Integer getIdMovimientoDocumento() {
		return idMovimientoDocumento;
	}

	/**
	 * @param idMovimientoDocumento the idMovimientoDocumento to set
	 */
	public void setIdMovimientoDocumento(Integer idMovimientoDocumento) {
		this.idMovimientoDocumento = idMovimientoDocumento;
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
	 * @return the rutaNombre
	 */
	public String getRutaNombre() {
		return rutaNombre;
	}

	/**
	 * @param rutaNombre the rutaNombre to set
	 */
	public void setRutaNombre(String rutaNombre) {
		this.rutaNombre = rutaNombre;
	}
}