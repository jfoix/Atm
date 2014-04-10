package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cl.jfoix.atm.comun.entity.Trabajo;

@Entity
@Table(name="orden_trabajo")
public class OrdenTrabajo implements Serializable{

	private static final long serialVersionUID = -8952356338377803176L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenTrabajo")
	private Integer idOrdenTrabajo;
	
	@ManyToOne
	@JoinColumn(name="idOrden")
	private Orden orden;
	
	@ManyToOne
	@JoinColumn(name="idTrabajo")
	private Trabajo trabajo;
	
	@Column(name="precioManoObra")
	private Integer precioManoObra;
	
	@Column(name="hhEstimada")
	private Double hhEstimada;
	
	@Column(name="fechaInicio")
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Column(name="fechaTermino")
	@Temporal(TemporalType.DATE)
	private Date fechaTermino;
	
	@Column(name="garantia")
	private Boolean garantia;

	@OneToMany(mappedBy="ordenTrabajo", cascade=CascadeType.ALL)
	private List<OrdenTrabajoEstado> estadosOrden;
	
	@OneToMany(mappedBy="ordenTrabajo")
	private List<OrdenTrabajoUsuario> ordenTrabajoUsuarios;
	
	@OneToMany(mappedBy="ordenTrabajo", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<OrdenTrabajoProducto> ordenTrabajoProductos;

	@Transient
	private OrdenTrabajoEstado ultimoEstado;
	
//	public OrdenTrabajoEstado getUlitmoEstado(){
//		return estadosOrden != null && estadosOrden.size() > 0 ? estadosOrden.get(estadosOrden.size() - 1) : null;
//	}
	
	/**
	 * @return the idOrdenTrabajo
	 */
	public Integer getIdOrdenTrabajo() {
		return idOrdenTrabajo;
	}

	/**
	 * @param idOrdenTrabajo the idOrdenTrabajo to set
	 */
	public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
		this.idOrdenTrabajo = idOrdenTrabajo;
	}

	/**
	 * @return the orden
	 */
	public Orden getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	/**
	 * @return the trabajo
	 */
	public Trabajo getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

	/**
	 * @return the precioManoObra
	 */
	public Integer getPrecioManoObra() {
		return precioManoObra;
	}

	/**
	 * @param precioManoObra the precioManoObra to set
	 */
	public void setPrecioManoObra(Integer precioManoObra) {
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
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaTermino
	 */
	public Date getFechaTermino() {
		return fechaTermino;
	}

	/**
	 * @param fechaTermino the fechaTermino to set
	 */
	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	/**
	 * @return the garantia
	 */
	public Boolean getGarantia() {
		return garantia;
	}

	/**
	 * @param garantia the garantia to set
	 */
	public void setGarantia(Boolean garantia) {
		this.garantia = garantia;
	}

	/**
	 * @return the ordenTrabajoProductos
	 */
	public List<OrdenTrabajoProducto> getOrdenTrabajoProductos() {
		if(ordenTrabajoProductos == null){
			ordenTrabajoProductos = new ArrayList<OrdenTrabajoProducto>();
		}
		return ordenTrabajoProductos;
	}

	/**
	 * @param ordenTrabajoProductos the ordenTrabajoProductos to set
	 */
	public void setOrdenTrabajoProductos(
			List<OrdenTrabajoProducto> ordenTrabajoProductos) {
		this.ordenTrabajoProductos = ordenTrabajoProductos;
	}

	/**
	 * @return the ordenTrabajoUsuarios
	 */
	public List<OrdenTrabajoUsuario> getOrdenTrabajoUsuarios() {
		return ordenTrabajoUsuarios;
	}

	/**
	 * @param ordenTrabajoUsuarios the ordenTrabajoUsuarios to set
	 */
	public void setOrdenTrabajoUsuarios(
			List<OrdenTrabajoUsuario> ordenTrabajoUsuarios) {
		this.ordenTrabajoUsuarios = ordenTrabajoUsuarios;
	}

	/**
	 * @return the estadosOrden
	 */
	public List<OrdenTrabajoEstado> getEstadosOrden() {
		return estadosOrden;
	}

	/**
	 * @param estadosOrden the estadosOrden to set
	 */
	public void setEstadosOrden(List<OrdenTrabajoEstado> estadosOrden) {
		this.estadosOrden = estadosOrden;
	}

	/**
	 * @return the ultimoEstado
	 */
	public OrdenTrabajoEstado getUltimoEstado() {
		ultimoEstado = estadosOrden != null && estadosOrden.size() > 0 ? estadosOrden.get(estadosOrden.size() - 1) : null;
		return ultimoEstado;
	}

	/**
	 * @param ultimoEstado the ultimoEstado to set
	 */
	public void setUltimoEstado(OrdenTrabajoEstado ultimoEstado) {
		this.ultimoEstado = ultimoEstado;
	}
}
