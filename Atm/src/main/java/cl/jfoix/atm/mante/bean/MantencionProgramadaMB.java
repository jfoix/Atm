package cl.jfoix.atm.mante.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.comun.entity.MantencionProgramada;
import cl.jfoix.atm.comun.entity.MantencionProgramadaTrabajo;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.service.IMantencionProgramadaService;
import cl.jfoix.atm.comun.service.ITrabajoService;
import cl.jfoix.atm.comun.service.ITrabajoSubTipoService;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;

@ViewScoped
@ManagedBean(name="mantencionProgramadaMB")
public class MantencionProgramadaMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{trabajoService}")
	private ITrabajoService trabajoService;
	
	@ManagedProperty(value="#{trabajoSubTipoService}")
	private ITrabajoSubTipoService trabajoSubTipoService;
	
	@ManagedProperty(value="#{mantencionProgramadaService}")
	private IMantencionProgramadaService mantencionProgramadaService;
	
	@ManagedProperty(value="#{trabajoTipoService}")
	private ITrabajoTipoService trabajoTipoService;

	private String mpDesc;
	private String mpCode;
	
	private String trabajoDesc;
	private String trabajoCode;
	private Integer idTrabajoTipo;
	private Integer idTrabajoSubTipo;
	
	private Trabajo trabajo;
	private MantencionProgramada mantencionProgramada;
	
	private List<Trabajo> trabajos;
	private List<Trabajo> trabajosMantencion;
	private List<TrabajoTipo> trabajoTipos;
	private List<TrabajoSubTipo> trabajoSubTipos;
	
	private List<MantencionProgramada> mantencionesProgramadas;
	
	@PostConstruct
	public void init(){
		trabajoTipos = trabajoTipoService.buscarTodosTrabajosTipo();
		mantencionesProgramadas = mantencionProgramadaService.buscarMantencionesProgramadas();
	}
	
	public void nuevoMantencionProgramada(){
		mantencionProgramada = new MantencionProgramada();
		trabajosMantencion = new ArrayList<Trabajo>();
	}
	
	public void buscarMantenciones(){
		try{
			mantencionesProgramadas = mantencionProgramadaService.buscarMantencionProgramadaPorCodigoDescripcion(mpCode, mpDesc);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void editarMantencion(){
		try{
			if(mantencionProgramada.getMantencionTrabajos() != null){
				trabajosMantencion = new ArrayList<Trabajo>();
				for(MantencionProgramadaTrabajo mpt : mantencionProgramada.getMantencionTrabajos()){
					trabajosMantencion.add(mpt.getTrabajo());
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void abrirBusquedaTrabajo(){
		trabajoDesc = null;
		trabajoCode = null;
		idTrabajoTipo = null;
		idTrabajoSubTipo = null;
		trabajos = new ArrayList<Trabajo>();
	}
	
	public void buscarTrabajo(){
		try{
			trabajos = trabajoService.buscarTrabajoPorDescripcionCodigo(trabajoDesc, trabajoCode, idTrabajoTipo, idTrabajoSubTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void agregarTrabajos(){
		if(trabajos != null){
			for(Trabajo trabajo : trabajos){
				if(trabajo.isSeleccionado()){
					if(trabajosMantencion == null){
						trabajosMantencion = new ArrayList<Trabajo>();
					}
					
					boolean existe = false;
					for(Trabajo tra : trabajosMantencion){
						if(tra.getIdTrabajo().equals(trabajo.getIdTrabajo())){
							existe = true;
							break;
						}
					}
					
					if(!existe){
						trabajosMantencion.add(trabajo);
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje", "El Trabajo " + trabajo.getCodigo() + " - " + trabajo.getDescripcion() + " ya se encuentra agregado."));
					}
				}
			}
		}
	}
	
	public void eliminarTrabajo(){
		trabajosMantencion.remove(trabajo);
	}
	
	public void buscarTrabajoSubTipos(AjaxBehaviorEvent event){
		try {
			Integer idTrabajoTipo = (Integer) ((UIOutput)event.getSource()).getValue();
			trabajoSubTipos = trabajoSubTipoService.buscarTrabajoSubTipoPorIdTrabajoTipo(idTrabajoTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarMatencionProgramada(){
		try {
			
			List<FacesMessage> messages = new ArrayList<FacesMessage>();
			
			if(mantencionProgramada.getCodigo() == null || mantencionProgramada.getCodigo().equals("")){
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "Debe ingresar un código para la Mantención Programada"));
			} else if(!mantencionProgramadaService.validarMantencionProgramadaPorCodigo(mantencionProgramada.getIdMantencionProgramada(), mantencionProgramada.getCodigo())){
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "El código ingresado ya se encuentra asociado a otra Mantención Programada"));
			}
			
			if(mantencionProgramada.getDescripcion() == null || mantencionProgramada.getDescripcion().equals("")){
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "Debe ingresar una descripción para la Mantención Programada"));
			}
			
			if(trabajosMantencion == null || trabajosMantencion.size() == 0){
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "Debe ingresar al menos un Trabajo a la Mantención Programada"));
			}
			
			if(messages.size() > 0){
				for(FacesMessage message : messages){
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			} else {
				String msg = "";

				mantencionProgramada.setCodigo(mantencionProgramada.getCodigo().toUpperCase());
				
				List<MantencionProgramadaTrabajo> mpTrabajos = new ArrayList<MantencionProgramadaTrabajo>();
				
				for(Trabajo trabajo : trabajosMantencion){
					MantencionProgramadaTrabajo mpt = new MantencionProgramadaTrabajo();
					mpt.setTrabajo(trabajo);
					mpTrabajos.add(mpt);
				}
				
				mantencionProgramada.setMantencionTrabajos(mpTrabajos);
				
				if(mantencionProgramada.getIdMantencionProgramada() == null){
					msg = "Ingresado con éxito";
				} else {
					msg = "Modificado con éxito";
				}
				
				mantencionProgramadaService.guardarMantencionProgramada(mantencionProgramada);
				
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarMantencion(){
		try {
			mantencionesProgramadas.remove(mantencionProgramada);
			mantencionProgramada = mantencionProgramadaService.buscarMantencionProgramadPorId(mantencionProgramada.getIdMantencionProgramada());
			mantencionProgramada.setEstado(false);
			mantencionProgramadaService.modificarMantencionProgramad(mantencionProgramada);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	/**
	 * @return the trabajoService
	 */
	public ITrabajoService getTrabajoService() {
		return trabajoService;
	}

	/**
	 * @param trabajoService the trabajoService to set
	 */
	public void setTrabajoService(ITrabajoService trabajoService) {
		this.trabajoService = trabajoService;
	}

	/**
	 * @return the trabajoSubTipoService
	 */
	public ITrabajoSubTipoService getTrabajoSubTipoService() {
		return trabajoSubTipoService;
	}

	/**
	 * @param trabajoSubTipoService the trabajoSubTipoService to set
	 */
	public void setTrabajoSubTipoService(
			ITrabajoSubTipoService trabajoSubTipoService) {
		this.trabajoSubTipoService = trabajoSubTipoService;
	}

	/**
	 * @return the mantencionProgramadaService
	 */
	public IMantencionProgramadaService getMantencionProgramadaService() {
		return mantencionProgramadaService;
	}

	/**
	 * @param mantencionProgramadaService the mantencionProgramadaService to set
	 */
	public void setMantencionProgramadaService(
			IMantencionProgramadaService mantencionProgramadaService) {
		this.mantencionProgramadaService = mantencionProgramadaService;
	}

	/**
	 * @return the trabajoTipoService
	 */
	public ITrabajoTipoService getTrabajoTipoService() {
		return trabajoTipoService;
	}

	/**
	 * @param trabajoTipoService the trabajoTipoService to set
	 */
	public void setTrabajoTipoService(ITrabajoTipoService trabajoTipoService) {
		this.trabajoTipoService = trabajoTipoService;
	}

	/**
	 * @return the trabajoDesc
	 */
	public String getTrabajoDesc() {
		return trabajoDesc;
	}

	/**
	 * @param trabajoDesc the trabajoDesc to set
	 */
	public void setTrabajoDesc(String trabajoDesc) {
		this.trabajoDesc = trabajoDesc;
	}

	/**
	 * @return the trabajoCode
	 */
	public String getTrabajoCode() {
		return trabajoCode;
	}

	/**
	 * @param trabajoCode the trabajoCode to set
	 */
	public void setTrabajoCode(String trabajoCode) {
		this.trabajoCode = trabajoCode;
	}

	/**
	 * @return the idTrabajoTipo
	 */
	public Integer getIdTrabajoTipo() {
		return idTrabajoTipo;
	}

	/**
	 * @param idTrabajoTipo the idTrabajoTipo to set
	 */
	public void setIdTrabajoTipo(Integer idTrabajoTipo) {
		this.idTrabajoTipo = idTrabajoTipo;
	}

	/**
	 * @return the idTrabajoSubTipo
	 */
	public Integer getIdTrabajoSubTipo() {
		return idTrabajoSubTipo;
	}

	/**
	 * @param idTrabajoSubTipo the idTrabajoSubTipo to set
	 */
	public void setIdTrabajoSubTipo(Integer idTrabajoSubTipo) {
		this.idTrabajoSubTipo = idTrabajoSubTipo;
	}

	/**
	 * @return the trabajo
	 */
	public Trabajo getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

	/**
	 * @return the mantencionProgramada
	 */
	public MantencionProgramada getMantencionProgramada() {
		return mantencionProgramada;
	}

	/**
	 * @param mantencionProgramada the mantencionProgramada to set
	 */
	public void setMantencionProgramada(MantencionProgramada mantencionProgramada) {
		this.mantencionProgramada = mantencionProgramada;
	}

	/**
	 * @return the trabajos
	 */
	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

	/**
	 * @param trabajos the trabajos to set
	 */
	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	/**
	 * @return the trabajosMantencion
	 */
	public List<Trabajo> getTrabajosMantencion() {
		return trabajosMantencion;
	}

	/**
	 * @param trabajosMantencion the trabajosMantencion to set
	 */
	public void setTrabajosMantencion(List<Trabajo> trabajosMantencion) {
		this.trabajosMantencion = trabajosMantencion;
	}

	/**
	 * @return the trabajoTipos
	 */
	public List<TrabajoTipo> getTrabajoTipos() {
		return trabajoTipos;
	}

	/**
	 * @param trabajoTipos the trabajoTipos to set
	 */
	public void setTrabajoTipos(List<TrabajoTipo> trabajoTipos) {
		this.trabajoTipos = trabajoTipos;
	}

	/**
	 * @return the trabajoSubTipos
	 */
	public List<TrabajoSubTipo> getTrabajoSubTipos() {
		return trabajoSubTipos;
	}

	/**
	 * @param trabajoSubTipos the trabajoSubTipos to set
	 */
	public void setTrabajoSubTipos(List<TrabajoSubTipo> trabajoSubTipos) {
		this.trabajoSubTipos = trabajoSubTipos;
	}

	/**
	 * @return the mantencionesProgramadas
	 */
	public List<MantencionProgramada> getMantencionesProgramadas() {
		return mantencionesProgramadas;
	}

	/**
	 * @param mantencionesProgramadas the mantencionesProgramadas to set
	 */
	public void setMantencionesProgramadas(
			List<MantencionProgramada> mantencionesProgramadas) {
		this.mantencionesProgramadas = mantencionesProgramadas;
	}

	/**
	 * @return the mpDesc
	 */
	public String getMpDesc() {
		return mpDesc;
	}

	/**
	 * @param mpDesc the mpDesc to set
	 */
	public void setMpDesc(String mpDesc) {
		this.mpDesc = mpDesc;
	}

	/**
	 * @return the mpCode
	 */
	public String getMpCode() {
		return mpCode;
	}

	/**
	 * @param mpCode the mpCode to set
	 */
	public void setMpCode(String mpCode) {
		this.mpCode = mpCode;
	}
}
