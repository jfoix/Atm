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

import org.springframework.security.core.context.SecurityContextHolder;

import cl.jfoix.atm.comun.entity.EstadoTrabajo;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.seguridad.proveedor.UsuarioAtenticacion;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.service.IOrdenTrabajoService;

@ViewScoped
@ManagedBean(name="trabajoMecanicoMB")
public class TrabajoMecanicoMB implements Serializable {

	private static final long serialVersionUID = -8328224553768893369L;

	@ManagedProperty(value="#{ordenTrabajoService}")
	private IOrdenTrabajoService ordenTrabajoService;
	
	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;
	
	private List<OrdenTrabajoUsuario> trabajos;
	private List<EstadoTrabajo> estadosTrabajo;
	private List<Producto> productos;
	private List<OrdenTrabajoSolicitudProducto> productosSolicitud;
	
	private OrdenTrabajoUsuario trabajo;
	private OrdenTrabajoSolicitud solicitud;
	
	private Integer idOTBusq;
	private Integer idEstadoOTBusq;
	private Date fechBusq;
	
	private Integer idEstadoTrabajo;

	private String codProductoBusq;
	private String descProductoBusq;
	
	@PostConstruct
	public void init(){
		UsuarioAtenticacion auth = (UsuarioAtenticacion) SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getUsuario();
		trabajos = ordenTrabajoService.buscarTrabajosUsuario(usuario.getNombreUsuario(), null, new Date(), null);
		
		if(trabajos != null){
			for(OrdenTrabajoUsuario otUsuario : trabajos){
				for(OrdenTrabajoProducto otp : otUsuario.getOrdenTrabajo().getOrdenTrabajoProductos()){
					Stock stock = ordenService.buscarStockPorProducto(otp.getProducto().getIdProducto(), otp.getCantidad(), otUsuario.getOrdenTrabajo().getOrden().getIdOrden());
					otp.setTieneStock(stock != null);
				}
			}
		}
		
		estadosTrabajo = ordenTrabajoService.obtenerEstadosTrabajo();
		solicitud = new OrdenTrabajoSolicitud();
	}
	
	public void buscarTrabajos(){
		UsuarioAtenticacion auth = (UsuarioAtenticacion) SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getUsuario();
		trabajos = ordenTrabajoService.buscarTrabajosUsuario(usuario.getNombreUsuario(), idOTBusq, fechBusq, idEstadoOTBusq);
		
		if(trabajos != null){
			for(OrdenTrabajoUsuario otUsuario : trabajos){
				for(OrdenTrabajoProducto otp : otUsuario.getOrdenTrabajo().getOrdenTrabajoProductos()){
					Stock stock = ordenService.buscarStockPorProducto(otp.getProducto().getIdProducto(), otp.getCantidad(), otUsuario.getOrdenTrabajo().getOrden().getIdOrden());
					otp.setTieneStock(stock != null);
				}
			}
		}
	}
	
	public void seleccionarCambioEstado(){
		idEstadoTrabajo = trabajo.getOrdenTrabajo().getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo();
	}
	
	public void buscarProductos(){
		productos = ordenTrabajoService.buscarProductos(codProductoBusq, descProductoBusq);
	}
	
	public void guardarEstadoOrdenTrabajo() throws ViewException{
		EstadoTrabajo estTrabajo = new EstadoTrabajo();
		
		for(EstadoTrabajo estado : estadosTrabajo){
			if(estado.getIdEstadoTrabajo().equals(idEstadoTrabajo)){
				estTrabajo.setDescripcion(estado.getDescripcion());
				break;
			}
		}
		
		estTrabajo.setIdEstadoTrabajo(idEstadoTrabajo);
		
		ordenTrabajoService.cambiarEstadoOrdenTrabajo(estTrabajo, trabajo.getOrdenTrabajo().getIdOrdenTrabajo());
		
		buscarTrabajos();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Cambio de estado guardado correctamente"));
	}

	public void nuevaSolicitud(){
		
		productos = new ArrayList<Producto>();
		productosSolicitud = new ArrayList<OrdenTrabajoSolicitudProducto>();
		
		solicitud = new OrdenTrabajoSolicitud();
		UsuarioAtenticacion auth = (UsuarioAtenticacion) SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getUsuario();
		
		solicitud.setUsuario(usuario);
		solicitud.setOrdenTrabajo(trabajo.getOrdenTrabajo());
	}
	
