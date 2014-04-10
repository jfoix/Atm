package cl.jfoix.atm.comun.seguridad.proveedor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cl.jfoix.atm.comun.excepcion.handler.ComunExceptionHandler;
import cl.jfoix.atm.comun.seguridad.modelo.Rol;
import cl.jfoix.atm.comun.seguridad.modelo.Usuario;
import cl.jfoix.atm.comun.seguridad.services.ISeguridadService;

public class ComunAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private ISeguridadService seguridadService;
	
	@SuppressWarnings({ "serial", "deprecation" })
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsuarioAtenticacion auth = null;
		if(authentication!=null){
			Usuario usuario = seguridadService.obtenerUsuario(authentication.getName(), authentication.getCredentials().toString());
			if(usuario!=null && usuario.getNombreUsuarioSession().equals(authentication.getName())){
				auth = new UsuarioAtenticacion();
				auth.setAuthenticated(true);
				auth.setUsuario(usuario);
				auth.setName(usuario.getNombreUsuarioSession());
				auth.setCredentials(usuario.getClaveSession());
				auth.setPrincipal(usuario);
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				for(Rol rol: usuario.getRoles()){
					authorities.add(new SimpleGrantedAuthority(rol.getCodigo()));
				}
				auth.setAuthorities(authorities);
			}else{
				FacesContext fc = FacesContext.getCurrentInstance();
				throw new AuthenticationException(ComunExceptionHandler.getMessageSource().getMessage("login.mensajeError", new Object[0], fc.getViewRoot().getLocale())) {};
			}
		}
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public ISeguridadService getSeguridadService() {
		return seguridadService;
	}

	public void setSeguridadService(ISeguridadService seguridadService) {
		this.seguridadService = seguridadService;
	}

}
