package cl.jfoix.atm.ot.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
import cl.jfoix.atm.comun.entity.ProductoGrupo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.ot.entity.Bodega;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProductoGrupo;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.service.IOrdenTrabajoService;
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

	@ManagedProperty(value="#{ordenTrabajoService}")
	private IOrdenTrabajoService ordenTrabajoService;

	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;

	//Filtro
	private Integer idProductoGrupo;
	private Integer estadoSolicitud;
	private Date fechaSolicitud;
	
	private List<ProductoGrupo> productoGrupos;
	
	private Integer idProducto;
	
	private List<Proveedor> proveedores;
	private List<Bodega> bodegas;
	private List<Producto> productos;
	private List<Producto> productosSeleccionados;
	private List<OrdenTrabajoSolicitud> solicitudes;
	private List<OrdenTrabajoSolicitudProducto> productosSolicitud;
	
	private OrdenTrabajoSolicitud ordenTrabajoSolicitud;
	private OrdenTrabajoSolicitudProducto ordenTrabajoSolicitudProducto;
	private OrdenTrabajoSolicitudProductoGrupo ordenTrabajoSolicitudProductoGrupo;
	private Stock stock;
	private Movimiento movimiento;
	
	private String filtroNomProd;
	private String filtroCodigo;
	
	@PostConstruct
	public void init(){
		
		productoGrupos = adminStockService.buscarProductoGrupo();
		
		bodegas = adminStockService.buscarBodegas();
		proveedores = adminStockService.buscarProveedores();
		productos = adminStockService.buscarProductos();
		
		fechaSolicitud = new Date();
		
		solicitudes = solicitudRepuestosService.buscarSolicitudesRepuestos(fechaSolicitud, null, null, null);
		
		nuevoStock();
	}
	
	public void buscarSolicitudes(){
		solicitudes = solicitudRepuestosService.buscarSolicitudesRepuestos(fechaSolicitud, idProductoGrupo.equals(-1) ? null : idProductoGrupo, estadoSolicitud.equals(-1) ? null : estadoSolicitud.equals(0) ? false : true, null);
	}
	
	public void cambiarEstadoSolicitud(){
		solicitudRepuestosService.cambiarEstadoSolicitudRepuesto(ordenTrabajoSolicitud);
		ordenTrabajoSolicitud.setEstado(false);
	}
	
	public void buscarProductosPorGrupo(OrdenTrabajoSolicitudProductoGrupo otspg){
		
		ordenTrabajoSolicitudProductoGrupo = otspg;
		filtroNomProd = "";
		filtroCodigo = "";
		
		productos = adminStockService.buscarProductosPorGrupo(ordenTrabajoSolicitudProductoGrupo.getProductoGrupo().getIdProductoGrupo(), filtroNomProd, filtroCodigo);
		productosSeleccionados = null;
	}
	
	public void agregarProductos(Producto producto){
		if(productosSeleccionados == null){
			productosSeleccionados = new ArrayList<Producto>();
		}
		
		productos.remove(producto);
		productosSeleccionados.add(producto);
	}

	public void quitarProductos(Producto producto){
		productos.add(producto);
		productosSeleccionados.remove(producto);
	}
	
	private void validarProductosSolicitud() throws ViewException{
		if(productosSeleccionados == null || productosSeleccionados.size() == 0){
			throw new ViewException("Debe seleccionar al menos un producto a agregar");
		}
		
		ViewException vEx = new ViewException();
		
		for(Producto producto : productosSeleccionados){
			if(producto.getCantidad() == null || producto.getCantidad().equals(0d)){
				vEx.agregarMensaje("Debe ingresar una cantidad al producto " + producto.getCodigo() + "-" + producto.getDescripcion());
				producto.setCantidad(null);
			} else {
				Stock stock = ordenService.buscarStockPorProducto(producto.getIdProducto(), producto.getCantidad(), ordenTrabajoSolicitudProductoGrupo.getSolicitud().getOrdenTrabajo().getOrden().getIdOrden());
				
				if(stock == null){
					vEx.agregarMensaje("El producto " + producto.getCodigo() + "-" + producto.getDescripcion() + " no tiene stock disponible");
				}
			}
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
	}
	
	public void guardarProductosSolicitud() throws ViewException{
		validarProductosSolicitud();
		
		solicitudRepuestosService.guardarProductosSolicitud(ordenTrabajoSolicitudProductoGrupo, productosSeleccionados);
		
		productos = null;
		productosSeleccionados = null;
		
		RequestContext.getCurrentInstance().addCallbackParam("done", true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Productos Agregados Correctamente"));
	}

	public void cambiarEstadoSolProducto() throws ViewException{
			
		OrdenTrabajoProducto otp = new OrdenTrabajoProducto();
		otp.setCantidad(ordenTrabajoSolicitudProducto.getCantidadAgregada());
		otp.setOrdenTrabajo(ordenTrabajoSolicitud.getOrdenTrabajo());
		otp.setProducto(ordenTrabajoSolicitudProducto.getProducto());
		otp.setTraidoCliente(false);
		otp.setValor(0);
		
		Stock stock = ordenService.buscarStockPorProducto(ordenTrabajoSolicitudProducto.getProducto().getIdProducto(), ordenTrabajoSolicitudProducto.getCantidad(), ordenTrabajoSolicitud.getOrdenTrabajo().getOrden().getIdOrden());
		
		if(stock == null){
			throw new ViewException("El producto a agregar no posee stock suficiente");
		}
			
		try {
			ordenTrabajoService.guardarOrdenTrabajoProducto(otp);
			
			solicitudRepuestosService.cambiarEstadoSolicitudRepuestoProducto(ordenTrabajoSolicitudProducto);
			ordenTrabajoSolicitudProducto.setEstado(false);
		} catch (ViewException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
		
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
	
	public void filtroProductoAgregar(){
		productos = adminStockService.buscarProductosPorGrupo(ordenTrabajoSolicitudProductoGrupo.getProductoGrupo().getIdProductoGrupo(), filtroNomProd, filtroCodigo);
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

	/**
	 * @return the ordenTrabajoService
	 */
	public IOrdenTrabajoService getOrdenTrabajoService() {
		return ordenTrabajoService;
	}

	/**
	 * @param ordenTrabajoService the ordenTrabajoService to set
	 */
	public void setOrdenTrabajoService(IOrdenTrabajoService ordenTrabajoService) {
		this.ordenTrabajoService = ordenTrabajoService;
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
	 * @return the idProductoGrupo
	 */
	public Integer getIdProductoGrupo() {
		return idProductoGrupo;
	}

	/**
	 * @param idProductoGrupo the idProductoGrupo to set
	 */
	public void setIdProductoGrupo(Integer idProductoGrupo) {
		this.idProductoGrupo = idProductoGrupo;
	}

	/**
	 * @return the productoGrupos
	 */
	public List<ProductoGrupo> getProductoGrupos() {
		return productoGrupos;
	}

	/**
	 * @param productoGrupos the productoGrupos to set
	 */
	public void setProductoGrupos(List<ProductoGrupo> productoGrupos) {
		this.productoGrupos = productoGrupos;
	}

	/**
	 * @return the productosSeleccionados
	 */
	public List<Producto> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	/**
	 * @param productosSeleccionados the productosSeleccionados to set
	 */
	public void setProductosSeleccionados(List<Producto> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}

	/**
	 * @return the ordenTrabajoSolicitudProductoGrupo
	 */
	public OrdenTrabajoSolicitudProductoGrupo getOrdenTrabajoSolicitudProductoGrupo() {
		return ordenTrabajoSolicitudProductoGrupo;
	}

	/**
	 * @param ordenTrabajoSolicitudProductoGrupo the ordenTrabajoSolicitudProductoGrupo to set
	 */
	public void setOrdenTrabajoSolicitudProductoGrupo(
			OrdenTrabajoSolicitudProductoGrupo ordenTrabajoSolicitudProductoGrupo) {
		this.ordenTrabajoSolicitudProductoGrupo = ordenTrabajoSolicitudProductoGrupo;
	}

	/**
	 * @return the filtroNomProd
	 */
	public String getFiltroNomProd() {
		return filtroNomProd;
	}

	/**
	 * @param filtroNomProd the filtroNomProd to set
	 */
	public void setFiltroNomProd(String filtroNomProd) {
		this.filtroNomProd = filtroNomProd;
	}

	/**
	 * @return the filtroCodigo
	 */
	public String getFiltroCodigo() {
		return filtroCodigo;
	}

	/**
	 * @param filtroCodigo the filtroCodigo to set
	 */
	public void setFiltroCodigo(String filtroCodigo) {
		this.filtroCodigo = filtroCodigo;
	}
}
