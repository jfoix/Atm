package cl.jfoix.atm.ot.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

import cl.jfoix.atm.admin.service.IUsuarioService;
import cl.jfoix.atm.comun.entity.EstadoTrabajo;
import cl.jfoix.atm.comun.entity.MantencionProgramada;
import cl.jfoix.atm.comun.entity.MantencionProgramadaTrabajo;
import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.seguridad.proveedor.UsuarioAtenticacion;
import cl.jfoix.atm.comun.service.IMantencionProgramadaService;
import cl.jfoix.atm.comun.service.IMarcaService;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.comun.service.IProductoService;
import cl.jfoix.atm.comun.service.ITrabajoService;
import cl.jfoix.atm.comun.service.ITrabajoSubTipoService;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;
import cl.jfoix.atm.comun.util.ValidacionesUtil;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.ot.dto.TrabajoProductoDto;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.entity.MarcaVehiculo;
import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenDocumento;
import cl.jfoix.atm.ot.entity.OrdenObservacion;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoEstado;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.entity.Vehiculo;
import cl.jfoix.atm.ot.entity.VehiculoOrden;
import cl.jfoix.atm.ot.filter.BuscarTrabajoFiltro;
import cl.jfoix.atm.ot.service.IClienteService;
import cl.jfoix.atm.ot.service.IMarcaVehiculoService;
import cl.jfoix.atm.ot.service.IOrdenTrabajoService;
import cl.jfoix.atm.ot.service.IVehiculoService;
import cl.jfoix.atm.ot.util.TipoOrdenObservacionEnum;

@ViewScoped
@ManagedBean(name="crearOrdenTrabajoMB")
public class CrearOrdenTrabajoMB implements Serializable {

	private static final long serialVersionUID = 6978751292967227293L;

	@ManagedProperty(value="#{ordenTrabajoService}")
	private IOrdenTrabajoService ordenTrabajoService;
	
	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;

	@ManagedProperty(value="#{trabajoService}")
	private ITrabajoService trabajoService;
	
	@ManagedProperty(value="#{marcaVehiculoService}")
	private IMarcaVehiculoService marcaVehiculoService;
	
	@ManagedProperty(value="#{vehiculoService}")
	private IVehiculoService vehiculoService;
	
	@ManagedProperty(value="#{clienteService}")
	private IClienteService clienteService;
	
	@ManagedProperty(value="#{trabajoTipoService}")
	private ITrabajoTipoService trabajoTipoService;
	
	@ManagedProperty(value="#{trabajoSubTipoService}")
	private ITrabajoSubTipoService trabajoSubTipoService;

	@ManagedProperty(value="#{marcaService}")
	private IMarcaService marcaService;
	
	@ManagedProperty(value="#{productoService}")
	private IProductoService productoService;
	
	@ManagedProperty(value="#{usuarioService}")
	private IUsuarioService usuarioService;
	
	@ManagedProperty(value="#{mantencionProgramadaService}")
	private IMantencionProgramadaService mantencionProgramadaService;

	private String ordenTrabajoCodigo;
	private boolean crearNuevoCliente;
	private boolean crearNuevoVehiculo;
	
	//Filters
	private BuscarTrabajoFiltro trabajoFiltro;

	private String productoCodigo;
	private String productoDesc;
	private Integer idMarca;

	private String mpCodigo;
	private String mpDescripcion;
	
	//Lists
	private List<MarcaVehiculo> marcasVehiculo;
	private List<Trabajo> trabajos;
	private List<Trabajo> trabajosAgregados;
	private List<TrabajoTipo> trabajoTipos;
	private List<TrabajoSubTipo> trabajoSubTipos;
	private List<MantencionProgramada> mantencionesProgramadas;
	private List<Producto> productos;
	private List<Producto> productosSol;
	private List<Marca> marcas;
	private List<OrdenDocumento> documentos;
	private List<String> mensajesStock;
	
	private OrdenDocumento documento;
	private OrdenObservacion ordenObservacion;
	
	//Objects
	private Orden orden;
	private UploadedFile file;
	private TrabajoProductoDto trabajoProductoDto;
	private MantencionProgramada mantencionProgramada;
	
