package cl.jfoix.atm.mante.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.ProductoGrupo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IMarcaService;
import cl.jfoix.atm.comun.service.IProductoGrupoService;
import cl.jfoix.atm.comun.service.IProductoService;

@ViewScoped
@ManagedBean(name="productoMB")
public class ProductoMB implements Serializable {

	private static final long serialVersionUID = 5134194114190172477L;

	@ManagedProperty(value="#{productoService}")
	private IProductoService productoService;
	
	@ManagedProperty(value="#{marcaService}")
	private IMarcaService marcaService;
	
	@ManagedProperty(value="#{productoGrupoService}")
	private IProductoGrupoService productoGrupoService;
	
	private Producto producto;
	
	private List<Marca> marcas;
	private List<ProductoGrupo> gruposProducto;
	private List<Producto> productos;
	
	private String productoCode;
	private String productoDesc;
	private Integer productoIdMarca;
	private Integer productoGrupoId;
	
	private String grupoCodigo = "___";
	
	private boolean modExistente;

	@PostConstruct
	public void init(){
		marcas = marcaService.buscarTodasMarcas();
		gruposProducto = productoGrupoService.buscarProductosGrupo();

		producto = new Producto();
		producto.setMarca(new Marca());
		producto.setProductoGrupo(new ProductoGrupo());
		modExistente = false;
	}

	public void mostarGrupoCodigo(){
		if(producto.getProductoGrupo().getIdProductoGrupo().equals(-1)){
			grupoCodigo = "___";
		} else {
			for(ProductoGrupo pg : gruposProducto){
				if(pg.getIdProductoGrupo().equals(producto.getProductoGrupo().getIdProductoGrupo())){
					grupoCodigo = pg.getCodigo();
					break;
				}
			}
		}
	}
	
	public void nuevoProducto(){
		this.producto = new Producto();
		this.producto.setEstado(true);
		this.producto.setMarca(new Marca());
		this.producto.setProductoGrupo(new ProductoGrupo());
		modExistente = false;
		grupoCodigo = "___";
	}
	
	public void actualizarProducto(){
		if(this.producto.getMarca() == null){
			this.producto.setMarca(new Marca());
		}
		if(producto.getProductoGrupo().getIdProductoGrupo().equals(-1)){
			grupoCodigo = "___";
		} else {
			for(ProductoGrupo pg : gruposProducto){
				if(pg.getIdProductoGrupo().equals(producto.getProductoGrupo().getIdProductoGrupo())){
					grupoCodigo = pg.getCodigo();
					break;
				}
			}
		}
		modExistente = false;
	}
	
