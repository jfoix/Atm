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

import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.service.IMarcaService;

@ViewScoped
@ManagedBean(name="marcaMB")
public class MarcaMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{marcaService}")
	private IMarcaService marcaService;
	
	private String nombreMarca;
	private Marca marca;
	
	private List<Marca> marcas;
	
	public void nuevaMarca(){
		this.marca = new Marca();
		this.marca.setEstado(true);
	}
	
	public void buscarMarcas(){
		try{
			marcas = marcaService.buscarMarcasPorDescripcion(nombreMarca);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarMarca(){
		try {
			if(this.marca.getDescripcion() == null || this.marca.getDescripcion().equals("")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Mensaje", "Debe ingresar una descripción para la marca"));
			} else {
				String msg = "";
				
				if(marcas == null){
					marcas = new ArrayList<Marca>();
				}
				
				if(this.marca.getIdMarca() == null){
					marcas.add(marca);
					
					msg = "Ingresado con éxito";
				} else {
					msg = "Modificado con éxito";
				}
				
				marcaService.guardarMarca(marca);
				
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarMarca(){
		try {
			this.marca.setEstado(false);
			this.marcaService.guardarMarca(marca);
			this.marcas.remove(marca);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	/**
	 * @return the marcaService
	 */
	public IMarcaService getMarcaService() {
		return marcaService;
	}

	/**
	 * @param marcaService the marcaService to set
	 */
	public void setMarcaService(IMarcaService marcaService) {
		this.marcaService = marcaService;
	}

	/**
	 * @return the nombreMarca
	 */
	public String getNombreMarca() {
		return nombreMarca;
	}

	/**
	 * @param nombreMarca the nombreMarca to set
	 */
	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}

	/**
	 * @return the marca
	 */
	public Marca getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	/**
	 * @return the marcas
	 */
	public List<Marca> getMarcas() {
		return marcas;
	}

	/**
	 * @param marcas the marcas to set
	 */
	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
}
