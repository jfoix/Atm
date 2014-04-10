package cl.jfoix.atm.login.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cl.jfoix.atm.admin.util.EstadoUsuarioEnum;
import cl.jfoix.atm.comun.seguridad.modelo.Rol;

@Entity
@Table(name="usuario")
public class Usuario extends cl.jfoix.atm.comun.seguridad.modelo.Usuario implements Serializable{

	private static final long serialVersionUID = 8605651064893322726L;

	@Id
	@Column(name="nombreUsuario")
	private String nombreUsuario;
	
	@Column(name="clave")
	private String clave;
	
	@Column(name="fechaIngreso")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIngreso;
	
	@Column(name="estado")
	@Enumerated(EnumType.ORDINAL)
	private EstadoUsuarioEnum estado;

	@Transient
	private String claveRep;
	
	@ManyToOne
	@JoinColumn(name="idPersona")
	private Persona persona;
	
	@OneToMany(mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true)
	List<UsuarioPermiso> usuarioPermisos;

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the estado
	 */
	public EstadoUsuarioEnum getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoUsuarioEnum estado) {
		this.estado = estado;
	}

	/**
	 * @return the claveRep
	 */
	public String getClaveRep() {
		return claveRep;
	}

	/**
	 * @param claveRep the claveRep to set
	 */
	public void setClaveRep(String claveRep) {
		this.claveRep = claveRep;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the usuarioPermisos
	 */
	public List<UsuarioPermiso> getUsuarioPermisos() {
		return usuarioPermisos;
	}

	/**
	 * @param usuarioPermisos the usuarioPermisos to set
	 */
	public void setUsuarioPermisos(List<UsuarioPermiso> usuarioPermisos) {
		this.usuarioPermisos = usuarioPermisos;
	}

	@Override
	public List<Rol> getRoles() {
		
		List<Rol> roles = new ArrayList<Rol>();
		
		for(UsuarioPermiso uPermiso : usuarioPermisos){
			roles.add(uPermiso.getPermiso());
		}
		
		return roles;
	}

	@Override
	public String getNombreUsuarioSession() {
		return nombreUsuario;
	}

	@Override
	public String getClaveSession() {
		return clave;
	}
}