	public void buscarProductos(){
		try{
			productos =  productoService.buscarProductosPorCodigoDescripcionMarca(productoCode, productoDesc, productoIdMarca, productoGrupoId);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void cargarProducto(){
		modExistente = true;
		producto = productoService.buscarProductoPorId(producto.getIdProducto());
	}
	
	public void guardarProducto() throws ViewException{
		
		ViewException vEx = new ViewException();
		
		if(producto.getCodigo() == null || producto.getCodigo().equals("")){
			vEx.agregarMensaje("Debe ingresar un código para el producto");
		}
		
		if(producto.getDescripcion() == null || producto.getDescripcion().equals("")){
			vEx.agregarMensaje("Debe ingresar una descripción para el producto");
		}
		
		if(producto.getProductoGrupo().getIdProductoGrupo().equals(-1)){
			vEx.agregarMensaje("Debe seleccionar un grupo para el producto");
		}
		
		if(producto.getMarca().getIdMarca() == null || producto.getMarca().getIdMarca().equals(0)){
			vEx.agregarMensaje("Debe seleccionar la marca del producto");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		try{
			
			if(!productoService.validarProductoPorCodigoGrupo(producto.getIdProducto(), producto.getProductoGrupo().getIdProductoGrupo(), producto.getCodigo(), producto.getMarca().getIdMarca())){
				RequestContext.getCurrentInstance().addCallbackParam("prodExiste", true);
				return;
			}
			
			String msg = "";
			
			producto.setCodigo(producto.getCodigo().toUpperCase());
			
			msg = "Modificado con éxito";
			
			if(producto.getIdProducto() == null){
				msg = "Ingresado con éxito";
			}
			
			productoService.guardarProducto(producto);
			
			if(producto.getMarca() != null){
				for(Marca marca : marcas){
					if(marca.getIdMarca().equals(this.producto.getMarca().getIdMarca())){
						producto.getMarca().setDescripcion(marca.getDescripcion());
						break;
					}
				}
			}
			
			modExistente = false;
			
			buscarProductos();
			String codigoGrupo = "";
			for(ProductoGrupo grupo : gruposProducto){
				if(grupo.getIdProductoGrupo().equals(producto.getProductoGrupo().getIdProductoGrupo())){
					codigoGrupo = grupo.getCodigo();
					break;
				}
			}
			
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			RequestContext.getCurrentInstance().addCallbackParam("codigo", codigoGrupo + producto.getCodigo());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarProducto(){
		try{
			producto.setEstado(false);
			productoService.guardarProducto(producto);
			productos.remove(producto);
			modExistente = false;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	/**
	 * @return the productoService
	 */
	public IProductoService getProductoService() {
		return productoService;
	}

	/**
	 * @param productoService the productoService to set
	 */
	public void setProductoService(IProductoService productoService) {
		this.productoService = productoService;
	}

	/**
	 * @return the marcaService
	 */
	public IMarcaService getMarcaService() {
		return marcaService;
	}

	/**
	 * @param marcaService the marcaService to set
	 */
	public void setMarcaService(IMarcaService marcaService) {
		this.marcaService = marcaService;
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
	 * @return the productoCode
	 */
	public String getProductoCode() {
		return productoCode;
	}

	/**
	 * @param productoCode the productoCode to set
	 */
	public void setProductoCode(String productoCode) {
		this.productoCode = productoCode;
	}

	/**
	 * @return the productoDesc
	 */
	public String getProductoDesc() {
		return productoDesc;
	}

	/**
	 * @param productoDesc the productoDesc to set
	 */
	public void setProductoDesc(String productoDesc) {
		this.productoDesc = productoDesc;
	}

	/**
	 * @return the productoIdMarca
	 */
	public Integer getProductoIdMarca() {
		return productoIdMarca;
	}

	/**
	 * @param productoIdMarca the productoIdMarca to set
	 */
	public void setProductoIdMarca(Integer productoIdMarca) {
		this.productoIdMarca = productoIdMarca;
	}

	/**
	 * @return the productoGrupoService
	 */
	public IProductoGrupoService getProductoGrupoService() {
		return productoGrupoService;
	}

	/**
	 * @param productoGrupoService the productoGrupoService to set
	 */
	public void setProductoGrupoService(IProductoGrupoService productoGrupoService) {
		this.productoGrupoService = productoGrupoService;
	}

	/**
	 * @return the gruposProducto
	 */
	public List<ProductoGrupo> getGruposProducto() {
		return gruposProducto;
	}

	/**
	 * @param gruposProducto the gruposProducto to set
	 */
	public void setGruposProducto(List<ProductoGrupo> gruposProducto) {
		this.gruposProducto = gruposProducto;
	}

	/**
	 * @return the productoGrupoId
	 */
	public Integer getProductoGrupoId() {
		return productoGrupoId;
	}

	/**
	 * @param productoGrupoId the productoGrupoId to set
	 */
	public void setProductoGrupoId(Integer productoGrupoId) {
		this.productoGrupoId = productoGrupoId;
	}

	/**
	 * @return the grupoCodigo
	 */
	public String getGrupoCodigo() {
		return grupoCodigo;
	}

	/**
	 * @param grupoCodigo the grupoCodigo to set
	 */
	public void setGrupoCodigo(String grupoCodigo) {
		this.grupoCodigo = grupoCodigo;
	}

	/**
	 * @return the modExistente
	 */
	public boolean isModExistente() {
		return modExistente;
	}

	/**
	 * @param modExistente the modExistente to set
	 */
	public void setModExistente(boolean modExistente) {
		this.modExistente = modExistente;
	}
}
