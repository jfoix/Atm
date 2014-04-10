package cl.jfoix.atm.mante.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;

@ViewScoped
@ManagedBean(name="trabajoTipoMB")
public class TrabajoTipoMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{trabajoTipoService}")
	private ITrabajoTipoService trabajoTipoService;
	
	private String tipoDesc;
	private TrabajoTipo trabajoTipo;
	
	private List<TrabajoTipo> tipos;
	
	public void nuevoTrabajoTipo(){
		trabajoTipo = new TrabajoTipo();
		trabajoTipo.setEstado(true);
	}
	
	public void buscarTrabajoTipo(){
		try{
			tipos = trabajoTipoService.buscarTrabajoTipoPorDescripcion(tipoDesc);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarTrabajoTipo(){
		try {
			if(trabajoTipo.getDescripcion() == null || trabajoTipo.getDescripcion().equals("")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Mensaje", "Debe ingresar una descripción para el Tipo de Trabajo"));
			} else {
				String msg = "";

				if(tipos == null){
					tipos = new ArrayList<TrabajoTipo>();
				}
				
				if(trabajoTipo.getIdTrabajoTipo() == null){
					
					tipos.add(trabajoTipo);
					
					msg = "Ingresado con éxito";
				} else {
					msg = "Modificado con éxito";
				}
				
				trabajoTipoService.guardarTrabajoTipo(trabajoTipo);
				
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarTrabajoTipo(){
		try {
			trabajoTipo.setEstado(false);
			trabajoTipoService.guardarTrabajoTipo(trabajoTipo);
			tipos.remove(trabajoTipo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
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
	 * @return the tipoDesc
	 */
	public String getTipoDesc() {
		return tipoDesc;
	}

	/**
	 * @param tipoDesc the tipoDesc to set
	 */
	public void setTipoDesc(String tipoDesc) {
		this.tipoDesc = tipoDesc;
	}

	/**
	 * @return the trabajoTipo
	 */
	public TrabajoTipo getTrabajoTipo() {
		return trabajoTipo;
	}

	/**
	 * @param trabajoTipo the trabajoTipo to set
	 */
	public void setTrabajoTipo(TrabajoTipo trabajoTipo) {
		this.trabajoTipo = trabajoTipo;
	}

	/**
	 * @return the tipos
	 */
	public List<TrabajoTipo> getTipos() {
		return tipos;
	}

	/**
	 * @param tipos the tipos to set
	 */
	public void setTipos(List<TrabajoTipo> tipos) {
		this.tipos = tipos;
	}
}
