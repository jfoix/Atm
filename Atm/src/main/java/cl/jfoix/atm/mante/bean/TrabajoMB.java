package cl.jfoix.atm.mante.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IMarcaService;
import cl.jfoix.atm.comun.service.IParametroGeneralService;
import cl.jfoix.atm.comun.service.IProductoService;
import cl.jfoix.atm.comun.service.ITrabajoService;
import cl.jfoix.atm.comun.service.ITrabajoSubTipoService;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;

@ViewScoped
@ManagedBean(name="trabajoMB")
public class TrabajoMB implements Serializable {

	private static final long serialVersionUID = 3146972816525029892L;

	@ManagedProperty(value="#{trabajoService}")
	private ITrabajoService trabajoService;
	
	@ManagedProperty(value="#{trabajoSubTipoService}")
	private ITrabajoSubTipoService trabajoSubTipoService;
	
	@ManagedProperty(value="#{trabajoTipoService}")
	private ITrabajoTipoService trabajoTipoService;

	@ManagedProperty(value="#{productoService}")
	private IProductoService productoService;

	@ManagedProperty(value="#{marcaService}")
	private IMarcaService marcaService;
	
	@ManagedProperty(value="#{parametroGeneralService}")
	private IParametroGeneralService parametroGeneralService;

	private String trabajoDesc;
	private String trabajoCode;
	private Integer idTrabajoTipo;
	private Integer idTrabajoSubTipo;
	
	private Integer idTrabajoTipoTrabajo;
	private Integer idTrabajoSubTipoTrabajo;
	
	private String productoCode;
	private String productoDesc;
	private Integer idMarca;
	
	private Trabajo trabajo;
	private TrabajoProducto trabajoProducto;
	
	private List<Trabajo> trabajos;
	private List<TrabajoSubTipo> trabajoSubTipos;
	private List<TrabajoSubTipo> subTiposTrabajo;
	private List<TrabajoTipo> trabajoTipos;
	private List<TrabajoProducto> trabajoProductos;
	private List<Producto> productos;
	private List<Marca> marcas;
	
	@PostConstruct
	public void init(){
		this.trabajoTipos = trabajoTipoService.buscarTodosTrabajosTipo();
		this.marcas = marcaService.buscarTodasMarcas();
		
		this.trabajo = new Trabajo();
		this.trabajo.setTrabajoSubTipo(new TrabajoSubTipo());
	}
	
	public void nuevoTrabajo(){
		this.trabajo = new Trabajo();
		this.trabajo.setEstado(true);
		this.trabajo.setTrabajoSubTipo(new TrabajoSubTipo());
		this.trabajoProductos = new ArrayList<TrabajoProducto>();
		idTrabajoTipoTrabajo = null;
		idTrabajoSubTipoTrabajo = null;
	}
	
