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
import javax.persistence.Transient;

@Entity
@Table(name="orden_documento")
public class OrdenDocumento implements Serializable {

	private static final long serialVersionUID = -4039183107661656372L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idOrdenDocumento")
	private Integer idOrdenDocumento;

	@ManyToOne
	@JoinColumn(name="idOrden")
	private Orden orden;
	
	@Column(name="rutaNombre")
	private String rutaNombre;
	
	@Transient
	private byte[] datosArchivo;
	
	@Transient
	private String nombreArchivo;
	
	/**
	 * @return the idOrdenDocumento
	 */
	public Integer getIdOrdenDocumento() {
		return idOrdenDocumento;
	}

	/**
	 * @param idOrdenDocumento the idOrdenDocumento to set
	 */
	public void setIdOrdenDocumento(Integer idOrdenDocumento) {
		this.idOrdenDocumento = idOrdenDocumento;
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

	/**
	 * @return the datosArchivo
	 */
	public byte[] getDatosArchivo() {
		return datosArchivo;
	}

	/**
	 * @param datosArchivo the datosArchivo to set
	 */
	public void setDatosArchivo(byte[] datosArchivo) {
		this.datosArchivo = datosArchivo;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
}