package cl.jfoix.atm.comun.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import cl.jfoix.atm.comun.service.IParametroGeneralService;

@ApplicationScoped
@ManagedBean(name="parametrosMB")
public class ParametrosMB implements Serializable {

	private static final long serialVersionUID = 7035618732102060152L;

	@ManagedProperty(value="#{parametroGeneralService}")
	private IParametroGeneralService parametroGeneralService;

	private String version;
	
	@PostConstruct
	public void init(){
		version = parametroGeneralService.buscarString("version.app");
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
