package cl.jfoix.atm.cons.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.ot.dto.ResumentOTDto;
import cl.jfoix.atm.ot.entity.Orden;

@ViewScoped
@ManagedBean(name="consultaOTVehiculoMB")
public class ConsultaOTVehiculoMB implements Serializable {

	private static final long serialVersionUID = -8869306601874238013L;

	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;
	
	private Integer idOrdenBusq;
	private String patenteBusq;
	
	private List<Orden> ordenes;
	private Orden orden;
	
	private ResumentOTDto resumenOT;
	
	public void buscarOrdenes(){
		ordenes = ordenService.consultaOTVehiculo(idOrdenBusq, patenteBusq);
	}
	
	public void generarResumenOT(){
		resumenOT = ordenService.buscarResumenOT(orden); 
	}
	
	public void generarReporteResumentOT(){
		try{
			
			Map<String, Boolean> totalesIVA = new HashMap<String, Boolean>();
			
			totalesIVA.put("ivaRepuesto", true);
			totalesIVA.put("ivaManoObra", true);
			totalesIVA.put("ivaSTercero", true);
			
			byte[] reportePDF = ordenService.generarResumenOT(orden, totalesIVA);
			
			if(reportePDF != null){
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.setContentLength(reportePDF.length);
				response.setContentType("application/pdf");
		        response.setHeader("Content-Disposition", "attachment;filename=ResumenOT_" + orden.getIdOrden().toString() + ".pdf");
		        response.getOutputStream().write(reportePDF);
		        response.getOutputStream().flush();
		        response.getOutputStream().close();
		        FacesContext.getCurrentInstance().responseComplete();
			}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	/**
	 * @return the idOrdenBusq
	 */
	public Integer getIdOrdenBusq() {
		return idOrdenBusq;
	}
	/**
	 * @param idOrdenBusq the idOrdenBusq to set
	 */
	public void setIdOrdenBusq(Integer idOrdenBusq) {
		this.idOrdenBusq = idOrdenBusq;
	}
	/**
	 * @return the patenteBusq
	 */
	public String getPatenteBusq() {
		return patenteBusq;
	}
	/**
	 * @param patenteBusq the patenteBusq to set
	 */
	public void setPatenteBusq(String patenteBusq) {
		this.patenteBusq = patenteBusq;
	}
	/**
	 * @return the ordenes
	 */
	public List<Orden> getOrdenes() {
		return ordenes;
	}
	/**
	 * @param ordenes the ordenes to set
	 */
	public void setOrdenes(List<Orden> ordenes) {
		this.ordenes = ordenes;
	}
	/**
	 * @return the orden
	 */
	public Orden getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	/**
	 * @return the ordenService
	 */
	public IOrdenService getOrdenService() {
		return ordenService;
	}

	/**
	 * @param ordenService the ordenService to set
	 */
	public void setOrdenService(IOrdenService ordenService) {
		this.ordenService = ordenService;
	}

	/**
	 * @return the resumenOT
	 */
	public ResumentOTDto getResumenOT() {
		return resumenOT;
	}

	/**
	 * @param resumenOT the resumenOT to set
	 */
	public void setResumenOT(ResumentOTDto resumenOT) {
		this.resumenOT = resumenOT;
	}
	
	
}
