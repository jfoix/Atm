package cl.jfoix.atm.admin.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.comun.entity.ParametroGeneral;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IParametroGeneralService;

@ViewScoped
@ManagedBean(name="parametroGeneralMB")
public class ParametroGeneralMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{parametroGeneralService}")
	private IParametroGeneralService parametroGeneralService;
	
	private String codigoBusqueda;
	private String grupoBusqueda;
	
	private int tipoBusqueda;
	
	private List<ParametroGeneral> parametros;
	private ParametroGeneral parametro;
	
	@PostConstruct
	public void init(){
	}
	
	public void buscarParametros(){
		try{
			parametros = parametroGeneralService.buscarPorCodigoGrupo(tipoBusqueda, tipoBusqueda == 0 ? codigoBusqueda : grupoBusqueda);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarParametro() throws ViewException {
		
		ViewException vEx = new ViewException();
		
		if(this.parametro.getDescripcion() == null || this.parametro.getDescripcion().equals("")){
			vEx.agregarMensaje("Debe ingresar una descripción al parámetro");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		try {
			parametroGeneralService.guardarParametroGeneral(parametro);
			
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Modificado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	public void modificarParametro(){
		System.out.println(parametro);
	}
	
	/**
	 * @return the parametroGeneralService
	 */
	public IParametroGeneralService getParametroGeneralService() {
		return parametroGeneralService;
	}

	/**
	 * @param parametroGeneralService the parametroGeneralService to set
	 */
	public void setParametroGeneralService(
			IParametroGeneralService parametroGeneralService) {
		this.parametroGeneralService = parametroGeneralService;
	}

	/**
	 * @return the codigoBusqueda
	 */
	public String getCodigoBusqueda() {
		return codigoBusqueda;
	}

	/**
	 * @param codigoBusqueda the codigoBusqueda to set
	 */
	public void setCodigoBusqueda(String codigoBusqueda) {
		this.codigoBusqueda = codigoBusqueda;
	}

	/**
	 * @return the grupoBusqueda
	 */
	public String getGrupoBusqueda() {
		return grupoBusqueda;
	}

	/**
	 * @param grupoBusqueda the grupoBusqueda to set
	 */
	public void setGrupoBusqueda(String grupoBusqueda) {
		this.grupoBusqueda = grupoBusqueda;
	}

	/**
	 * @return the tipoBusqueda
	 */
	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * @param tipoBusqueda the tipoBusqueda to set
	 */
	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * @return the parametros
	 */
	public List<ParametroGeneral> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(List<ParametroGeneral> parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return the parametro
	 */
	public ParametroGeneral getParametro() {
		return parametro;
	}

	/**
	 * @param parametro the parametro to set
	 */
	public void setParametro(ParametroGeneral parametro) {
		this.parametro = parametro;
	}	
}