	private List<Usuario> mecanicos;
	
	private DualListModel<Usuario> dlmMecanicos;

	private TreeNode tnTrabajos;

	//Solicitud Productos
	private String codProductoBusq;
	private String descProductoBusq;
	private List<OrdenTrabajoSolicitudProducto> productosSolicitud;
	private OrdenTrabajoSolicitudProducto productoSolicitud;
	private OrdenTrabajoSolicitud solicitud;
	
	@PostConstruct
	public void init(){
		orden = new Orden();
		orden.setVehiculoOrden(new VehiculoOrden());
		orden.getVehiculoOrden().setCliente(new Cliente());
		orden.getVehiculoOrden().setVehiculo(new Vehiculo());
		orden.getVehiculoOrden().getVehiculo().setMarcaVehiculo(new MarcaVehiculo());
		
		trabajoFiltro = new BuscarTrabajoFiltro(); 
				
		marcasVehiculo = marcaVehiculoService.buscarTodasMarcasVehiculo();
		trabajoTipos = trabajoTipoService.buscarTodosTrabajosTipo();
		
		tnTrabajos = new DefaultTreeNode("root", null);
		
		marcas = marcaService.buscarTodasMarcas();
		
		List<Usuario> source = new ArrayList<Usuario>();
		List<Usuario> target = new ArrayList<Usuario>();
		
		dlmMecanicos = new DualListModel<Usuario>(source, target);
		
		ordenObservacion = new OrdenObservacion();
		
		nuevaSolicitud();
	}
	
	public void nuevoVehiculo(){
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMarcaVehiculo(new MarcaVehiculo());
		vehiculo.setPatente(orden.getVehiculoOrden().getVehiculo().getPatente());
		orden.getVehiculoOrden().setVehiculo(vehiculo);
	}
	
	public void guardarVehiculo(){
		try {
			vehiculoService.guardarVehiculo(orden.getVehiculoOrden().getVehiculo());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Vehiculo almacenado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al almacenar la información, intentelo más tarde"));
		}
	}
	
