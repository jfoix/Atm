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
import javax.persistence.Transient;

@Entity
@Table(name="trabajo")
public class Trabajo implements Serializable{

	private static final long serialVersionUID = 452654085699865997L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idTrabajo")
	private Integer idTrabajo;
	
	@Column(name="codigo")
	private String codigo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="precioManoObra")
	private Long precioManoObra;
	
	@Column(name="hhEstimada")
	private Double hhEstimada;
	
	@Column(name="estado")
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name="idTrabajoSubTipo")
	private TrabajoSubTipo trabajoSubTipo;
	
	@OneToMany(mappedBy="trabajo")
	private List<TrabajoProducto> trabajoProductos;

	@Transient
	private boolean seleccionado;

	/**
	 * @return the idTrabajo
	 */
	public Integer getIdTrabajo() {
		return idTrabajo;
	}

	/**
	 * @param idTrabajo the idTrabajo to set
	 */
	public void setIdTrabajo(Integer idTrabajo) {
		this.idTrabajo = idTrabajo;
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
	 * @return the precioManoObra
	 */
	public Long getPrecioManoObra() {
		return precioManoObra;
	}

	/**
	 * @param precioManoObra the precioManoObra to set
	 */
	public void setPrecioManoObra(Long precioManoObra) {
		this.precioManoObra = precioManoObra;
	}

	/**
	 * @return the hhEstimada
	 */
	public Double getHhEstimada() {
		return hhEstimada;
	}

	/**
	 * @param hhEstimada the hhEstimada to set
	 */
	public void setHhEstimada(Double hhEstimada) {
		this.hhEstimada = hhEstimada;
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
	 * @return the trabajoSubTipo
	 */
	public TrabajoSubTipo getTrabajoSubTipo() {
		return trabajoSubTipo;
	}

	/**
	 * @param trabajoSubTipo the trabajoSubTipo to set
	 */
	public void setTrabajoSubTipo(TrabajoSubTipo trabajoSubTipo) {
		this.trabajoSubTipo = trabajoSubTipo;
	}

	/**
	 * @return the trabajoProductos
	 */
	public List<TrabajoProducto> getTrabajoProductos() {
		return trabajoProductos;
	}

	/**
	 * @param trabajoProductos the trabajoProductos to set
	 */
	public void setTrabajoProductos(List<TrabajoProducto> trabajoProductos) {
		this.trabajoProductos = trabajoProductos;
	}

	/**
	 * @return the seleccionado
	 */
	public boolean isSeleccionado() {
		return seleccionado;
	}

	/**
	 * @param seleccionado the seleccionado to set
	 */
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
}
