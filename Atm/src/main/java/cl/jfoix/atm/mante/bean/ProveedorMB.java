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
import cl.jfoix.atm.comun.service.IProveedorService;
import cl.jfoix.atm.ot.entity.Proveedor;

@ViewScoped
@ManagedBean(name="proveedorMB")
public class ProveedorMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{proveedorService}")
	private IProveedorService proveedorService;
	
	private String nombreProveedor;
	private Proveedor proveedor;
	
	private List<Proveedor> proveedores;
	
	public void nuevaProveedor(){
		this.proveedor = new Proveedor();
		this.proveedor.setEstado(true);
	}
	
	public void buscarProveedores(){
		try{
			proveedores = proveedorService.buscarProveedoresPorDescripcion(nombreProveedor);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarProveedor() throws ViewException {
		
		ViewException vEx = new ViewException();
		
		if(this.proveedor.getCodigo() == null || this.proveedor.getCodigo().equals("")){
			vEx.agregarMensaje("Debe ingresar un código al proveedor");
		}
		
		if(this.proveedor.getDescripcion() == null || this.proveedor.getDescripcion().equals("")){
			vEx.agregarMensaje("Debe ingresar una descripción a el proveedor");
		}
		
		if(this.proveedor.getPorcentajeGanancia().equals(0d)){
			vEx.agregarMensaje("Debe ingresar el porcentaje de ganancia");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		try {
			String msg = "";
			
			if(proveedores == null){
				proveedores = new ArrayList<Proveedor>();
			}
			
			proveedor.setCodigo(proveedor.getCodigo().toUpperCase());
			
			if(this.proveedor.getIdProveedor() == null){
				proveedores.add(proveedor);
				
				msg = "Ingresado con éxito";
			} else {
				msg = "Modificado con éxito";
			}
			
			proveedorService.guardarProveedor(proveedor);
			
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarProveedor(){
		try {
			this.proveedor.setEstado(false);
			this.proveedorService.guardarProveedor(proveedor);
			this.proveedores.remove(proveedor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	/**
	 * @return the proveedorService
	 */
	public IProveedorService getProveedorService() {
		return proveedorService;
	}

	/**
	 * @param proveedorService the proveedorService to set
	 */
	public void setProveedorService(IProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}

	/**
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}

	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	/**
	 * @return the proveedor
	 */
	public Proveedor getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the proveedores
	 */
	public List<Proveedor> getProveedores() {
		return proveedores;
	}

	/**
	 * @param proveedores the proveedores to set
	 */
	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}
}
