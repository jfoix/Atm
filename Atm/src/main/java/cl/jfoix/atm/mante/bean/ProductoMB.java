package cl.jfoix.atm.mante.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
import cl.jfoix.atm.comun.service.IMarcaService;
import cl.jfoix.atm.comun.service.IProductoService;

@ViewScoped
@ManagedBean(name="productoMB")
public class ProductoMB implements Serializable {

	private static final long serialVersionUID = 5134194114190172477L;

	@ManagedProperty(value="#{productoService}")
	private IProductoService productoService;
	
	@ManagedProperty(value="#{marcaService}")
	private IMarcaService marcaService;
	
	private Producto producto;
	
	private List<Marca> marcas;
	private List<Producto> productos;
	
	private String productoCode;
	private String productoDesc;
	private Integer productoIdMarca;

	@PostConstruct
	public void init(){
		marcas = marcaService.buscarTodasMarcas();

		producto = new Producto();
		producto.setMarca(new Marca());
	}

	public void nuevoProducto(){
		this.producto = new Producto();
		this.producto.setEstado(true);
		this.producto.setMarca(new Marca());
	}
	
	public void actualizarProducto(){
		if(this.producto.getMarca() == null){
			this.producto.setMarca(new Marca());
		}
	}
	
	public void buscarProductos(){
		try{
			productos =  productoService.buscarProductosPorCodigoDescripcionMarca(productoCode, productoDesc, productoIdMarca);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarProducto(){
		try{
			List<FacesMessage> messages = new ArrayList<FacesMessage>();
			
			if(producto.getCodigo() == null || producto.getCodigo().equals("")){
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "Debe ingresar un código para el producto"));
			}
			
			if(producto.getDescripcion() == null || producto.getDescripcion().equals("")){
				messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "Debe ingresar una descripción para el producto"));
			}
			
			if(messages.size() != 0){
				for(FacesMessage message : messages){
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			} else {
			
				String msg = "";
				
				if(producto.getMarca().getIdMarca() == null || producto.getMarca().getIdMarca().equals(0)){
					producto.setMarca(null);
				}
				
				producto.setCodigo(producto.getCodigo().toUpperCase());
				
				if(productos == null){
					productos = new ArrayList<Producto>();
				}
				
				msg = "Modificado con éxito";
				
				if(producto.getIdProducto() == null){
					productos.add(producto);
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
				
				RequestContext.getCurrentInstance().addCallbackParam("done", true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
			
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarProducto(){
		try{
			producto.setEstado(false);
			productoService.guardarProducto(producto);
			productos.remove(producto);
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
}