	public void buscarTrabajo(){
		try{
			trabajos = trabajoService.buscarTrabajoPorDescripcionCodigo(trabajoDesc, trabajoCode, idTrabajoTipo, idTrabajoSubTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void calcularValorManoObra(){
		Integer valorManoObra = parametroGeneralService.buscarInteger("valor.hh");
		trabajo.setPrecioManoObra((new Double(valorManoObra * trabajo.getHhEstimada())).longValue());
	}
	
	public void buscarTrabajoSubTipos(AjaxBehaviorEvent event){
		try {
			Integer idTrabajoTipo = (Integer) ((UIOutput)event.getSource()).getValue();
			trabajoSubTipos = trabajoSubTipoService.buscarTrabajoSubTipoPorIdTrabajoTipo(idTrabajoTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void buscarTrabajoSubTiposTrabajo(AjaxBehaviorEvent event){
		try {
			Integer idTrabajoTipo = (Integer) ((UIOutput)event.getSource()).getValue();
			this.subTiposTrabajo = trabajoSubTipoService.buscarTrabajoSubTipoPorIdTrabajoTipo(idTrabajoTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	public void editarTrabajo(){
		try {

			trabajoProductos = new ArrayList<TrabajoProducto>();
			
			subTiposTrabajo = trabajoSubTipoService.buscarTrabajoSubTipoPorIdTrabajoTipo(trabajo.getTrabajoSubTipo().getTrabajoTipo().getIdTrabajoTipo());
			idTrabajoTipoTrabajo = trabajo.getTrabajoSubTipo().getTrabajoTipo().getIdTrabajoTipo();
			
			idTrabajoSubTipoTrabajo = trabajo.getTrabajoSubTipo().getIdTrabajoSubTipo();
			
			if(trabajo.getTrabajoProductos() != null && this.trabajo.getTrabajoProductos().size() > 0){
				for(TrabajoProducto tp : this.trabajo.getTrabajoProductos()){
					trabajoProductos.add(tp);
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarTrabajo() throws ViewException {
			
		ViewException vEx = new ViewException();
		
		if(this.trabajo.getCodigo() == null || this.trabajo.getCodigo().equals("")){
			vEx.agregarMensaje("Debe ingresar un código para el Trabajo");
		} else if(!trabajoService.validarTrabajoPorCodigo(trabajo.getIdTrabajo(), trabajo.getCodigo())){
			vEx.agregarMensaje("El código ingresado ya se encuentra asociado a otro trabajo");
		}
		
		if(this.trabajo.getDescripcion() == null || this.trabajo.getDescripcion().equals("")){
			vEx.agregarMensaje("Debe ingresar una descripción para el Trabajo");
		}
		
		if(this.trabajo.getPrecioManoObra() == null || this.trabajo.getPrecioManoObra().equals(new Long(0))){
			vEx.agregarMensaje("Debe ingresar el valor de la mano de obra para el Trabajo");
		}
		
		if(this.trabajo.getHhEstimada() == null || this.trabajo.getHhEstimada().equals(0d)){
			vEx.agregarMensaje("Debe ingresar las HH estimadas para el Trabajo");
		}
		
		if(this.idTrabajoTipoTrabajo.equals(-1)){
			vEx.agregarMensaje("Debe seleccionar un Trabajo Tipo");
		} else if(this.idTrabajoSubTipoTrabajo.equals(-1)){
			vEx.agregarMensaje("Debe seleccionar un Trabajo Sub-Tipo");
		}
		
		if(trabajoProductos != null && trabajoProductos.size() > 0){
			for(TrabajoProducto tp : trabajoProductos){
				if(tp.getCantidadProducto().equals(0d)){
					vEx.agregarMensaje("Debe ingresar una cantidad para el Producto " + tp.getProducto().getDescripcion());
				}
			}
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
			
		try {
			
			String msg = "";

			TrabajoSubTipo trabajoSubTipo = trabajoSubTipoService.buscarTrabajoSubTipo(idTrabajoSubTipoTrabajo);
			
			trabajo.setCodigo(trabajo.getCodigo().toUpperCase());
			trabajo.setTrabajoSubTipo(trabajoSubTipo);
			
			if(trabajo.getIdTrabajo() == null){
				msg = "Ingresado con éxito";
			} else {
				msg = "Modificado con éxito";
			}
			
			trabajo.setTrabajoProductos(trabajoProductos);
			
			trabajoService.guardarTrabajo(trabajo);

			buscarTrabajo();
			
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", msg));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarTrabajo(){
		try {
			this.trabajo.setEstado(false);
			this.trabajoService.guardarTrabajo(trabajo);
			this.trabajos.remove(this.trabajo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	public void abrirBusquedaProductos(){
		this.productos = null;
		this.productoCode = null;
		this.productoDesc = null;
		this.idMarca = null;
	}
	
	public void searchProducts(){
		try{
			productos =  productoService.buscarProductosPorCodigoDescripcionMarca(productoDesc, productoDesc, idMarca);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void eliminarTrabajoProducto(){
		this.trabajoProductos.remove(trabajoProducto);
	}
	
	public void addProduct(){
		
		List<FacesMessage> messages = new ArrayList<FacesMessage>();
		
		if(this.trabajoProductos == null){
			this.trabajoProductos = new ArrayList<TrabajoProducto>();
		}
		
		for(Producto producto : this.productos){
			if(producto.isSeleccionado()){
				
				boolean exist = false;
				
				for(TrabajoProducto trabajoProducto : this.trabajoProductos){
					if(trabajoProducto.getProducto().getIdProducto().equals(producto.getIdProducto())){
						exist = true;
						messages.add(new FacesMessage(FacesMessage.SEVERITY_WARN,"Mensaje", "El producto " + trabajoProducto.getProducto().getDescripcion() + " ya se encuentra en la lista"));
					}
				}
				
				if(!exist){
					TrabajoProducto wp = new TrabajoProducto();
					wp.setProducto(producto);
					trabajoProductos.add(wp);
				}
				
				producto.setSeleccionado(false);
			}
		}
		
		if(messages.size() > 0){
			for(FacesMessage message : messages){
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
	}

	/**
	 * @return the trabajoService
	 */
	public ITrabajoService getTrabajoService() {
		return trabajoService;
	}

	/**
	 * @param trabajoService the trabajoService to set
	 */
	public void setTrabajoService(ITrabajoService trabajoService) {
		this.trabajoService = trabajoService;
	}

	/**
	 * @return the trabajoSubTipoService
	 */
	public ITrabajoSubTipoService getTrabajoSubTipoService() {
		return trabajoSubTipoService;
	}

	/**
	 * @param trabajoSubTipoService the trabajoSubTipoService to set
	 */
	public void setTrabajoSubTipoService(
			ITrabajoSubTipoService trabajoSubTipoService) {
		this.trabajoSubTipoService = trabajoSubTipoService;
	}

	/**
	 * @return the trabajoTipoService
	 */
	public ITrabajoTipoService getTrabajoTipoService() {
		return trabajoTipoService;
	}

	/**
	 * @param trabajoTipoService the trabajoTipoService to set
	 */
	public void setTrabajoTipoService(ITrabajoTipoService trabajoTipoService) {
		this.trabajoTipoService = trabajoTipoService;
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
	 * @return the trabajoDesc
	 */
	public String getTrabajoDesc() {
		return trabajoDesc;
	}

	/**
	 * @param trabajoDesc the trabajoDesc to set
	 */
	public void setTrabajoDesc(String trabajoDesc) {
		this.trabajoDesc = trabajoDesc;
	}

	/**
	 * @return the trabajoCode
	 */
	public String getTrabajoCode() {
		return trabajoCode;
	}

	/**
	 * @param trabajoCode the trabajoCode to set
	 */
	public void setTrabajoCode(String trabajoCode) {
		this.trabajoCode = trabajoCode;
	}

	/**
	 * @return the idTrabajoTipo
	 */
	public Integer getIdTrabajoTipo() {
		return idTrabajoTipo;
	}

	/**
	 * @param idTrabajoTipo the idTrabajoTipo to set
	 */
	public void setIdTrabajoTipo(Integer idTrabajoTipo) {
		this.idTrabajoTipo = idTrabajoTipo;
	}

	/**
	 * @return the idTrabajoSubTipo
	 */
	public Integer getIdTrabajoSubTipo() {
		return idTrabajoSubTipo;
	}

	/**
	 * @param idTrabajoSubTipo the idTrabajoSubTipo to set
	 */
	public void setIdTrabajoSubTipo(Integer idTrabajoSubTipo) {
		this.idTrabajoSubTipo = idTrabajoSubTipo;
	}

	/**
	 * @return the idTrabajoTipoTrabajo
	 */
	public Integer getIdTrabajoTipoTrabajo() {
		return idTrabajoTipoTrabajo;
	}

	/**
	 * @param idTrabajoTipoTrabajo the idTrabajoTipoTrabajo to set
	 */
	public void setIdTrabajoTipoTrabajo(Integer idTrabajoTipoTrabajo) {
		this.idTrabajoTipoTrabajo = idTrabajoTipoTrabajo;
	}

	/**
	 * @return the idTrabajoSubTipoTrabajo
	 */
	public Integer getIdTrabajoSubTipoTrabajo() {
		return idTrabajoSubTipoTrabajo;
	}

	/**
	 * @param idTrabajoSubTipoTrabajo the idTrabajoSubTipoTrabajo to set
	 */
	public void setIdTrabajoSubTipoTrabajo(Integer idTrabajoSubTipoTrabajo) {
		this.idTrabajoSubTipoTrabajo = idTrabajoSubTipoTrabajo;
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
	 * @return the idMarca
	 */
	public Integer getIdMarca() {
		return idMarca;
	}

	/**
	 * @param idMarca the idMarca to set
	 */
	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	/**
	 * @return the trabajo
	 */
	public Trabajo getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

	/**
	 * @return the trabajos
	 */
	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

	/**
	 * @param trabajos the trabajos to set
	 */
	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	/**
	 * @return the trabajoSubTipos
	 */
	public List<TrabajoSubTipo> getTrabajoSubTipos() {
		return trabajoSubTipos;
	}

	/**
	 * @param trabajoSubTipos the trabajoSubTipos to set
	 */
	public void setTrabajoSubTipos(List<TrabajoSubTipo> trabajoSubTipos) {
		this.trabajoSubTipos = trabajoSubTipos;
	}

	/**
	 * @return the subTiposTrabajo
	 */
	public List<TrabajoSubTipo> getSubTiposTrabajo() {
		return subTiposTrabajo;
	}

	/**
	 * @param subTiposTrabajo the subTiposTrabajo to set
	 */
	public void setSubTiposTrabajo(List<TrabajoSubTipo> subTiposTrabajo) {
		this.subTiposTrabajo = subTiposTrabajo;
	}

	/**
	 * @return the trabajoTipos
	 */
	public List<TrabajoTipo> getTrabajoTipos() {
		return trabajoTipos;
	}

	/**
	 * @param trabajoTipos the trabajoTipos to set
	 */
	public void setTrabajoTipos(List<TrabajoTipo> trabajoTipos) {
		this.trabajoTipos = trabajoTipos;
	}

	/**
	 * @return the trabajoProductos
	 */
	public List<TrabajoProducto> getTrabajoProductos() {
		return trabajoProductos;
	}

	/**
	 * @param trabajoProductos the trabajoProductos to set
	 */
	public void setTrabajoProductos(List<TrabajoProducto> trabajoProductos) {
		this.trabajoProductos = trabajoProductos;
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
	 * @return the trabajoProducto
	 */
	public TrabajoProducto getTrabajoProducto() {
		return trabajoProducto;
	}

	/**
	 * @param trabajoProducto the trabajoProducto to set
	 */
	public void setTrabajoProducto(TrabajoProducto trabajoProducto) {
		this.trabajoProducto = trabajoProducto;
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
}
