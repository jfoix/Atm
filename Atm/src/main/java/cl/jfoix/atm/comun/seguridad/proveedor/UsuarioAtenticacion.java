package cl.jfoix.atm.comun.seguridad.proveedor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import cl.jfoix.atm.comun.seguridad.modelo.Rol;
import cl.jfoix.atm.comun.seguridad.modelo.Usuario;


public class UsuarioAtenticacion implements Authentication{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<GrantedAuthority> authorities;
	private Object credentials;
	private Object details;
	private Object principal;
	private boolean authenticated;
	private Usuario usuario;
	
	public boolean isRolesHabilitados(String...roles){
		boolean tieneRol = true;
		if(roles!=null && roles.length>0){
			for(String codigoRol: roles){
				Collections.sort(this.usuario.getRoles());
				@SuppressWarnings("serial")
				Rol rol = new Rol(codigoRol) {
					@Override
					public List<Usuario> getUsuarios() {
						return null;
					}
				};
				int index = Collections.binarySearch(this.usuario.getRoles(), rol);
				if(index<0){
					tieneRol=false;
					break;
				}
			}
		}
		return tieneRol;
	}
	
	public boolean isAlgunRolHabilitado(String...roles){
		if(roles!=null && roles.length>0){
			for(String codigoRol: roles){
				
				for(Rol rol : this.usuario.getRoles()){
					if(rol.getCodigo().equals(codigoRol)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<GrantedAuthority> auths){
		this.authorities = auths;
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}
	public void setCredentials(Object credentials){
		this.credentials = credentials;
	}
	
	@Override
	public Object getDetails() {
		return details;
	}
	@Override
	public Object getPrincipal() {
		return principal;
	}
	
	public void setDetails(Object details) {
		this.details= details;
	}
	public void setPrincipal(Object principal) {
		this.principal = principal ;
	}
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}
	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated = authenticated;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
