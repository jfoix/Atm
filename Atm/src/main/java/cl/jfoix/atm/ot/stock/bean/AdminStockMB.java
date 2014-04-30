package cl.jfoix.atm.ot.stock.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.entity.Bodega;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.MovimientoDocumento;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.stock.dto.FiltroBusquedaStockDto;
import cl.jfoix.atm.ot.stock.service.IAdminStockService;
import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;

@ViewScoped
@ManagedBean(name="adminStockMB")
public class AdminStockMB implements Serializable {

	private static final long serialVersionUID = 5914430368207164591L;

	@ManagedProperty(value="#{adminStockService}")
	private IAdminStockService adminStockService;

	private FiltroBusquedaStockDto filtro;
	
	private List<Bodega> bodegas;
	private List<Stock> stocks;
	private List<Producto> productos;
	private List<Proveedor> proveedores;
	
	private Stock stock;
	private Movimiento movimiento;
	private TipoMovimientoEnum tipoMovimiento;
	
	private List<MovimientoDocumento> documentos;
	private MovimientoDocumento documento;
	
	@PostConstruct
	public void init(){
		filtro = new FiltroBusquedaStockDto();
		bodegas = adminStockService.buscarBodegas();
		productos = adminStockService.buscarProductos();
		proveedores = adminStockService.buscarProveedores();
		
		nuevoStock();
	}
	
	public void elminiarArchivo(){
		documentos.remove(documento);
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		if(documentos == null){
			documentos = new ArrayList<MovimientoDocumento>();
		}
		
		MovimientoDocumento doc = new MovimientoDocumento();
		doc.setDatosArchivo(event.getFile().getContents());
		doc.setNombreArchivo(event.getFile().getFileName());
		doc.setMovimiento(movimiento);
		
		documentos.add(doc);
	}
	
	public void descargarArchivo(){
		try{
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment;filename=" + documento.getNombreArchivo() + "");
	        response.getOutputStream().write(documento.getDatosArchivo());
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        FacesContext.getCurrentInstance().responseComplete();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void buscarStock(){
		stocks = adminStockService.buscarStock(filtro);
	}
	
	public void ajusteIngresoStock(){
		tipoMovimiento = TipoMovimientoEnum.INGRESO;
		
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
	}
	
	public void ajusteEgresoStock(){
		tipoMovimiento = TipoMovimientoEnum.EGRESO;
		
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
	}
	
	public void guardarStock() throws ViewException {
		
		ViewException vEx = new ViewException();
		
		if(stock.getProducto().getIdProducto().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar un producto");
		}
		
		if(movimiento.getCantidad().equals(0d)){
			vEx.agregarMensaje("Debe ingresar la cantidad");
		}
		
		if(movimiento.getCantidad().doubleValue() > 0d && movimiento.getProveedor().getIdProveedor().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar un proveedor");
		}

		if(movimiento.getCantidad().doubleValue() > 0d && stock.getBodega().getIdBodega().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar una bodega");
		}
		
		if(movimiento.getCantidad().doubleValue() > 0d && movimiento.getValorUnidad().equals(0)){
			vEx.agregarMensaje("Debe ingresar un valor al stock");
		}
		
		Stock stockProd = adminStockService.buscarStockPorIdProducto(stock.getProducto().getIdProducto());

		if(movimiento.getCantidad().doubleValue() < 0d && (stockProd == null || stockProd.getCantidad().doubleValue() == 0d)){
			vEx.agregarMensaje("No puede descontar stock a un producto que no tiene stock");
		}
		
		if(movimiento.getCantidad().doubleValue() < 0d && stockProd != null && (stockProd.getCantidad() - (movimiento.getCantidad()*-1d)) < 0d){
			vEx.agregarMensaje("La cantidad que intenta descontar es mayor al stock que tiene actualmente");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		if(stockProd != null){
			stock = stockProd;
			stock.setCantidad(stock.getCantidad() + movimiento.getCantidad());
		} else {
			stock.setCantidad(movimiento.getCantidad());
		}
		
		if(movimiento.getProveedor().getIdProveedor().equals(-1)){
			movimiento.setProveedor(null);
		} else {
			for(Proveedor proveedor : proveedores){
				if(proveedor.getIdProveedor().equals(movimiento.getProveedor().getIdProveedor())){
					movimiento.getProveedor().setPorcentajeGanancia(proveedor.getPorcentajeGanancia());
				}
			}
		}
		
		movimiento.setDocumentos(documentos);
		
		movimiento.setTipo(tipoMovimiento);
		
		adminStockService.guardarStock(stock, movimiento);
		
		adminStockService.actualizarProductoStockValor(movimiento);
		
		RequestContext.getCurrentInstance().addCallbackParam("done", true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Stock Almacenado Correctamente"));
		
		nuevoStock();
		buscarStock();
	}
	
	public void nuevoStock(){
		
		tipoMovimiento = TipoMovimientoEnum.INGRESO;
		
		stock = new Stock();
		stock.setProducto(new Producto());
		stock.setBodega(new Bodega());
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
	}
	
	/**
	 * @return the filtro
	 */
	public FiltroBusquedaStockDto getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(FiltroBusquedaStockDto filtro) {
		this.filtro = filtro;
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
	 * @return the stocks
	 */
	public List<Stock> getStocks() {
		return stocks;
	}

	/**
	 * @param stocks the stocks to set
	 */
	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
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
	 * @return the tipoMovimiento
	 */
	public TipoMovimientoEnum getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(TipoMovimientoEnum tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return the documentos
	 */
	public List<MovimientoDocumento> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(List<MovimientoDocumento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the documento
	 */
	public MovimientoDocumento getDocumento() {
		return documento;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(MovimientoDocumento documento) {
		this.documento = documento;
	}
}
