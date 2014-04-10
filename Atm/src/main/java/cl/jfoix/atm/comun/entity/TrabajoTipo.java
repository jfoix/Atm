package cl.jfoix.atm.comun.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="trabajo_tipo")
public class TrabajoTipo implements Serializable {

	private static final long serialVersionUID = 2041879692757240881L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idTrabajoTipo")
	private Integer idTrabajoTipo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;
	
	@OneToMany(mappedBy="trabajoTipo")
	private List<TrabajoSubTipo> trabajosSubTipo;

	/**
	 * @return the idTrabajoTipo
	 */
	public Integer getIdTrabajoTipo() {
		return idTrabajoTipo;
	}

	/**
	 * @param idTrabajoTipo the idTrabajoTipo to set
	 */
	public void setIdTrabajoTipo(Integer idTrabajoTipo) {
		this.idTrabajoTipo = idTrabajoTipo;
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
	 * @return the trabajosSubTipo
	 */
	public List<TrabajoSubTipo> getTrabajosSubTipo() {
		return trabajosSubTipo;
	}

	/**
	 * @param trabajosSubTipo the trabajosSubTipo to set
	 */
	public void setTrabajosSubTipo(List<TrabajoSubTipo> trabajosSubTipo) {
		this.trabajosSubTipo = trabajosSubTipo;
	}
}
