package cl.jfoix.atm.ot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;

@Entity
@Table(name="movimiento")
public class Movimiento implements Serializable {

	private static final long serialVersionUID = 4478435075024236789L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMovimiento")
	private Integer idMovimiento;

	@ManyToOne
	@JoinColumn(name="idStock")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name="idProveedor")
	private Proveedor proveedor;
	
	@Column(name="tipo")
	@Enumerated(EnumType.ORDINAL)
	private TipoMovimientoEnum tipo;
	
	@Column(name="fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name="cantidad")
	private Double cantidad;
	
	@Column(name="valorUnidad")
	private Integer valorUnidad;
	
	@Column(name="observacion")
	private String observacion;

	@OneToMany(mappedBy="movimiento")
	private List<MovimientoIngreso> movimientosIngreso;
	
	@OneToMany(mappedBy="movimiento")
	private List<MovimientoDocumento> documentos;
	
	/**
	 * @return the idMovimiento
	 */
	public Integer getIdMovimiento() {
		return idMovimiento;
	}

	/**
	 * @param idMovimiento the idMovimiento to set
	 */
	public void setIdMovimiento(Integer idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * @return the tipo
	 */
	public TipoMovimientoEnum getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoMovimientoEnum tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the proveedor
	 */
	public Proveedor getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the cantidad
	 */
	public Double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the valorUnidad
	 */
	public Integer getValorUnidad() {
		return valorUnidad;
	}

	/**
	 * @param valorUnidad the valorUnidad to set
	 */
	public void setValorUnidad(Integer valorUnidad) {
		this.valorUnidad = valorUnidad;
	}

	/**
	 * @return the movimientosIngreso
	 */
	public List<MovimientoIngreso> getMovimientosIngreso() {
		return movimientosIngreso;
	}

	/**
	 * @param movimientosIngreso the movimientosIngreso to set
	 */
	public void setMovimientosIngreso(List<MovimientoIngreso> movimientosIngreso) {
		this.movimientosIngreso = movimientosIngreso;
	}

	/**
	 * @return the documentos
	 */
	public List<MovimientoDocumento> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(List<MovimientoDocumento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}
