package cl.jfoix.atm.comun.entity;

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

@Entity
@Table(name="trabajo_sub_tipo")
public class TrabajoSubTipo implements Serializable {

	private static final long serialVersionUID = 2041879692757240881L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idTrabajoSubTipo")
	private Integer idTrabajoSubTipo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;
	
	@Column(name="externo")
	private Boolean externo;

	@ManyToOne
	@JoinColumn(name="idTrabajoTipo")
	private TrabajoTipo trabajoTipo;
	
	@OneToMany(mappedBy="trabajoSubTipo")
	private List<Trabajo> trabajos;

	/**
	 * @return the idTrabajoSubTipo
	 */
	public Integer getIdTrabajoSubTipo() {
		return idTrabajoSubTipo;
	}

	/**
	 * @param idTrabajoSubTipo the idTrabajoSubTipo to set
	 */
	public void setIdTrabajoSubTipo(Integer idTrabajoSubTipo) {
		this.idTrabajoSubTipo = idTrabajoSubTipo;
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

	/**
	 * @return the trabajoTipo
	 */
	public TrabajoTipo getTrabajoTipo() {
		return trabajoTipo;
	}

	/**
	 * @param trabajoTipo the trabajoTipo to set
	 */
	public void setTrabajoTipo(TrabajoTipo trabajoTipo) {
		this.trabajoTipo = trabajoTipo;
	}

	/**
	 * @return the trabajos
	 */
	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

	/**
	 * @param trabajos the trabajos to set
	 */
	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	/**
	 * @return the externo
	 */
	public Boolean getExterno() {
		return externo;
	}

	/**
	 * @param externo the externo to set
	 */
	public void setExterno(Boolean externo) {
		this.externo = externo;
	}
}
