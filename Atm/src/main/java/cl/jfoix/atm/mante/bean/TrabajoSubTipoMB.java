package cl.jfoix.atm.mante.bean;

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

import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.ITrabajoSubTipoService;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;

@ViewScoped
@ManagedBean(name="trabajoSubTipoMB")
public class TrabajoSubTipoMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{trabajoSubTipoService}")
	private ITrabajoSubTipoService trabajoSubTipoService;
	
	@ManagedProperty(value="#{trabajoTipoService}")
	private ITrabajoTipoService trabajoTipoService;
	
	private String subTipoDesc;
	private Integer idTrabajoTipo;
	
	private TrabajoSubTipo trabajoSubTipo;
	
	private List<TrabajoSubTipo> subTipos;
	private List<TrabajoTipo> trabajoTipos;
	
	@PostConstruct
	public void init(){
		trabajoTipos = trabajoTipoService.buscarTodosTrabajosTipo();
		
		trabajoSubTipo = new TrabajoSubTipo();
		trabajoSubTipo.setTrabajoTipo(new TrabajoTipo());
	}
	
	public void nuevoTrabajoSubTipo(){
		trabajoSubTipo = new TrabajoSubTipo();
		trabajoSubTipo.setTrabajoTipo(new TrabajoTipo());
		trabajoSubTipo.setEstado(true);
	}
	
	public void buscarTrabajosSubTipo(){
		try{
			subTipos = trabajoSubTipoService.buscarTrabajoSubTipoPorDescripcionIdTrabajoTipo(subTipoDesc, idTrabajoTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarTrabajoSubTipo() throws ViewException{
			
		ViewException vEx = new ViewException();
		
		if(trabajoSubTipo.getDescripcion() == null || trabajoSubTipo.getDescripcion().equals("")){
			vEx.agregarMensaje("Debe ingresar una descripción para el Sub-Tipo");
		}
		
		if(trabajoSubTipo.getTrabajoTipo().getIdTrabajoTipo() == null || trabajoSubTipo.getTrabajoTipo().getIdTrabajoTipo().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar el Tipo del trabajo");
		}

		if(vEx.tieneMensajes()){
			throw vEx;
		}
			
		try {
			
			String msg = "";

			if(subTipos == null){
				subTipos = new ArrayList<TrabajoSubTipo>();
			}
			
			if(trabajoSubTipo.getIdTrabajoSubTipo() == null){
				subTipos.add(trabajoSubTipo);
				msg = "Ingresado con éxito";
			} else {
				msg = "Modificado con éxito";
			}
			
			trabajoSubTipoService.guardarTrabajoSubTipo(trabajoSubTipo);
			
			for(TrabajoTipo tt : trabajoTipos){
				if(tt.getIdTrabajoTipo().equals(trabajoSubTipo.getTrabajoTipo().getIdTrabajoTipo())){
					trabajoSubTipo.getTrabajoTipo().setDescripcion(tt.getDescripcion());
					break;
				}
			}
			
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarTrabajoSubTipo(){
		try {
			trabajoSubTipo.setEstado(false);
			trabajoSubTipoService.guardarTrabajoSubTipo(trabajoSubTipo);
			subTipos.remove(trabajoSubTipo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
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
	 * @return the subTipoDesc
	 */
	public String getSubTipoDesc() {
		return subTipoDesc;
	}

	/**
	 * @param subTipoDesc the subTipoDesc to set
	 */
	public void setSubTipoDesc(String subTipoDesc) {
		this.subTipoDesc = subTipoDesc;
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
	 * @return the trabajoSubTipo
	 */
	public TrabajoSubTipo getTrabajoSubTipo() {
		return trabajoSubTipo;
	}

	/**
	 * @param trabajoSubTipo the trabajoSubTipo to set
	 */
	public void setTrabajoSubTipo(TrabajoSubTipo trabajoSubTipo) {
		this.trabajoSubTipo = trabajoSubTipo;
	}

	/**
	 * @return the subTipos
	 */
	public List<TrabajoSubTipo> getSubTipos() {
		return subTipos;
	}

	/**
	 * @param subTipos the subTipos to set
	 */
	public void setSubTipos(List<TrabajoSubTipo> subTipos) {
		this.subTipos = subTipos;
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
}
