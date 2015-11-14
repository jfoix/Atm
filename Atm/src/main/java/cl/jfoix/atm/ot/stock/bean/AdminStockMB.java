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

import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.ProductoGrupo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.entity.Bodega;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.MovimientoDocumento;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.stock.dto.FiltroBusquedaStockDto;
import cl.jfoix.atm.ot.stock.service.IAdminStockService;
import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
	private List<ProductoGrupo> grupos;
	private List<Marca> marcas;
	private List<Movimiento> movimientos;
	
	private Stock stock;
	private Movimiento movimiento;
	private TipoMovimientoEnum tipoMovimiento;
	
	private List<MovimientoDocumento> documentos;
	private MovimientoDocumento documento;
	
	private String busqProdDesc;
	private Integer busqProdIdGrupo;
	private Integer busqProdIdMarca;
	
	private List<Producto> productosBusqueda;
	private Producto producto;
	private Double cantidadStock;
	
	@PostConstruct
	public void init(){
		filtro = new FiltroBusquedaStockDto();
		bodegas = adminStockService.buscarBodegas();
		productos = adminStockService.buscarProductos();
		proveedores = adminStockService.buscarProveedores();
		
		grupos = adminStockService.buscarProductoGrupo();
		marcas = adminStockService.buscarMarcas();
		
		nuevoStock();
		
		limpiarBusquedaProducto();
	}
	
	public void limpiarBusquedaProducto(){
		busqProdDesc = "";
		busqProdIdGrupo = null;
		busqProdIdMarca = null;
		productosBusqueda = null;
	}
	
	public void buscarProductos(){
		productosBusqueda = adminStockService.buscarProductosFiltros(busqProdDesc, busqProdIdGrupo, busqProdIdMarca);
	}
	
	public void seleccionarProducto(){
		
		Stock stockActual = adminStockService.buscarStockPorIdProducto(producto.getIdProducto());
		
		if(stockActual != null){
			return;
		}
		
		RequestContext.getCurrentInstance().addCallbackParam("done", true);
		stock.setProducto(producto);
	}
	
	public void elminiarArchivo(){
		documentos.remove(documento);
	}
	
	public void cambiarMovimientoStock(){
		stock = adminStockService.buscarStockPorIdProducto(producto.getIdProducto());
		iniciarMovimientoStock();
	}
	
	public void iniciarMovimientoStock(){
		producto = stock.getProducto();
		
		cantidadStock = stock.getCantidad();
		
		tipoMovimiento = TipoMovimientoEnum.INGRESO;
		
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
		
		stock.setProducto(producto);
		movimientos = adminStockService.buscarMovimientoPorIdStock(stock.getIdStock());
	}
	
	public void buscarMovimientosProductoBodega(){
		stock = adminStockService.buscarStockPorIdProductoIdBodega(producto.getIdProducto(), stock.getBodega().getIdBodega());
		
		if(stock != null){
			movimientos = adminStockService.buscarMovimientoPorIdStock(stock.getIdStock());
			cantidadStock = stock.getCantidad();
		} else {
			movimientos = null;
			cantidadStock = 0d;
			stock = new Stock();
			stock.setBodega(new Bodega());
			stock.setProducto(producto);
			stock.setCantidad(0d);
		}
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
	
	public void modificarCoordenadaStock(Stock stock){
		this.stock = stock;
	}
	
	public void guardarCoordenadaStock() throws ViewException{
		if(stock.getCoordBodega() == null || stock.getCoordBodega().equals("")){
			throw new ViewException("Debe ingresar un valor para la coordenada del producto en Stock");
		}
		
		adminStockService.modificarStock(stock);
		
		RequestContext.getCurrentInstance().addCallbackParam("done", true);
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);

        for(int j=1; j < sheet.getPhysicalNumberOfRows(); j++){
        	HSSFRow row = sheet.getRow(j);
	        for(int i=0; i < row.getPhysicalNumberOfCells();i++) {
	            HSSFCell cell = row.getCell(i);
	            String dato = cell.getStringCellValue();
	            dato = dato.replaceAll("<div align=\"center\">", "").replaceAll("<div align=\"right\">", "").replaceAll("</div>", "");
	            cell.setCellValue(dato);
	        }
        }
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
		
//		Stock stockProd = adminStockService.buscarStockPorIdProducto(stock.getProducto().getIdProducto());
		Stock stockProd = adminStockService.buscarStockPorIdProductoIdBodega(stock.getProducto().getIdProducto(), stock.getBodega().getIdBodega());

		if(movimiento.getCantidad().doubleValue() < 0d && (stockProd == null || stockProd.getCantidad().doubleValue() == 0d)){
			vEx.agregarMensaje("No puede descontar stock a un producto que no tiene stock");
		}
		
		if(movimiento.getCantidad().doubleValue() < 0d && stockProd != null && (stockProd.getCantidad() - (movimiento.getCantidad()*-1d)) < 0d){
			vEx.agregarMensaje("La cantidad que intenta descontar es mayor al stock que tiene actualmente");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		boolean modificacion = false;
		
		if(stockProd != null){
			stock = stockProd;
			stock.setCantidad(stock.getCantidad() + movimiento.getCantidad());
			modificacion = true;
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
		
		movimientos = adminStockService.buscarMovimientoPorIdStock(stock.getIdStock());
		cantidadStock = stock.getCantidad();
		
		tipoMovimiento = TipoMovimientoEnum.INGRESO;
		
		stock.setBodega(new Bodega());
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
			
		if(!modificacion){
			buscarStock();
		}
	}
	
	public void nuevoStock(){
		
		tipoMovimiento = TipoMovimientoEnum.INGRESO;
		
		stock = new Stock();
		stock.setProducto(new Producto());
		stock.setBodega(new Bodega());
		stock.setCantidad(0d);
		
		movimiento = new Movimiento();
		movimiento.setProveedor(new Proveedor());
		
		producto = null;
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

	/**
	 * @return the grupos
	 */
	public List<ProductoGrupo> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(List<ProductoGrupo> grupos) {
		this.grupos = grupos;
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

	/**
	 * @return the busqProdDesc
	 */
	public String getBusqProdDesc() {
		return busqProdDesc;
	}

	/**
	 * @param busqProdDesc the busqProdDesc to set
	 */
	public void setBusqProdDesc(String busqProdDesc) {
		this.busqProdDesc = busqProdDesc;
	}

	/**
	 * @return the busqProdIdGrupo
	 */
	public Integer getBusqProdIdGrupo() {
		return busqProdIdGrupo;
	}

	/**
	 * @param busqProdIdGrupo the busqProdIdGrupo to set
	 */
	public void setBusqProdIdGrupo(Integer busqProdIdGrupo) {
		this.busqProdIdGrupo = busqProdIdGrupo;
	}

	/**
	 * @return the busqProdIdMarca
	 */
	public Integer getBusqProdIdMarca() {
		return busqProdIdMarca;
	}

	/**
	 * @param busqProdIdMarca the busqProdIdMarca to set
	 */
	public void setBusqProdIdMarca(Integer busqProdIdMarca) {
		this.busqProdIdMarca = busqProdIdMarca;
	}

	/**
	 * @return the productosBusqueda
	 */
	public List<Producto> getProductosBusqueda() {
		return productosBusqueda;
	}

	/**
	 * @param productosBusqueda the productosBusqueda to set
	 */
	public void setProductosBusqueda(List<Producto> productosBusqueda) {
		this.productosBusqueda = productosBusqueda;
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
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
	 * @return the cantidadStock
	 */
	public Double getCantidadStock() {
		return cantidadStock;
	}

	/**
	 * @param cantidadStock the cantidadStock to set
	 */
	public void setCantidadStock(Double cantidadStock) {
		this.cantidadStock = cantidadStock;
	}
}
