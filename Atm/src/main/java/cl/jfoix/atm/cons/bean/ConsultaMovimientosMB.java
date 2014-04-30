package cl.jfoix.atm.cons.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.stock.service.IAdminStockService;

@ViewScoped
@ManagedBean(name="consultaMovimientosMB")
public class ConsultaMovimientosMB implements Serializable {

	private static final long serialVersionUID = -8869306601874238013L;

	@ManagedProperty(value="#{adminStockService}")
	private IAdminStockService adminStockService;
	
	private Date fechaDesdeBusq;
	private Date fechaHastaBusq;
	private String codProductoBusq;
	private Integer idProveedorBusq;
	
	private List<Proveedor> proveedores;
	
	private List<Movimiento> movimientos;
	private Movimiento movimiento;
	
	@PostConstruct
	public void init(){
		proveedores = adminStockService.buscarProveedores();
	}
	
	public void buscarMovimientos() throws ViewException {
		
		ViewException vEx = new ViewException();
		
		if(fechaDesdeBusq == null){
			vEx.agregarMensaje("Debe seleccionar una fecha desde");
		}
		if(fechaHastaBusq == null){
			vEx.agregarMensaje("Debe seleccionar una fecha hasta");
		}
		if(fechaDesdeBusq != null && fechaHastaBusq != null && fechaDesdeBusq.after(fechaHastaBusq)){
			vEx.agregarMensaje("La fecha desde debe ser menor a la fecha hasta");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		movimientos = adminStockService.buscarMovimientoPorRangoFechasCodigoProducto(fechaDesdeBusq, fechaHastaBusq, codProductoBusq, idProveedorBusq);
	}

	/**
	 * @return the adminStockService
	 */
	public IAdminStockService getAdminStockService() {
		return adminStockService;
	}

	/**
	 * @param adminStockService the adminStockService to set
	 */
	public void setAdminStockService(IAdminStockService adminStockService) {
		this.adminStockService = adminStockService;
	}

	/**
	 * @return the fechaDesdeBusq
	 */
	public Date getFechaDesdeBusq() {
		return fechaDesdeBusq;
	}

	/**
	 * @param fechaDesdeBusq the fechaDesdeBusq to set
	 */
	public void setFechaDesdeBusq(Date fechaDesdeBusq) {
		this.fechaDesdeBusq = fechaDesdeBusq;
	}

	/**
	 * @return the fechaHastaBusq
	 */
	public Date getFechaHastaBusq() {
		return fechaHastaBusq;
	}

	/**
	 * @param fechaHastaBusq the fechaHastaBusq to set
	 */
	public void setFechaHastaBusq(Date fechaHastaBusq) {
		this.fechaHastaBusq = fechaHastaBusq;
	}

	/**
	 * @return the codProductoBusq
	 */
	public String getCodProductoBusq() {
		return codProductoBusq;
	}

	/**
	 * @param codProductoBusq the codProductoBusq to set
	 */
	public void setCodProductoBusq(String codProductoBusq) {
		this.codProductoBusq = codProductoBusq;
	}

	/**
	 * @return the idProveedorBusq
	 */
	public Integer getIdProveedorBusq() {
		return idProveedorBusq;
	}

	/**
	 * @param idProveedorBusq the idProveedorBusq to set
	 */
	public void setIdProveedorBusq(Integer idProveedorBusq) {
		this.idProveedorBusq = idProveedorBusq;
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

	/**
	 * @return the movimientos
	 */
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	/**
	 * @param movimientos the movimientos to set
	 */
	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	/**
	 * @return the movimiento
	 */
	public Movimiento getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento the movimiento to set
	 */
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
}