	public void agregarProductos(){
		if(productosSolicitud == null){
			productosSolicitud = new ArrayList<OrdenTrabajoSolicitudProducto>();
		}
		
		for(Producto producto : productos){
			if(producto.isSeleccionado()){
				
				boolean existeProducto = false;
				
				for(OrdenTrabajoSolicitudProducto productoSolicitud:  productosSolicitud){
					if(productoSolicitud.getProducto().getIdProducto().equals(producto.getIdProducto())){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje", "El producto " + producto.getDescripcion() + " ya se encuentra agregado a la solicitud"));
						existeProducto = true;
						break;
					}
				}
				
				if(!existeProducto){
					OrdenTrabajoSolicitudProducto solicitudProducto = new OrdenTrabajoSolicitudProducto();
					Producto productoSel = new Producto();
					productoSel.setIdProducto(producto.getIdProducto());
					productoSel.setDescripcion(producto.getDescripcion());
					productoSel.setCodigo(producto.getCodigo());
					solicitudProducto.setProducto(productoSel);
					solicitudProducto.setSolicitud(solicitud);
					solicitudProducto.setEstado(true);
					productosSolicitud.add(solicitudProducto);
				}
			}
		}
		
		productos = new ArrayList<Producto>();
	}
	
	public void guardarSolicitud() throws ViewException{
		solicitud.setProductos(productosSolicitud);
		solicitud.setFechaSolicitud(new Date());
		solicitud.setEstado(true);
		ordenTrabajoService.guardarOrdenTrabajoSolicitud(solicitud);
		solicitud = new OrdenTrabajoSolicitud();
		productosSolicitud = new ArrayList<OrdenTrabajoSolicitudProducto>();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Solicitud guardada correctamente"));
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
	 * @return the trabajos
	 */
	public List<OrdenTrabajoUsuario> getTrabajos() {
		return trabajos;
	}

	/**
	 * @param trabajos the trabajos to set
	 */
	public void setTrabajos(List<OrdenTrabajoUsuario> trabajos) {
		this.trabajos = trabajos;
	}

	/**
	 * @return the estadosTrabajo
	 */
	public List<EstadoTrabajo> getEstadosTrabajo() {
		return estadosTrabajo;
	}

	/**
	 * @param estadosTrabajo the estadosTrabajo to set
	 */
	public void setEstadosTrabajo(List<EstadoTrabajo> estadosTrabajo) {
		this.estadosTrabajo = estadosTrabajo;
	}

	/**
	 * @return the idEstadoTrabajo
	 */
	public Integer getIdEstadoTrabajo() {
		return idEstadoTrabajo;
	}

	/**
	 * @param idEstadoTrabajo the idEstadoTrabajo to set
	 */
	public void setIdEstadoTrabajo(Integer idEstadoTrabajo) {
		this.idEstadoTrabajo = idEstadoTrabajo;
	}

	/**
	 * @return the trabajo
	 */
	public OrdenTrabajoUsuario getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(OrdenTrabajoUsuario trabajo) {
		this.trabajo = trabajo;
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
	 * @return the solicitud
	 */
	public OrdenTrabajoSolicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(OrdenTrabajoSolicitud solicitud) {
		this.solicitud = solicitud;
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
	 * @return the idOTBusq
	 */
	public Integer getIdOTBusq() {
		return idOTBusq;
	}

	/**
	 * @param idOTBusq the idOTBusq to set
	 */
	public void setIdOTBusq(Integer idOTBusq) {
		this.idOTBusq = idOTBusq;
	}

	/**
	 * @return the idEstadoOTBusq
	 */
	public Integer getIdEstadoOTBusq() {
		return idEstadoOTBusq;
	}

	/**
	 * @param idEstadoOTBusq the idEstadoOTBusq to set
	 */
	public void setIdEstadoOTBusq(Integer idEstadoOTBusq) {
		this.idEstadoOTBusq = idEstadoOTBusq;
	}

	/**
	 * @return the fechBusq
	 */
	public Date getFechBusq() {
		return fechBusq;
	}

	/**
	 * @param fechBusq the fechBusq to set
	 */
	public void setFechBusq(Date fechBusq) {
		this.fechBusq = fechBusq;
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
	 * @return the descProductoBusq
	 */
	public String getDescProductoBusq() {
		return descProductoBusq;
	}

	/**
	 * @param descProductoBusq the descProductoBusq to set
	 */
	public void setDescProductoBusq(String descProductoBusq) {
		this.descProductoBusq = descProductoBusq;
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
}
