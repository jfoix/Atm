package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
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

import cl.jfoix.atm.login.entity.Usuario;

@Entity
@Table(name="orden")
public class Orden implements Serializable {

	private static final long serialVersionUID = -4039183107661656372L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrden")
	private Integer idOrden;

	@ManyToOne
	@JoinColumn(name="idVehiculoOrden")
	private VehiculoOrden vehiculoOrden;
	
	@ManyToOne
	@JoinColumn(name="nombreUsuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="idFormaPago")
	private FormaPago formaPago;
	
	@Column(name="fechaOrden")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaOrden;
	
	@OneToMany(mappedBy="orden", cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<OrdenTrabajo> ordenTrabajos;
	
	@OneToMany(mappedBy="orden")
	private List<OrdenEstado> ordenEstados;
	
	@OneToMany(mappedBy="orden")
	private List<OrdenDocumento> ordenDocumentos;
	
	@OneToMany(mappedBy="orden", cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<OrdenObservacion> ordenObservaciones;

	@Column(name="descuentoRepuestos")
	private Double descuentoRepuestos;
	
	@Column(name="descuentoManoObra")
	private Double descuentoManoObra;
	
	@Column(name="descuentoTerceros")
	private Double descuentoTerceros;
	
	@Column(name="tipoDescRepuestos")
	private String tipoDescRepuestos;
	
	@Column(name="tipoDescManoObra")
	private String tipoDescManoObra;
	
	@Column(name="tipoDescTerceros")
	private String tipoDescTerceros;
	
	@Transient
	private OrdenEstado ultimoOrdenEstado;
	
	@Transient
	private int trabajosPendientes;
	
	@Transient
	private int trabajosEnProceso;
	
	@Transient
	private int trabajosTerminados;
	
	@Transient
	private int trabajosCancelados;

	/**
	 * @return the idOrden
	 */
	public Integer getIdOrden() {
		return idOrden;
	}

	/**
	 * @param idOrden the idOrden to set
	 */
	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}

	/**
	 * @return the vehiculoOrden
	 */
	public VehiculoOrden getVehiculoOrden() {
		return vehiculoOrden;
	}

	/**
	 * @param vehiculoOrden the vehiculoOrden to set
	 */
	public void setVehiculoOrden(VehiculoOrden vehiculoOrden) {
		this.vehiculoOrden = vehiculoOrden;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the formaPago
	 */
	public FormaPago getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the fechaOrden
	 */
	public Date getFechaOrden() {
		return fechaOrden;
	}

	/**
	 * @param fechaOrden the fechaOrden to set
	 */
	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	/**
	 * @return the ordenTrabajos
	 */
	public List<OrdenTrabajo> getOrdenTrabajos() {
		return ordenTrabajos;
	}

	/**
	 * @param ordenTrabajos the ordenTrabajos to set
	 */
	public void setOrdenTrabajos(List<OrdenTrabajo> ordenTrabajos) {
		this.ordenTrabajos = ordenTrabajos;
	}

	/**
	 * @return the ordenEstados
	 */
	public List<OrdenEstado> getOrdenEstados() {
		return ordenEstados;
	}

	/**
	 * @param ordenEstados the ordenEstados to set
	 */
	public void setOrdenEstados(List<OrdenEstado> ordenEstados) {
		this.ordenEstados = ordenEstados;
	}

	/**
	 * @return the ultimoOrdenEstado
	 */
	public OrdenEstado getUltimoOrdenEstado() {
		if(ordenEstados != null && ordenEstados.size() > 0){
			for(OrdenEstado oe : ordenEstados){
				if(oe.getFechaTermino() == null){
					ultimoOrdenEstado = oe;
					break;
				}
			}
		}
		
		return ultimoOrdenEstado;
	}

	/**
	 * @param ultimoOrdenEstado the ultimoOrdenEstado to set
	 */
	public void setUltimoOrdenEstado(OrdenEstado ultimoOrdenEstado) {
		this.ultimoOrdenEstado = ultimoOrdenEstado;
	}

	/**
	 * @return the trabajosPendientes
	 */
	public int getTrabajosPendientes() {
		
		trabajosPendientes = 0;
		
		if(ordenTrabajos != null){
			for(OrdenTrabajo ot : ordenTrabajos){
				if(ot.getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(1)){
					trabajosPendientes++;
				}
			}
		}
		
		return trabajosPendientes;
	}

	/**
	 * @param trabajosPendientes the trabajosPendientes to set
	 */
	public void setTrabajosPendientes(int trabajosPendientes) {
		this.trabajosPendientes = trabajosPendientes;
	}

	/**
	 * @return the trabajosEnProceso
	 */
	public int getTrabajosEnProceso() {
		
		trabajosEnProceso = 0;
		
		if(ordenTrabajos != null){
			for(OrdenTrabajo ot : ordenTrabajos){
				if(ot.getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(2)){
					trabajosEnProceso++;
				}
			}
		}
		
		return trabajosEnProceso;
	}

	/**
	 * @param trabajosEnProceso the trabajosEnProceso to set
	 */
	public void setTrabajosEnProceso(int trabajosEnProceso) {
		this.trabajosEnProceso = trabajosEnProceso;
	}

	/**
	 * @return the trabajosTerminados
	 */
	public int getTrabajosTerminados() {
		trabajosTerminados = 0;
		
		if(ordenTrabajos != null){
			for(OrdenTrabajo ot : ordenTrabajos){
				if(ot.getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(3)){
					trabajosTerminados++;
				}
			}
		}
		
		return trabajosTerminados;
	}

	/**
	 * @param trabajosTerminados the trabajosTerminados to set
	 */
	public void setTrabajosTerminados(int trabajosTerminados) {
		this.trabajosTerminados = trabajosTerminados;
	}

	/**
	 * @return the trabajosCancelados
	 */
	public int getTrabajosCancelados() {
		
		trabajosCancelados = 0;
		
		if(ordenTrabajos != null){
			for(OrdenTrabajo ot : ordenTrabajos){
				if(ot.getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(4)){
					trabajosCancelados++;
				}
			}
		}
		
		return trabajosCancelados;
	}

	/**
	 * @param trabajosCancelados the trabajosCancelados to set
	 */
	public void setTrabajosCancelados(int trabajosCancelados) {
		this.trabajosCancelados = trabajosCancelados;
	}

	/**
	 * @return the ordenDocumentos
	 */
	public List<OrdenDocumento> getOrdenDocumentos() {
		return ordenDocumentos;
	}

	/**
	 * @param ordenDocumentos the ordenDocumentos to set
	 */
	public void setOrdenDocumentos(List<OrdenDocumento> ordenDocumentos) {
		this.ordenDocumentos = ordenDocumentos;
	}

	/**
	 * @return the ordenObservaciones
	 */
	public List<OrdenObservacion> getOrdenObservaciones() {
		return ordenObservaciones;
	}

	/**
	 * @param ordenObservaciones the ordenObservaciones to set
	 */
	public void setOrdenObservaciones(List<OrdenObservacion> ordenObservaciones) {
		this.ordenObservaciones = ordenObservaciones;
	}

	/**
	 * @return the descuentoRepuestos
	 */
	public Double getDescuentoRepuestos() {
		return descuentoRepuestos;
	}

	/**
	 * @param descuentoRepuestos the descuentoRepuestos to set
	 */
	public void setDescuentoRepuestos(Double descuentoRepuestos) {
		this.descuentoRepuestos = descuentoRepuestos;
	}

	/**
	 * @return the descuentoManoObra
	 */
	public Double getDescuentoManoObra() {
		return descuentoManoObra;
	}

	/**
	 * @param descuentoManoObra the descuentoManoObra to set
	 */
	public void setDescuentoManoObra(Double descuentoManoObra) {
		this.descuentoManoObra = descuentoManoObra;
	}

	/**
	 * @return the descuentoTerceros
	 */
	public Double getDescuentoTerceros() {
		return descuentoTerceros;
	}

	/**
	 * @param descuentoTerceros the descuentoTerceros to set
	 */
	public void setDescuentoTerceros(Double descuentoTerceros) {
		this.descuentoTerceros = descuentoTerceros;
	}

	/**
	 * @return the tipoDescRepuestos
	 */
	public String getTipoDescRepuestos() {
		return tipoDescRepuestos;
	}

	/**
	 * @param tipoDescRepuestos the tipoDescRepuestos to set
	 */
	public void setTipoDescRepuestos(String tipoDescRepuestos) {
		this.tipoDescRepuestos = tipoDescRepuestos;
	}

	/**
	 * @return the tipoDescManoObra
	 */
	public String getTipoDescManoObra() {
		return tipoDescManoObra;
	}

	/**
	 * @param tipoDescManoObra the tipoDescManoObra to set
	 */
	public void setTipoDescManoObra(String tipoDescManoObra) {
		this.tipoDescManoObra = tipoDescManoObra;
	}

	/**
	 * @return the tipoDescTerceros
	 */
	public String getTipoDescTerceros() {
		return tipoDescTerceros;
	}

	/**
	 * @param tipoDescTerceros the tipoDescTerceros to set
	 */
	public void setTipoDescTerceros(String tipoDescTerceros) {
		this.tipoDescTerceros = tipoDescTerceros;
	}
}