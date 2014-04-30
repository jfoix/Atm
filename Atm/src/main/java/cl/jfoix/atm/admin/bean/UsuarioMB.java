package cl.jfoix.atm.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import cl.jfoix.atm.admin.service.IPersonaService;
import cl.jfoix.atm.admin.service.IUsuarioService;
import cl.jfoix.atm.admin.util.EstadoUsuarioEnum;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IPerfilService;
import cl.jfoix.atm.comun.service.IPermisoService;
import cl.jfoix.atm.login.entity.Perfil;
import cl.jfoix.atm.login.entity.Permiso;
import cl.jfoix.atm.login.entity.Persona;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.login.entity.UsuarioPermiso;
import cl.jfoix.atm.login.entity.UsuarioPermisoPK;

@ViewScoped
@ManagedBean(name="usuarioMB")
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 5548637555636438747L;

	@ManagedProperty(value="#{usuarioService}")
	private IUsuarioService usuarioService;
	
	@ManagedProperty(value="#{personaService}")
	private IPersonaService personaService;
	
	@ManagedProperty(value="#{perfilService}")
	private IPerfilService perfilService;
	
	@ManagedProperty(value="#{permisoService}")
	private IPermisoService permisoService;
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	
	private List<Perfil> perfiles;
	private Integer perfilId;
	
	private DualListModel<Permiso> permisos; 
	private List<Permiso> permisosUsuario;
	
	private boolean actualizarUsuario;
	
	private String nombreUsuario;
	private Integer estadoUsuario;
	
	@PostConstruct
	public void init(){
		usuarios = usuarioService.buscarTodosUsuarios();
		usuario = new Usuario();
		usuario.setPersona(new Persona());
		perfiles = perfilService.buscarTodosPerfiles();
		
		List<Permiso> source = new ArrayList<Permiso>(); 
		List<Permiso> target = new ArrayList<Permiso>(); 
		permisos = new DualListModel<Permiso>(source, target);
		
		permisosUsuario = new ArrayList<Permiso>();
	}

	public void buscarUsuarios(){
		try{
			if(estadoUsuario.equals(0)){
				usuarios = usuarioService.buscarUsuarios(nombreUsuario, EstadoUsuarioEnum.ACTIVO);
			} else if(estadoUsuario.equals(1)){
				usuarios = usuarioService.buscarUsuarios(nombreUsuario, EstadoUsuarioEnum.INACTIVO);
			} else {
				usuarios = usuarioService.buscarUsuarios(nombreUsuario);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void nuevoUsuario(){
		usuario = new Usuario();
		usuario.setEstado(EstadoUsuarioEnum.ACTIVO);
		usuario.setFechaIngreso(new Date());
		usuario.setPersona(new Persona());
		actualizarUsuario = false;
		
		perfilId = null;
		List<Permiso> source = new ArrayList<Permiso>(); 
		List<Permiso> target = new ArrayList<Permiso>(); 
		permisos = new DualListModel<Permiso>(source, target);
		
		permisosUsuario = new ArrayList<Permiso>();
	}
	
	public void cambiarEstado(){
		try {
			usuario.setEstado(usuario.getEstado().equals(EstadoUsuarioEnum.ACTIVO) ? EstadoUsuarioEnum.INACTIVO : EstadoUsuarioEnum.ACTIVO);
			usuarioService.guardarUsuario(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Modificado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void buscarPersona(){
		
		String rutTemp = usuario.getPersona().getRut();
		Persona persona = personaService.buscarPersonaPorRut(usuario.getPersona().getRut());
		
		if(persona != null){
			usuario.setPersona(persona);
		} else {
			persona = new Persona();
			persona.setRut(rutTemp);
			usuario.setPersona(persona);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Persona no encontrada"));
		}
	}

	public void buscarPermisosPerfil(){
		
		if(permisos != null && permisos.getTarget() != null && permisos.getTarget().size() > 0){
			for(Permiso permiso : permisos.getTarget()){
				permisosUsuario.add(permiso);
			}
		}
		
		if(perfilId == null || perfilId.equals(-1)){
			List<Permiso> source = new ArrayList<Permiso>(); 
			List<Permiso> target = new ArrayList<Permiso>(); 
			permisos = new DualListModel<Permiso>(source, target);
		} else {
			List<Permiso> source = permisoService.obtenerPermisosPorIdPerfil(perfilId); 
			List<Permiso> target = new ArrayList<Permiso>();
			
			if(permisosUsuario != null){
				List<Permiso> targetUsuario = new ArrayList<Permiso>();
				for(Permiso permiso : permisosUsuario){
					if(permiso.getPerfil() != null && permiso.getPerfil().getIdPerfil().equals(perfilId)){
						targetUsuario.add(permiso);
					}
				}
				
				target = targetUsuario;
				
				permisosUsuario.removeAll(target);
				
				List<Permiso> sourceUsuario = new ArrayList<Permiso>();
				
				for(Permiso permiso : target){
					for(Permiso perSource : source){
						if(permiso.getIdPermiso().equals(perSource.getIdPermiso())){
							sourceUsuario.add(perSource);
							break;
						}
					}
				}
				
				source.removeAll(sourceUsuario);
			}
			
			permisos = new DualListModel<Permiso>(source, target);
		}
	}
	
	public void eliminarUsuario(){
		try {
			usuario.setEstado(EstadoUsuarioEnum.ELIMINADO);
			usuarioService.guardarUsuario(usuario);
			usuarios.remove(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void seleccionarUsuario(){
		actualizarUsuario = true;
		
		permisosUsuario = new ArrayList<Permiso>();
		
		if(usuario.getUsuarioPermisos() != null){
			for(UsuarioPermiso usuarioPermiso : usuario.getUsuarioPermisos()){
				permisosUsuario.add(usuarioPermiso.getPermiso());
			}
		}
		
		perfilId = null;
		List<Permiso> source = new ArrayList<Permiso>();
		List<Permiso> target = new ArrayList<Permiso>();
		
		permisos = new DualListModel<Permiso>(source, target);
	}
	
	private void validarUsuario() throws ViewException{
		
		ViewException vEx = new ViewException();
		
		if(!actualizarUsuario && usuario.getNombreUsuario() == ""){
			vEx.agregarMensaje("Debe ingresar un Nombre de Usuario");
		} else if(!actualizarUsuario) {
			try {
				Usuario userDB = usuarioService.buscarUsuario(usuario.getNombreUsuario());
				if(userDB != null){
					vEx.agregarMensaje("El Nombre de Usuario ingresado ya se encuentra registrado, intente con otro");
				}
			} catch (Exception e) { }
			
		}
		
		if(usuario.getClave().equals("")){
			vEx.agregarMensaje("Debe ingresar una Clave");
		}
		
		if(usuario.getClaveRep().equals("")){
			vEx.agregarMensaje("Debe repetir su Clave");
		}
		
		if(!usuario.getClave().equals("") && !usuario.getClaveRep().equals("")){
			if(!usuario.getClave().equals(usuario.getClaveRep())){
				vEx.agregarMensaje("Las Claves ingresadas no concuerdan");
			}
		}

		if(usuario.getPersona().getIdPersona() == null){
			vEx.agregarMensaje("Debe asociar una Persona al Usuario");
		}
		
		if(permisosUsuario == null || permisosUsuario.size() == 0){
			vEx.agregarMensaje("Debe agregar al menos un permiso al Usuario");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
	}
	
	public void guardarUsuario() throws ViewException{
		
		if(permisos != null && permisos.getTarget() != null && permisos.getTarget().size() > 0){
			for(Permiso permiso : permisos.getTarget()){
				permisosUsuario.add(permiso);
			}
		}
		
		validarUsuario();

		try {
			
			List<UsuarioPermiso> usuarioPermisos = new ArrayList<UsuarioPermiso>();
			
			for(Permiso permiso : permisosUsuario){
				UsuarioPermisoPK pk = new UsuarioPermisoPK();
				pk.setNombreUsuario(usuario.getNombreUsuario());
				pk.setIdPermiso(permiso.getIdPermiso());
				UsuarioPermiso usuarioPermiso = new UsuarioPermiso();
				usuarioPermiso.setPk(pk);
				usuarioPermiso.setUsuario(usuario);
				usuarioPermiso.setPermiso(permiso);
				
				usuarioPermisos.add(usuarioPermiso);
			}
			
			usuario.setUsuarioPermisos(usuarioPermisos);
			
			if(!actualizarUsuario){
				
				UsuarioPermisoPK pk = new UsuarioPermisoPK();
				pk.setNombreUsuario(usuario.getNombreUsuario());
				pk.setIdPermiso(7);
				UsuarioPermiso usuarioPermiso = new UsuarioPermiso();
				usuarioPermiso.setPk(pk);
				usuarioPermiso.setUsuario(usuario);
				
				usuarioPermisos.add(usuarioPermiso);
				
				usuarioService.guardarUsuario(usuario);
				usuarios.add(usuario);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Ingresado con éxito"));
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
			} else {
				usuarioService.guardarUsuario(usuario);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Modificado con éxito"));
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
		
		actualizarUsuario = false;
	}

	/**
	 * @return the usuarioService
	 */
	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
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
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the actualizarUsuario
	 */
	public boolean isActualizarUsuario() {
		return actualizarUsuario;
	}

	/**
	 * @param actualizarUsuario the actualizarUsuario to set
	 */
	public void setActualizarUsuario(boolean actualizarUsuario) {
		this.actualizarUsuario = actualizarUsuario;
	}

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
	 * @return the estadoUsuario
	 */
	public Integer getEstadoUsuario() {
		return estadoUsuario;
	}

	/**
	 * @param estadoUsuario the estadoUsuario to set
	 */
	public void setEstadoUsuario(Integer estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	/**
	 * @return the personaService
	 */
	public IPersonaService getPersonaService() {
		return personaService;
	}

	/**
	 * @param personaService the personaService to set
	 */
	public void setPersonaService(IPersonaService personaService) {
		this.personaService = personaService;
	}

	/**
	 * @return the perfilService
	 */
	public IPerfilService getPerfilService() {
		return perfilService;
	}

	/**
	 * @param perfilService the perfilService to set
	 */
	public void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}

	/**
	 * @return the perfiles
	 */
	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	/**
	 * @param perfiles the perfiles to set
	 */
	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	/**
	 * @return the perfilId
	 */
	public Integer getPerfilId() {
		return perfilId;
	}

	/**
	 * @param perfilId the perfilId to set
	 */
	public void setPerfilId(Integer perfilId) {
		this.perfilId = perfilId;
	}

	/**
	 * @return the permisos
	 */
	public DualListModel<Permiso> getPermisos() {
		return permisos;
	}

	/**
	 * @param permisos the permisos to set
	 */
	public void setPermisos(DualListModel<Permiso> permisos) {
		this.permisos = permisos;
	}

	/**
	 * @return the permisoService
	 */
	public IPermisoService getPermisoService() {
		return permisoService;
	}

	/**
	 * @param permisoService the permisoService to set
	 */
	public void setPermisoService(IPermisoService permisoService) {
		this.permisoService = permisoService;
	}
}