	public void buscarVehiculo(){
		
		crearNuevoVehiculo = false;
		
		try {
			
			if(orden.getVehiculoOrden().getVehiculo().getPatente() == null || orden.getVehiculoOrden().getVehiculo().getPatente().equals("")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la patente del Vehículo"));
			} else {
				orden.getVehiculoOrden().getVehiculo().setPatente(orden.getVehiculoOrden().getVehiculo().getPatente().toUpperCase());
				Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPatente(orden.getVehiculoOrden().getVehiculo().getPatente());
				
				if(vehiculo != null){
					orden.getVehiculoOrden().setVehiculo(vehiculo);
				} else {
					crearNuevoVehiculo = true;
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		if(documentos == null){
			documentos = new ArrayList<OrdenDocumento>();
		}
		
		OrdenDocumento doc = new OrdenDocumento();
		doc.setDatosArchivo(event.getFile().getContents());
		doc.setNombreArchivo(event.getFile().getFileName());
		doc.setOrden(orden);
		
		documentos.add(doc);
	}
	
	public void elminiarArchivo(){
		documentos.remove(documento);
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
	 
	public void nuevoCliente(){
		Cliente cliente = new Cliente();
		cliente.setRutCliente(orden.getVehiculoOrden().getCliente().getRutCliente());
		orden.getVehiculoOrden().setCliente(cliente);
	}
	
	public void guardarCliente(){
		try {
			orden.getVehiculoOrden().getCliente().setEstado(true);
			clienteService.guardarCliente(orden.getVehiculoOrden().getCliente());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Cliente almacenado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al almacenar la información, intentelo más tarde"));
		}
	}
	
	public void buscarCliente(){
		
		crearNuevoCliente = false;
		
		if(!ValidacionesUtil.validarFormatoRUT(orden.getVehiculoOrden().getCliente().getRutCliente())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "El formato de RUT ingresado es inválido, formato 11222333-4"));
		} else if(!ValidacionesUtil.validarRut(Integer.parseInt(orden.getVehiculoOrden().getCliente().getRutCliente().split("-")[0]), orden.getVehiculoOrden().getCliente().getRutCliente().split("-")[1].charAt(0))){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "El RUT ingresado es inválido"));
		} else {
			try {
				Cliente cliente = clienteService.buscarClientePorRut(orden.getVehiculoOrden().getCliente().getRutCliente());
				
				if(cliente != null){
					orden.getVehiculoOrden().setCliente(cliente);
				} else {
					crearNuevoCliente = true;
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
			}
		}
	}
	
	public void buscarTrabajosSubTipos(AjaxBehaviorEvent event){
		try {
			Integer idTrabajoTipo = (Integer) ((UIOutput)event.getSource()).getValue();
			trabajoSubTipos = trabajoSubTipoService.buscarTrabajoSubTipoPorIdTrabajoTipo(idTrabajoTipo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void buscarTrabajo(){
		try{
			trabajos = trabajoService.buscarTrabajoPorDescripcionCodigo(trabajoFiltro.getDesc(), trabajoFiltro.getCodigo(), trabajoFiltro.getTipo(), trabajoFiltro.getSubTipo());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void buscarMantenciones(){
		try{
			mantencionesProgramadas = mantencionProgramadaService.buscarMantencionProgramadaPorCodigoDescripcion(mpCodigo, mpDescripcion);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void abrirBusquedaMantencionProgramada(){
		mantencionesProgramadas = null;
		mpCodigo = null;
		mpDescripcion = null;
	}
	
	public void agregarTrabajosMantencionSeleccionada(){
		try{
			trabajos = new ArrayList<Trabajo>();
			for(MantencionProgramadaTrabajo mpt : mantencionProgramada.getMantencionTrabajos()){
				if(mpt.getTrabajo().getEstado()){
					Trabajo trabajo = trabajoService.buscarTrabajoPorId(mpt.getTrabajo().getIdTrabajo());
					trabajo.setSeleccionado(true);
					trabajos.add(trabajo);
				}
			}
			agregarTrabajo();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void guardarOT() throws ViewException{
		
		ViewException vEx = new ViewException();
		
		if(orden.getVehiculoOrden().getVehiculo().getIdVehiculo() == null){
			vEx.agregarMensaje("Debe asociar un vehiculo a la orden");
		} else if(orden.getVehiculoOrden().getKilometraje() == null || orden.getVehiculoOrden().getKilometraje().equals(0)){
			vEx.agregarMensaje("Debe ingresar el Kilometraje al vehiculo");
		}

		if(orden.getVehiculoOrden().getCliente().getIdCliente() == null){
			vEx.agregarMensaje("Debe asociar un cliente a la orden");
		}
		
		if(tnTrabajos.getChildren().size() <= 0){
			vEx.agregarMensaje("Debe agregar al menos un trabajo a la OT");
		} else {
			for(TreeNode trabajo : tnTrabajos.getChildren()){
				TrabajoProductoDto trabajoProductoDto = (TrabajoProductoDto) trabajo.getData();
				
				if(trabajoProductoDto.getPrecio().equals(0d)){
					vEx.agregarMensaje("Debe ingresar el valor el Trabajo " + trabajoProductoDto.getTrabajo().getDescripcion());
				}
				
				if(trabajoProductoDto.getHhEstimadas().equals(0d)){
					vEx.agregarMensaje("Debe ingresar la estimación de HH para el Trabajo " + trabajoProductoDto.getTrabajo().getDescripcion());
				}
				
				for(TreeNode producto : trabajo.getChildren()){
					TrabajoProductoDto productDto = (TrabajoProductoDto) producto.getData();
					
					if(productDto.getCantidad().equals(0d)){
						vEx.agregarMensaje("Debe la cantidad para el producto" + productDto.getProducto().getDescripcion());
					}
				}
			}
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		if(ordenObservacion.getObservacion() != null && !ordenObservacion.getObservacion().equals("")){
			List<OrdenObservacion> observaciones = new ArrayList<OrdenObservacion>();
			ordenObservacion.setFechaRegistro(new Date());
			ordenObservacion.setOrden(orden);
			ordenObservacion.setTipoObservacion(TipoOrdenObservacionEnum.INGRESO);
			
			observaciones.add(ordenObservacion);
			
			orden.setOrdenObservaciones(observaciones);
		}
		
		
		List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();
		
		UsuarioAtenticacion auth = (UsuarioAtenticacion) SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getUsuario();

		orden.setUsuario(usuario);
		orden.setFechaOrden(new Date());
		orden.setOrdenDocumentos(documentos);
		
		for(TreeNode trabajo : tnTrabajos.getChildren()){
			
			TrabajoProductoDto trabajoProductoDto = (TrabajoProductoDto) trabajo.getData();
			
			OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
			ordenTrabajo.setOrden(orden);
			ordenTrabajo.setTrabajo(trabajoProductoDto.getTrabajo());

			OrdenTrabajoEstado otEstado = new OrdenTrabajoEstado();
			otEstado.setFechaInicio(new Date());
			otEstado.setOrdenTrabajo(ordenTrabajo);
			otEstado.setEstadoTrabajo(new EstadoTrabajo(1));
			List<OrdenTrabajoEstado> estados = new ArrayList<OrdenTrabajoEstado>();
			estados.add(otEstado);
			ordenTrabajo.setEstadosOrden(estados);
			
			ordenTrabajo.setPrecioManoObra(trabajoProductoDto.getPrecio().intValue());
			ordenTrabajo.setHhEstimada(trabajoProductoDto.getHhEstimadas());
			ordenTrabajo.setFechaInicio(new Date());
			ordenTrabajo.setGarantia(trabajoProductoDto.isGarantia());
			
			if(trabajoProductoDto.getMecanicos() != null && trabajoProductoDto.getMecanicos().size() > 0){
				
				List<OrdenTrabajoUsuario> mecanicos = new ArrayList<OrdenTrabajoUsuario>();
				
				for(Usuario mecanico : trabajoProductoDto.getMecanicos()){
					OrdenTrabajoUsuario otu = new OrdenTrabajoUsuario();
					otu.setUsuario(mecanico);
					otu.setOrdenTrabajo(ordenTrabajo);
					mecanicos.add(otu);
				}
				
				ordenTrabajo.setOrdenTrabajoUsuarios(mecanicos);
			}
			
			for(TreeNode producto : trabajo.getChildren()){
				TrabajoProductoDto productDto = (TrabajoProductoDto) producto.getData();
				
				OrdenTrabajoProducto ordenTrabajoProducto = new OrdenTrabajoProducto();
				ordenTrabajoProducto.setOrdenTrabajo(ordenTrabajo);
				ordenTrabajoProducto.setTraidoCliente(productDto.isProductoCliente());
				ordenTrabajoProducto.setProducto(productDto.getProducto());
				ordenTrabajoProducto.setCantidad(productDto.getCantidad());
				ordenTrabajo.getOrdenTrabajoProductos().add(ordenTrabajoProducto);
			}
			
			ordenesTrabajo.add(ordenTrabajo);
		}
		
		try {
			
			orden.setOrdenTrabajos(ordenesTrabajo);
			
			mensajesStock = new ArrayList<String>();
			
			for(OrdenTrabajo trabajo : orden.getOrdenTrabajos()){
				for(OrdenTrabajoProducto producto : trabajo.getOrdenTrabajoProductos()){
					
					producto.setValor(0);
					
					if(!producto.getTraidoCliente()){
						Stock stock = ordenService.buscarStockPorProducto(producto.getProducto().getIdProducto(), producto.getCantidad(), -1);
						
						if(stock != null){
							Integer valorVenta = ordenService.buscarValorVentaProductoStock(stock.getIdStock());
							producto.setValor(valorVenta);
						} else {
							mensajesStock.add("No hay Stock para el producto " + producto.getProducto().getDescripcion());
						}
					}
				}
			}
			
			ordenService.guardarOrden(orden);
			
			ordenTrabajoCodigo = orden.getIdOrden().toString();
		
			RequestContext.getCurrentInstance().addCallbackParam("done", true);
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}

	public String saltarAAdmOT(){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("admOTMB");
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("idOrden", orden.getIdOrden());
		
		return "admOT";
	}
	
	public void buscarProductosSolicitud(){
		productosSol = ordenTrabajoService.buscarProductos(codProductoBusq, descProductoBusq);
	}
	
	public void nuevaSolicitud(){
		
		productosSol = new ArrayList<Producto>();
		productosSolicitud = new ArrayList<OrdenTrabajoSolicitudProducto>();
		
		solicitud = new OrdenTrabajoSolicitud();
		UsuarioAtenticacion auth = (UsuarioAtenticacion) SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getUsuario();
		
		solicitud.setUsuario(usuario);
	}
	
	public void eliminarProductosSolicitud(){
		productosSolicitud.remove(productoSolicitud);
	}
		
	public void agregarProductosSolicitud(){
		if(productosSolicitud == null){
			productosSolicitud = new ArrayList<OrdenTrabajoSolicitudProducto>();
		}
		
		for(Producto producto : productosSol){
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
		
		productosSol = new ArrayList<Producto>();
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
	
	public void quitarTrabajo(){
		
		List<TreeNode> objToRemove = new ArrayList<TreeNode>();
		
		if(trabajoProductoDto.getProducto() != null){
			TreeNode parentNodeRow = null;
			
			for(TreeNode node : tnTrabajos.getChildren()){
				TrabajoProductoDto tpDto = (TrabajoProductoDto)node.getData();
				if(tpDto.getTrabajo() != null && tpDto.getTrabajo().getIdTrabajo().equals(trabajoProductoDto.getTrabajo().getIdTrabajo())){
					parentNodeRow = node;
				}
			}
			
			for(TreeNode node : parentNodeRow.getChildren()){
				TrabajoProductoDto tpDto = (TrabajoProductoDto)node.getData();
				if(tpDto.getProducto() != null && tpDto.getProducto().getIdProducto().equals(trabajoProductoDto.getProducto().getIdProducto())){
					objToRemove.add(node);
					break;
				}
			}
			
			parentNodeRow.getChildren().remove(objToRemove.get(0));
			
		} else if(trabajoProductoDto.getTrabajo() != null){
			for(TreeNode node : tnTrabajos.getChildren()){
				TrabajoProductoDto wpDto = (TrabajoProductoDto)node.getData();
				if(wpDto.getTrabajo() != null && wpDto.getTrabajo().getIdTrabajo().equals(trabajoProductoDto.getTrabajo().getIdTrabajo())){
					objToRemove.add(node);
				}
			}
			
			for(TreeNode node : objToRemove){
				tnTrabajos.getChildren().remove(node);
			}
			
			Trabajo workDeleted = null;
			for(Trabajo work : this.trabajosAgregados){
				if(work.getIdTrabajo().equals(trabajoProductoDto.getTrabajo().getIdTrabajo())){
					workDeleted = work;
					break;
				}
			}
			
			trabajosAgregados.remove(workDeleted);
		}
	}
	
	public void abrirBusquedaProducto(){
		this.productos = new ArrayList<Producto>();
	}
	
	public void buscarProductos(){
		try{
			productos =  productoService.buscarProductosPorCodigoDescripcionMarca(productoCodigo, productoDesc, idMarca);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void agregarProductos(){
		
		TreeNode workTreeNode = null;
		
		if(trabajoProductoDto.getTrabajo() != null){
			for(TreeNode node : tnTrabajos.getChildren()){
				TrabajoProductoDto wpDto = (TrabajoProductoDto)node.getData();
				if(wpDto.getTrabajo() != null && wpDto.getTrabajo().getIdTrabajo().equals(trabajoProductoDto.getTrabajo().getIdTrabajo())){
					workTreeNode = node;
					break;
				}
			}
			
			if(workTreeNode != null){
				for(Producto product : this.productos){
					if(product.isSeleccionado()){
						
						boolean exist = false;
						
						for(TreeNode nodeProducts : workTreeNode.getChildren()){
							TrabajoProductoDto dto = (TrabajoProductoDto) nodeProducts.getData();
							if(dto.getProducto().getIdProducto().equals(product.getIdProducto())){
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Mensaje", "El producto: "+ product.getCodigo() + " - " + product.getDescripcion() + ", ya se encuentra agregado"));
								exist = true;
								break;
							}
						}
						
						if(!exist){
							new DefaultTreeNode(new TrabajoProductoDto(trabajoProductoDto.getTrabajo(), product, (double)1, null, TrabajoProductoDto.PRODUCT_TYPE), workTreeNode);
						}
					}
				}
			}
		}
	}
	
	public void abrirBusquedaTrabajos(){
		this.trabajos = null;
	}
	
	public void abrirBusquedaMecanicos(){
		mecanicos = usuarioService.buscarUsuariosMecanicos();
		
		List<Usuario> source = new ArrayList<Usuario>();
		List<Usuario> target = new ArrayList<Usuario>();
		
		source.addAll(mecanicos);
		
		if(trabajoProductoDto.getMecanicos() != null && trabajoProductoDto.getMecanicos().size() > 0){
			
			target.addAll(trabajoProductoDto.getMecanicos());
			
			List<Usuario> mecAsignados = new ArrayList<Usuario>();
			
			for(Usuario usuario : mecanicos){
				boolean existe = false;
				for(Usuario usuMec : trabajoProductoDto.getMecanicos()){
					if(usuMec.getNombreUsuario().equals(usuario.getNombreUsuario())){
						existe = true;
						break;
					}
				}
				
				if(existe){
					mecAsignados.add(usuario);
				}
			}
			
			source.removeAll(mecAsignados);
		}
		
		dlmMecanicos = new DualListModel<Usuario>(source, target);
	}
	
	public void guardarAsignacionMecanico(){
		List<Usuario> mecAsignados = new ArrayList<Usuario>();
		
		for(Usuario usuario : dlmMecanicos.getTarget()){
			mecAsignados.add(usuario);
		}
		
		trabajoProductoDto.setMecanicos(mecAsignados);
	}
	
	public void guardarAsignacionMecanicoMasiva(){
		for(TreeNode trabajo : tnTrabajos.getChildren()){
			
			TrabajoProductoDto trabajoProductoDto = (TrabajoProductoDto) trabajo.getData();
			
			List<Usuario> mecAsignados = new ArrayList<Usuario>();
			
			for(Usuario usuario : dlmMecanicos.getTarget()){
				mecAsignados.add(usuario);
			}
			
			trabajoProductoDto.setMecanicos(mecAsignados);
		}
	}
	
	public void agregarTrabajo(){
		
		for(Trabajo work : trabajos){
			if(work.isSeleccionado()){
				
				if(trabajosAgregados == null){
					trabajosAgregados = new ArrayList<Trabajo>();
				}
				
				boolean existWork = false;
				for(Trabajo currWork : trabajosAgregados){
					if(currWork.getIdTrabajo().equals(work.getIdTrabajo())){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Mensaje", "El trabajo " + work.getCodigo() + " ya se encuentra agregado"));
						existWork = true;
						break;
					}
				}
				
				if(existWork){
					continue;
				}
				
				this.trabajosAgregados.add(work);
				
				try{
					TreeNode workTree = new DefaultTreeNode(new TrabajoProductoDto(work, null, (double)1, work.getHhEstimada(), TrabajoProductoDto.WORK_TYPE), tnTrabajos);
					
					List<TrabajoProducto> productos = trabajoService.buscarProductosPorIdTrabajo(work.getIdTrabajo());
					
					for(TrabajoProducto product : productos){
						new DefaultTreeNode(new TrabajoProductoDto(work, product.getProducto(), product.getCantidadProducto(), null, TrabajoProductoDto.PRODUCT_TYPE), workTree);
					}
				
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
				}
			}
		}
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
	 * @return the marcaVehiculoService
	 */
	public IMarcaVehiculoService getMarcaVehiculoService() {
		return marcaVehiculoService;
	}

	/**
	 * @param marcaVehiculoService the marcaVehiculoService to set
	 */
	public void setMarcaVehiculoService(IMarcaVehiculoService marcaVehiculoService) {
		this.marcaVehiculoService = marcaVehiculoService;
	}

	/**
	 * @return the vehiculoService
	 */
	public IVehiculoService getVehiculoService() {
		return vehiculoService;
	}

	/**
	 * @param vehiculoService the vehiculoService to set
	 */
	public void setVehiculoService(IVehiculoService vehiculoService) {
		this.vehiculoService = vehiculoService;
	}

	/**
	 * @return the clienteService
	 */
	public IClienteService getClienteService() {
		return clienteService;
	}

	/**
	 * @param clienteService the clienteService to set
	 */
	public void setClienteService(IClienteService clienteService) {
		this.clienteService = clienteService;
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
	 * @return the ordenTrabajoCodigo
	 */
	public String getOrdenTrabajoCodigo() {
		return ordenTrabajoCodigo;
	}

	/**
	 * @param ordenTrabajoCodigo the ordenTrabajoCodigo to set
	 */
	public void setOrdenTrabajoCodigo(String ordenTrabajoCodigo) {
		this.ordenTrabajoCodigo = ordenTrabajoCodigo;
	}

	/**
	 * @return the crearNuevoCliente
	 */
	public boolean isCrearNuevoCliente() {
		return crearNuevoCliente;
	}

	/**
	 * @param crearNuevoCliente the crearNuevoCliente to set
	 */
	public void setCrearNuevoCliente(boolean crearNuevoCliente) {
		this.crearNuevoCliente = crearNuevoCliente;
	}

	/**
	 * @return the crearNuevoVehiculo
	 */
	public boolean isCrearNuevoVehiculo() {
		return crearNuevoVehiculo;
	}

	/**
	 * @param crearNuevoVehiculo the crearNuevoVehiculo to set
	 */
	public void setCrearNuevoVehiculo(boolean crearNuevoVehiculo) {
		this.crearNuevoVehiculo = crearNuevoVehiculo;
	}

	/**
	 * @return the trabajoFiltro
	 */
	public BuscarTrabajoFiltro getTrabajoFiltro() {
		return trabajoFiltro;
	}

	/**
	 * @param trabajoFiltro the trabajoFiltro to set
	 */
	public void setTrabajoFiltro(BuscarTrabajoFiltro trabajoFiltro) {
		this.trabajoFiltro = trabajoFiltro;
	}

	/**
	 * @return the productoCodigo
	 */
	public String getProductoCodigo() {
		return productoCodigo;
	}

	/**
	 * @param productoCodigo the productoCodigo to set
	 */
	public void setProductoCodigo(String productoCodigo) {
		this.productoCodigo = productoCodigo;
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
	 * @return the marcasVehiculo
	 */
	public List<MarcaVehiculo> getMarcasVehiculo() {
		return marcasVehiculo;
	}

	/**
	 * @param marcasVehiculo the marcasVehiculo to set
	 */
	public void setMarcasVehiculo(List<MarcaVehiculo> marcasVehiculo) {
		this.marcasVehiculo = marcasVehiculo;
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
	 * @return the trabajosAgregados
	 */
	public List<Trabajo> getTrabajosAgregados() {
		return trabajosAgregados;
	}

	/**
	 * @param trabajosAgregados the trabajosAgregados to set
	 */
	public void setTrabajosAgregados(List<Trabajo> trabajosAgregados) {
		this.trabajosAgregados = trabajosAgregados;
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
	 * @return the orden
	 */
	public Orden getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the trabajoProductoDto
	 */
	public TrabajoProductoDto getTrabajoProductoDto() {
		return trabajoProductoDto;
	}

	/**
	 * @param trabajoProductoDto the trabajoProductoDto to set
	 */
	public void setTrabajoProductoDto(TrabajoProductoDto trabajoProductoDto) {
		this.trabajoProductoDto = trabajoProductoDto;
	}

	/**
	 * @return the dlmMecanicos
	 */
	public DualListModel<Usuario> getDlmMecanicos() {
		return dlmMecanicos;
	}

	/**
	 * @param dlmMecanicos the dlmMecanicos to set
	 */
	public void setDlmMecanicos(DualListModel<Usuario> dlmMecanicos) {
		this.dlmMecanicos = dlmMecanicos;
	}

	/**
	 * @return the tnTrabajos
	 */
	public TreeNode getTnTrabajos() {
		return tnTrabajos;
	}

	/**
	 * @param tnTrabajos the tnTrabajos to set
	 */
	public void setTnTrabajos(TreeNode tnTrabajos) {
		this.tnTrabajos = tnTrabajos;
	}

	/**
	 * @return the usuarioService
	 */
	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @return the mecanicos
	 */
	public List<Usuario> getMecanicos() {
		return mecanicos;
	}

	/**
	 * @param mecanicos the mecanicos to set
	 */
	public void setMecanicos(List<Usuario> mecanicos) {
		this.mecanicos = mecanicos;
	}

	/**
	 * @return the mpCodigo
	 */
	public String getMpCodigo() {
		return mpCodigo;
	}

	/**
	 * @param mpCodigo the mpCodigo to set
	 */
	public void setMpCodigo(String mpCodigo) {
		this.mpCodigo = mpCodigo;
	}

	/**
	 * @return the mpDescripcion
	 */
	public String getMpDescripcion() {
		return mpDescripcion;
	}

	/**
	 * @param mpDescripcion the mpDescripcion to set
	 */
	public void setMpDescripcion(String mpDescripcion) {
		this.mpDescripcion = mpDescripcion;
	}

	/**
	 * @return the mantencionProgramadaService
	 */
	public IMantencionProgramadaService getMantencionProgramadaService() {
		return mantencionProgramadaService;
	}

	/**
	 * @param mantencionProgramadaService the mantencionProgramadaService to set
	 */
	public void setMantencionProgramadaService(
			IMantencionProgramadaService mantencionProgramadaService) {
		this.mantencionProgramadaService = mantencionProgramadaService;
	}

	/**
	 * @return the mantencionesProgramadas
	 */
	public List<MantencionProgramada> getMantencionesProgramadas() {
		return mantencionesProgramadas;
	}

	/**
	 * @param mantencionesProgramadas the mantencionesProgramadas to set
	 */
	public void setMantencionesProgramadas(
			List<MantencionProgramada> mantencionesProgramadas) {
		this.mantencionesProgramadas = mantencionesProgramadas;
	}

	/**
	 * @return the mantencionProgramada
	 */
	public MantencionProgramada getMantencionProgramada() {
		return mantencionProgramada;
	}

	/**
	 * @param mantencionProgramada the mantencionProgramada to set
	 */
	public void setMantencionProgramada(MantencionProgramada mantencionProgramada) {
		this.mantencionProgramada = mantencionProgramada;
	}

	/**
	 * @return the documentos
	 */
	public List<OrdenDocumento> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(List<OrdenDocumento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the documento
	 */
	public OrdenDocumento getDocumento() {
		return documento;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(OrdenDocumento documento) {
		this.documento = documento;
	}

	/**
	 * @return the mensajesStock
	 */
	public List<String> getMensajesStock() {
		return mensajesStock;
	}

	/**
	 * @param mensajesStock the mensajesStock to set
	 */
	public void setMensajesStock(List<String> mensajesStock) {
		this.mensajesStock = mensajesStock;
	}

	/**
	 * @return the ordenObservacion
	 */
	public OrdenObservacion getOrdenObservacion() {
		return ordenObservacion;
	}

	/**
	 * @param ordenObservacion the ordenObservacion to set
	 */
	public void setOrdenObservacion(OrdenObservacion ordenObservacion) {
		this.ordenObservacion = ordenObservacion;
	}

	/**
	 * @return the productosSol
	 */
	public List<Producto> getProductosSol() {
		return productosSol;
	}

	/**
	 * @param productosSol the productosSol to set
	 */
	public void setProductosSol(List<Producto> productosSol) {
		this.productosSol = productosSol;
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
	 * @return the productoSolicitud
	 */
	public OrdenTrabajoSolicitudProducto getProductoSolicitud() {
		return productoSolicitud;
	}

	/**
	 * @param productoSolicitud the productoSolicitud to set
	 */
	public void setProductoSolicitud(OrdenTrabajoSolicitudProducto productoSolicitud) {
		this.productoSolicitud = productoSolicitud;
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
}
