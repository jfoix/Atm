package cl.jfoix.atm.login.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cl.jfoix.atm.comun.seguridad.modelo.Rol;
import cl.jfoix.atm.comun.seguridad.modelo.Usuario;

@Entity
@Table(name="permiso")
public class Permiso extends Rol implements Serializable {

	private static final long serialVersionUID = -2041104840701184394L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idPermiso")
	private Integer idPermiso;
	
	@ManyToOne
	@JoinColumn(name="idPerfil")
	private Perfil perfil;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="codigo")
	private String codigo;
	
	/**
	 * @return the idPermiso
	 */
	public Integer getIdPermiso() {
		return idPermiso;
	}

	/**
	 * @param idPermiso the idPermiso to set
	 */
	public void setIdPermiso(Integer idPermiso) {
		this.idPermiso = idPermiso;
	}

	/**
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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

	@Override
	public List<Usuario> getUsuarios() {
		return null;
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
}
