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

import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IBodegaService;
import cl.jfoix.atm.ot.entity.Bodega;

@ViewScoped
@ManagedBean(name="bodegaMB")
public class BodegaMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{bodegaService}")
	private IBodegaService bodegaService;
	
	private String nombreBodega;
	private Bodega bodega;
	
	private List<Bodega> bodegas;
	
	public void nuevaBodega(){
		this.bodega = new Bodega();
		this.bodega.setEstado(true);
	}
	
	public void buscarBodegas(){
		try{
			bodegas = bodegaService.buscarBodegasPorDescripcion(nombreBodega);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarBodega() throws ViewException {
		
		ViewException vEx = new ViewException();
		
		if(this.bodega.getDescripcion() == null || this.bodega.getDescripcion().equals("")){
			vEx.agregarMensaje("Debe ingresar una descripción para la bodega");
		}
		
		if(this.bodega.getDireccion() == null || this.bodega.getDireccion().equals("")){
			vEx.agregarMensaje("Debe ingresar una dirección para la bodega");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		try {
			String msg = "";
			
			if(bodegas == null){
				bodegas = new ArrayList<Bodega>();
			}
			
			if(this.bodega.getIdBodega() == null){
				bodegas.add(bodega);
				
				msg = "Ingresado con éxito";
			} else {
				msg = "Modificado con éxito";
			}
			
			bodegaService.guardarBodega(bodega);
			
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarBodega(){
		try {
			this.bodega.setEstado(false);
			this.bodegaService.guardarBodega(bodega);
			this.bodegas.remove(bodega);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	/**
	 * @return the bodegaService
	 */
	public IBodegaService getBodegaService() {
		return bodegaService;
	}

	/**
	 * @param bodegaService the bodegaService to set
	 */
	public void setBodegaService(IBodegaService bodegaService) {
		this.bodegaService = bodegaService;
	}

	/**
	 * @return the nombreBodega
	 */
	public String getNombreBodega() {
		return nombreBodega;
	}

	/**
	 * @param nombreBodega the nombreBodega to set
	 */
	public void setNombreBodega(String nombreBodega) {
		this.nombreBodega = nombreBodega;
	}

	/**
	 * @return the bodega
	 */
	public Bodega getBodega() {
		return bodega;
	}

	/**
	 * @param bodega the bodega to set
	 */
	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	/**
	 * @return the bodegas
	 */
	public List<Bodega> getBodegas() {
		return bodegas;
	}

	/**
	 * @param bodegas the bodegas to set
	 */
	public void setBodegas(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}
}
