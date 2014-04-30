package cl.jfoix.atm.ot.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.entity.Bodega;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.service.ISolicitudRepuestosService;
import cl.jfoix.atm.ot.stock.service.IAdminStockService;
import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;

@ViewScoped
@ManagedBean(name="adminSolicitudRepuestoMB")
public class AdminSolicitudRepuestoMB implements Serializable {

	private static final long serialVersionUID = 171864051444296552L;

	@ManagedProperty(value="#{adminStockService}")
	private IAdminStockService adminStockService;
	
	@ManagedProperty(value="#{solicitudRepuestosService}")
	private ISolicitudRepuestosService solicitudRepuestosService;
	
	//Filtro
	private Integer idProducto;
	private Date fechaSolicitud;
	private Integer estadoSolicitud;
	
	private List<Proveedor> proveedores;
	private List<Bodega> bodegas;
	private List<Producto> productos;
	private List<OrdenTrabajoSolicitud> solicitudes;
	private List<OrdenTrabajoSolicitudProducto> productosSolicitud;
	
	private OrdenTrabajoSolicitud ordenTrabajoSolicitud;
	private OrdenTrabajoSolicitudProducto ordenTrabajoSolicitudProducto;
	private Stock stock;
	private Movimiento movimiento;
	
	@PostConstruct
	public void init(){
		
		bodegas = adminStockService.buscarBodegas();
		proveedores = adminStockService.buscarProveedores();
		productos = adminStockService.buscarProductos();
		
		fechaSolicitud = new Date();
		
		solicitudes = solicitudRepuestosService.buscarSolicitudesRepuestos(fechaSolicitud, null, null, null);
		
		nuevoStock();
	}
	
	public void buscarSolicitudes(){
		solicitudes = solicitudRepuestosService.buscarSolicitudesRepuestos(fechaSolicitud, idProducto.equals(-1) ? null : idProducto, estadoSolicitud.equals(-1) ? null : estadoSolicitud.equals(0) ? false : true, null);
	}
	
	public void cambiarEstadoSolicitud(){
		solicitudRepuestosService.cambiarEstadoSolicitudRepuesto(ordenTrabajoSolicitud);
		ordenTrabajoSolicitud.setEstado(false);
	}
	
	public void cambiarEstadoSolProducto(){
		solicitudRepuestosService.cambiarEstadoSolicitudRepuestoProducto(ordenTrabajoSolicitudProducto);
		ordenTrabajoSolicitudProducto.setEstado(false);
	}
	
	public void agregarStock(){
		stock.setProducto(ordenTrabajoSolicitudProducto.getProducto());
		movimiento.setCantidad(ordenTrabajoSolicitudProducto.getCantidad());
	}
	
	public void guardarStock() throws ViewException{
		
		ViewException vEx = new ViewException();
		
		if(movimiento.getCantidad().equals(0d)){
			vEx.agregarMensaje("Debe ingresar la cantidad");
		}
		
		if(movimiento.getProveedor().getIdProveedor().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar un proveedor");
		}

		if(stock.getBodega().getIdBodega().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar una bodega");
		}
		
		if(movimiento.getValorUnidad().equals(0)){
			vEx.agregarMensaje("Debe ingresar un valor al stock");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		Stock stockProd = adminStockService.buscarStockPorIdProducto(stock.getProducto().getIdProducto());
		
		if(stockProd != null){
			stock = stockProd;
			stock.setCantidad(stock.getCantidad() + movimiento.getCantidad());
		} else {
			stock.setCantidad(movimiento.getCantidad());
		}
		
		for(Proveedor proveedor : proveedores){
			if(proveedor.getIdProveedor().equals(movimiento.getProveedor().getIdProveedor())){
				movimiento.getProveedor().setPorcentajeGanancia(proveedor.getPorcentajeGanancia());
			}
		}
		
		movimiento.setTipo(TipoMovimientoEnum.INGRESO);
		adminStockService.guardarStock(stock, movimiento);
		
		adminStockService.actualizarProductoStockValor(movimiento);
		
		RequestContext.getCurrentInstance().addCallbackParam("done", true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Stock Almacenado Correctamente"));
		
		nuevoStock();
	}
	
	public void nuevoStock(){
		
		stock = new Stock();
		stock.setProducto(new Producto());
		stock.setBodega(new Bodega());
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
		movimiento.setTipo(TipoMovimientoEnum.INGRESO);
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
	 * @return the solicitudRepuestosService
	 */
	public ISolicitudRepuestosService getSolicitudRepuestosService() {
		return solicitudRepuestosService;
	}

	/**
	 * @param solicitudRepuestosService the solicitudRepuestosService to set
	 */
	public void setSolicitudRepuestosService(
			ISolicitudRepuestosService solicitudRepuestosService) {
		this.solicitudRepuestosService = solicitudRepuestosService;
	}

	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @return the fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public Integer getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(Integer estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
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

	/**
	 * @return the solicitudes
	 */
	public List<OrdenTrabajoSolicitud> getSolicitudes() {
		return solicitudes;
	}

	/**
	 * @param solicitudes the solicitudes to set
	 */
	public void setSolicitudes(List<OrdenTrabajoSolicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	/**
	 * @return the productosSolicitud
	 */
	public List<OrdenTrabajoSolicitudProducto> getProductosSolicitud() {
		return productosSolicitud;
	}

	/**
	 * @param productosSolicitud the productosSolicitud to set
	 */
	public void setProductosSolicitud(
			List<OrdenTrabajoSolicitudProducto> productosSolicitud) {
		this.productosSolicitud = productosSolicitud;
	}

	/**
	 * @return the ordenTrabajoSolicitud
	 */
	public OrdenTrabajoSolicitud getOrdenTrabajoSolicitud() {
		return ordenTrabajoSolicitud;
	}

	/**
	 * @param ordenTrabajoSolicitud the ordenTrabajoSolicitud to set
	 */
	public void setOrdenTrabajoSolicitud(OrdenTrabajoSolicitud ordenTrabajoSolicitud) {
		this.ordenTrabajoSolicitud = ordenTrabajoSolicitud;
	}

	/**
	 * @return the ordenTrabajoSolicitudProducto
	 */
	public OrdenTrabajoSolicitudProducto getOrdenTrabajoSolicitudProducto() {
		return ordenTrabajoSolicitudProducto;
	}

	/**
	 * @param ordenTrabajoSolicitudProducto the ordenTrabajoSolicitudProducto to set
	 */
	public void setOrdenTrabajoSolicitudProducto(
			OrdenTrabajoSolicitudProducto ordenTrabajoSolicitudProducto) {
		this.ordenTrabajoSolicitudProducto = ordenTrabajoSolicitudProducto;
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
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

	/**
	 * @return the productos
	 */
	public List<Producto> getProductos() {
		return productos;
	}

	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
