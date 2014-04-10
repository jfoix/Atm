package cl.jfoix.atm.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.admin.service.IPersonaService;
import cl.jfoix.atm.comun.util.ValidacionesUtil;
import cl.jfoix.atm.login.entity.Persona;

@ViewScoped
@ManagedBean(name="personaMB")
public class PersonaMB implements Serializable {

	private static final long serialVersionUID = 5548637555636438747L;

	@ManagedProperty(value="#{personaService}")
	private IPersonaService personaService;
	
	private Persona persona;
	private List<Persona> personas;
	
	private boolean modificarPersona;
	
	private String nombre;
	
	@PostConstruct
	public void init(){
		personas = personaService.buscarTodasPersonas();
	}
	
	public void nuevaPersona(){
		modificarPersona = false;
		persona = new Persona();
	}
	
	public void buscarPersona(){
		try {
			personas = personaService.buscarPersonaPorNombre(nombre);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void modPersona(){
		modificarPersona = true;
	}
	
	public void eliminarPersona(){
		try {
			persona.setEstado(false);
			personaService.guardarPersona(persona);
			personas.remove(persona);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void guardarPersona(){
		try {
			
			List<String> mensajes = new ArrayList<String>();
			
			if(persona.getRut().equals("")){
				mensajes.add("Debe ingresar el RUT de la persona");
			} else if(!ValidacionesUtil.validarFormatoRUT(persona.getRut())){
				mensajes.add("El formato de RUT ingresado es inválido");
			} else if(!ValidacionesUtil.validarRut(Integer.parseInt(persona.getRut().split("-")[0]), persona.getRut().split("-")[1].charAt(0))){
				mensajes.add("El RUT ingresado es inválido");
			}
			
			if(persona.getNombres().equals("")){
				mensajes.add("Debe los nombres de la persona");
			}
			
			if(persona.getApellidos().equals("")){
				mensajes.add("Debe los apellidos de la persona");
			}
			
			if(persona.getFechaNacimiento() == null){
				mensajes.add("Debe ingresar la fecha de nacimiento");
			}
			
			if(persona.getFechaContrato() == null){
				mensajes.add("Debe ingresar la fecha de contrato");
			}
			
			if(mensajes.size() > 0){
				
				for(String mensaje : mensajes){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errores", mensaje));
				}
			} else {
			
				personaService.guardarPersona(persona);
				
				String msg = "Modificado con éxito";
				
				if(!modificarPersona){
					if(personas == null){
						personas = new ArrayList<Persona>();
					}
					
					personas.add(persona);
					
					msg = "Ingresado con éxito";
				}
				
				modificarPersona = false;
				
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
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
	 * @return the personas
	 */
	public List<Persona> getPersonas() {
		return personas;
	}

	/**
	 * @param personas the personas to set
	 */
	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	/**
	 * @return the modificarPersona
	 */
	public boolean isModificarPersona() {
		return modificarPersona;
	}

	/**
	 * @param modificarPersona the modificarPersona to set
	 */
	public void setModificarPersona(boolean modificarPersona) {
		this.modificarPersona = modificarPersona;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
