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
import javax.persistence.Transient;

@Entity
@Table(name="mantencion_programada")
public class MantencionProgramada implements Serializable {

	private static final long serialVersionUID = -2776716534439110309L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMantencionProgramada")
	private Integer idMantencionProgramada;
	
	@Column(name="codigo")
	private String codigo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;
	
	@OneToMany(mappedBy="mantencionProgramada")
	private List<MantencionProgramadaTrabajo> mantencionTrabajos;

	@Transient
	private Integer numTrabajosAsociados;
	
	/**
	 * @return the idMantencionProgramada
	 */
	public Integer getIdMantencionProgramada() {
		return idMantencionProgramada;
	}

	/**
	 * @param idMantencionProgramada the idMantencionProgramada to set
	 */
	public void setIdMantencionProgramada(Integer idMantencionProgramada) {
		this.idMantencionProgramada = idMantencionProgramada;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return the mantencionTrabajos
	 */
	public List<MantencionProgramadaTrabajo> getMantencionTrabajos() {
		return mantencionTrabajos;
	}

	/**
	 * @param mantencionTrabajos the mantencionTrabajos to set
	 */
	public void setMantencionTrabajos(
			List<MantencionProgramadaTrabajo> mantencionTrabajos) {
		this.mantencionTrabajos = mantencionTrabajos;
	}

	/**
	 * @return the numTrabajosAsociados
	 */
	public Integer getNumTrabajosAsociados() {
		numTrabajosAsociados = 0;
		
		if(mantencionTrabajos != null){
			for(MantencionProgramadaTrabajo mpt : mantencionTrabajos){
				if(mpt.getTrabajo().getEstado()){
					numTrabajosAsociados++;
				}
			}
		}
		
		return numTrabajosAsociados;
	}

	/**
	 * @param numTrabajosAsociados the numTrabajosAsociados to set
	 */
	public void setNumTrabajosAsociados(Integer numTrabajosAsociados) {
		this.numTrabajosAsociados = numTrabajosAsociados;
	}
}
