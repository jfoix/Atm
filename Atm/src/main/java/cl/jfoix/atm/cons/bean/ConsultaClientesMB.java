package cl.jfoix.atm.cons.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.ot.entity.VehiculoOrden;

@ViewScoped
@ManagedBean(name="consultaClientesMB")
public class ConsultaClientesMB implements Serializable {

	private static final long serialVersionUID = -8869306601874238013L;

	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;
	
	private String rutClienteBusq;
	private String patenteBusq;
	
	private List<VehiculoOrden> vehiculos;
	private VehiculoOrden vehiculo;
	
	public void buscarClientes(){
		vehiculos = ordenService.consultaClientes(rutClienteBusq, patenteBusq);
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
	 * @return the rutClienteBusq
	 */
	public String getRutClienteBusq() {
		return rutClienteBusq;
	}

	/**
	 * @param rutClienteBusq the rutClienteBusq to set
	 */
	public void setRutClienteBusq(String rutClienteBusq) {
		this.rutClienteBusq = rutClienteBusq;
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
	 * @return the vehiculos
	 */
	public List<VehiculoOrden> getVehiculos() {
		return vehiculos;
	}

	/**
	 * @param vehiculos the vehiculos to set
	 */
	public void setVehiculos(List<VehiculoOrden> vehiculos) {
		this.vehiculos = vehiculos;
	}

	/**
	 * @return the vehiculo
	 */
	public VehiculoOrden getVehiculo() {
		return vehiculo;
	}

	/**
	 * @param vehiculo the vehiculo to set
	 */
	public void setVehiculo(VehiculoOrden vehiculo) {
		this.vehiculo = vehiculo;
	}
}
