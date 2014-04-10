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
@Table(name="vehiculo")
public class Vehiculo implements Serializable {

	private static final long serialVersionUID = -8344902913568353303L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idVehiculo")
	private Integer idVehiculo;

	@ManyToOne
	@JoinColumn(name="idMarcaVehiculo")
	private MarcaVehiculo marcaVehiculo;
	
	@Column(name="patente")
	private String patente;
	
	@Column(name="chasis")
	private String chasis;
	
	@Column(name="color")
	private String color;
	
	@Column(name="acno")
	private Integer acno;
	
	@Column(name="modelo")
	private String modelo;
	
	@Column(name="nroMotor")
	private String nroMotor;

	/**
	 * @return the idVehiculo
	 */
	public Integer getIdVehiculo() {
		return idVehiculo;
	}

	/**
	 * @param idVehiculo the idVehiculo to set
	 */
	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	/**
	 * @return the marcaVehiculo
	 */
	public MarcaVehiculo getMarcaVehiculo() {
		return marcaVehiculo;
	}

	/**
	 * @param marcaVehiculo the marcaVehiculo to set
	 */
	public void setMarcaVehiculo(MarcaVehiculo marcaVehiculo) {
		this.marcaVehiculo = marcaVehiculo;
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

	/**
	 * @return the chasis
	 */
	public String getChasis() {
		return chasis;
	}

	/**
	 * @param chasis the chasis to set
	 */
	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the nroMotor
	 */
	public String getNroMotor() {
		return nroMotor;
	}

	/**
	 * @param nroMotor the nroMotor to set
	 */
	public void setNroMotor(String nroMotor) {
		this.nroMotor = nroMotor;
	}

	/**
	 * @return the acno
	 */
	public Integer getAcno() {
		return acno;
	}

	/**
	 * @param acno the acno to set
	 */
	public void setAcno(Integer acno) {
		this.acno = acno;
	}
}
