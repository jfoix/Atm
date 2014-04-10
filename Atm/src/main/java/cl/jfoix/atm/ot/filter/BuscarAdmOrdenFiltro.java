package cl.jfoix.atm.ot.filter;

import java.io.Serializable;
import java.util.Date;

public class BuscarAdmOrdenFiltro implements Serializable {

	private static final long serialVersionUID = 7070075978831680192L;

	private Integer idOrden;
	private String idMecanico;
	private Integer idEstado;
	private Date fechaInicio;
	private Date fechaTermino;
	private String patente;
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
	 * @return the idMecanico
	 */
	public String getIdMecanico() {
		return idMecanico;
	}
	/**
	 * @param idMecanico the idMecanico to set
	 */
	public void setIdMecanico(String idMecanico) {
		this.idMecanico = idMecanico;
	}
	/**
	 * @return the idEstado
	 */
	public Integer getIdEstado() {
		return idEstado;
	}
	/**
	 * @param idEstado the idEstado to set
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
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
	 * @return the patente
	 */
	public String getPatente() {
		return patente;
	}
	/**
	 * @param patente the patente to set
	 */
	public void setPatente(String patente) {
		this.patente = patente;
	}
	
}