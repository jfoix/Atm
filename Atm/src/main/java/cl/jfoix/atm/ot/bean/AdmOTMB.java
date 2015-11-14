package cl.jfoix.atm.ot.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import cl.jfoix.atm.admin.service.IUsuarioService;
import cl.jfoix.atm.comun.entity.EstadoTrabajo;
import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.ProductoGrupo;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.service.IEstadoOrdenService;
import cl.jfoix.atm.comun.service.IMarcaService;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.comun.service.IProductoGrupoService;
import cl.jfoix.atm.comun.service.IProductoService;
import cl.jfoix.atm.comun.service.ITrabajoService;
import cl.jfoix.atm.comun.service.ITrabajoSubTipoService;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;
import cl.jfoix.atm.comun.util.ValidacionesUtil;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.ot.dto.TrabajoProductoDto;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.entity.EstadoOrden;
import cl.jfoix.atm.ot.entity.FormaPago;
import cl.jfoix.atm.ot.entity.MarcaVehiculo;
import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenDocumento;
import cl.jfoix.atm.ot.entity.OrdenEstado;
import cl.jfoix.atm.ot.entity.OrdenObservacion;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoEstado;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.entity.Vehiculo;
import cl.jfoix.atm.ot.entity.VehiculoOrden;
import cl.jfoix.atm.ot.filter.BuscarAdmOrdenFiltro;
import cl.jfoix.atm.ot.filter.BuscarTrabajoFiltro;
import cl.jfoix.atm.ot.service.IClienteService;
import cl.jfoix.atm.ot.service.IMarcaVehiculoService;
import cl.jfoix.atm.ot.service.IOrdenTrabajoService;
import cl.jfoix.atm.ot.service.IVehiculoService;
import cl.jfoix.atm.ot.util.TipoOrdenObservacionEnum;

@ViewScoped
@ManagedBean(name="admOTMB")
public class AdmOTMB implements Serializable {

	private static final long serialVersionUID = 6978751292967227293L;

	@ManagedProperty(value="#{ordenTrabajoService}")
	private IOrdenTrabajoService ordenTrabajoService;
	
	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;

	@ManagedProperty(value="#{estadoOrdenService}")
	private IEstadoOrdenService estadoOrdenService;
	
	@ManagedProperty(value="#{usuarioService}")
	private IUsuarioService usuarioService;
	
	@ManagedProperty(value="#{clienteService}")
	private IClienteService clienteService;

	@ManagedProperty(value="#{vehiculoService}")
	private IVehiculoService vehiculoService;
	
	@ManagedProperty(value="#{marcaVehiculoService}")
	private IMarcaVehiculoService marcaVehiculoService;

	@ManagedProperty(value="#{trabajoTipoService}")
	private ITrabajoTipoService trabajoTipoService;

	@ManagedProperty(value="#{productoGrupoService}")
	private IProductoGrupoService productoGrupoService;

	@ManagedProperty(value="#{trabajoService}")
	private ITrabajoService trabajoService;

	@ManagedProperty(value="#{marcaService}")
	private IMarcaService marcaService;
	
	@ManagedProperty(value="#{trabajoSubTipoService}")
	private ITrabajoSubTipoService trabajoSubTipoService;
	
	@ManagedProperty(value="#{productoService}")
	private IProductoService productoService;
	
	private String ordenTrabajoCodigo;
	
	//Filters
	private BuscarAdmOrdenFiltro filtroBusqueda;
	
	//Lists
	private List<Orden> ordenes;
	private List<Producto> productos;
	private List<ProductoGrupo> gruposProducto;
	private List<EstadoOrden> estadosOrdenes;
	private List<MarcaVehiculo> marcasVehiculo;
	private List<TrabajoTipo> trabajoTipos;
	private List<Marca> marcas;
	private List<FormaPago> formasPago;
	
	//Objects
	private Orden orden;
	private OrdenObservacion observacion;
	private List<OrdenObservacion> observaciones;
	
	//Vars
	private Integer idNuevoEstadoOrden;
	private String observacionEstado;
	private String productoCodigo;
	private String productoDesc;
	private Integer idMarca;
	private Integer idProductoGrupo;
	private Integer idFormaPago;
	
	//Var Termino
	private double desRepuesto;
	private double desManoObra;
	private double desSTercero;
	
	private String tipoDesRepuesto = "%";
	private String tipoDesManoObra = "%";
	private String tipoDesSTercero = "%";

	private boolean ivaRepuesto;
	private boolean ivaManoObra;
	private boolean ivaSTercero;
	
	//Vars Cliente
	private boolean crearNuevoCliente;
	
	private List<Usuario> mecanicos;
	private DualListModel<Usuario> dlmMecanicos;

	//Vars Vehiculo
	private boolean crearNuevoVehiculo;
	
	private TreeNode tnTrabajos;
	private TrabajoProductoDto trabajoProductoDto;
	private List<Trabajo> trabajosAgregados;
	private List<Trabajo> trabajos;
	private List<TrabajoSubTipo> trabajoSubTipos;
	private BuscarTrabajoFiltro trabajoFiltro;

	//Vars Doc
	private List<OrdenDocumento> documentos;
	private OrdenDocumento documento;
	
	public AdmOTMB(){
		
		filtroBusqueda = new BuscarAdmOrdenFiltro();
		
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("idOrden") != null){
			Integer idOrden = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("idOrden").toString());
			filtroBusqueda.setIdOrden(idOrden);
		}
	}
	
	@PostConstruct
	public void init(){
		
		if(filtroBusqueda != null && filtroBusqueda.getIdOrden() != null && !filtroBusqueda.getIdOrden().equals(0)){
			Orden orden = ordenService.buscarOrdenPorId(filtroBusqueda.getIdOrden());
			
			ordenes = new ArrayList<Orden>();
			ordenes.add(orden);
		}
		
		marcasVehiculo = marcaVehiculoService.buscarTodasMarcasVehiculo();
		mecanicos = usuarioService.buscarUsuariosMecanicos();
		tnTrabajos = new DefaultTreeNode("root", null);
		trabajoTipos = trabajoTipoService.buscarTodosTrabajosTipo();
		marcas = marcaService.buscarTodasMarcas();
		estadosOrdenes = estadoOrdenService.buscarTodosEstadosOrden();
		formasPago = ordenService.buscarFormasPago();
		gruposProducto = productoGrupoService.buscarProductosGrupo();
		
		trabajoFiltro = new BuscarTrabajoFiltro();
		
		orden = new Orden();
		orden.setVehiculoOrden(new VehiculoOrden());
		orden.getVehiculoOrden().setCliente(new Cliente());
		orden.getVehiculoOrden().setVehiculo(new Vehiculo());
		orden.getVehiculoOrden().getVehiculo().setMarcaVehiculo(new MarcaVehiculo());
		
		observacion = new OrdenObservacion();
		
		List<Usuario> source = new ArrayList<Usuario>();
		List<Usuario> target = new ArrayList<Usuario>();
		
		dlmMecanicos = new DualListModel<Usuario>(source, target);
	}
	
	public void abrirObservacionesOT(){
		observaciones = ordenService.buscarObservacionesPorIdOrden(orden.getIdOrden());
		documentos = ordenService.buscarDocumentosPorIdOrden(orden.getIdOrden());
	}
	
	public void nuevaObservacion(){
		observacion = new OrdenObservacion();
	}
	
	public void eliminarObservacion(){
		ordenService.eliminarObservacion(observacion);
		observaciones = ordenService.buscarObservacionesPorIdOrden(orden.getIdOrden());
	}
	
	public void guardarObservacion(){
		
		observacion.setOrden(orden);
		
		if(observacion.getIdOrdenObservacion() == null){
			observacion.setTipoObservacion(TipoOrdenObservacionEnum.EN_TRABAJO);
		}
		
		ordenService.guardarObservacion(observacion);
		
		observaciones = ordenService.buscarObservacionesPorIdOrden(orden.getIdOrden());
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		if(documentos == null){
			documentos = new ArrayList<OrdenDocumento>();
		}
		
		OrdenDocumento doc = new OrdenDocumento();
		doc.setDatosArchivo(event.getFile().getContents());
		doc.setNombreArchivo(event.getFile().getFileName());
		doc.setOrden(orden);
		
		ordenService.guardarDocumento(doc);
		documentos = ordenService.buscarDocumentosPorIdOrden(orden.getIdOrden());
	}
	
	public void elminiarArchivo(){
		ordenService.eliminarDocumento(documento);
		documentos = ordenService.buscarDocumentosPorIdOrden(orden.getIdOrden());
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
	
	public void buscarOrdenes(){
		try {
			this.ordenes = ordenService.buscarOrdenAdmin(filtroBusqueda.getIdOrden(), filtroBusqueda.getIdMecanico(), filtroBusqueda.getFechaInicio(), filtroBusqueda.getFechaTermino(), filtroBusqueda.getIdEstado(), filtroBusqueda.getPatente());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void abrirCambioEstadoOrden(){
		idNuevoEstadoOrden = orden.getUltimoOrdenEstado().getEstadoOrden().getIdEstadoOrden();
		observacionEstado = null;
		
		if(idNuevoEstadoOrden.equals(6)){
			desRepuesto = orden.getDescuentoRepuestos() == null ? 0d : orden.getDescuentoRepuestos();
			desManoObra = orden.getDescuentoManoObra() == null ? 0d : orden.getDescuentoManoObra();
			desSTercero = orden.getDescuentoTerceros() == null ? 0d : orden.getDescuentoTerceros();
			
			tipoDesRepuesto = orden.getTipoDescRepuestos();
			tipoDesManoObra = orden.getTipoDescManoObra();
			tipoDesSTercero = orden.getTipoDescTerceros();
			
			idFormaPago = orden.getFormaPago().getIdFormaPago();
			
			observacionEstado = orden.getUltimoOrdenEstado().getObservacion();
			
		} else {
			desRepuesto = 0d;
			desManoObra = 0d;
			desSTercero = 0d;
			
			tipoDesRepuesto = "%";
			tipoDesManoObra = "%";
			tipoDesSTercero = "%";
		}
	}
	
	public void guardarEstadoOrden(){
		
		try {
			
			EstadoOrden estado = ordenService.buscarEstadoOrdenPorId(idNuevoEstadoOrden);

			OrdenEstado ordenEstado = new OrdenEstado();
			ordenEstado.setEstadoOrden(estado);
			ordenEstado.setFechaInicio(new Date());
			ordenEstado.setObservacion(observacionEstado);
			ordenEstado.setOrden(orden);
			
			TipoOrdenObservacionEnum tipoObservacion = TipoOrdenObservacionEnum.CAMBIO_ESTADO;
			
			FormaPago fp = null;
			Map<String, String> descuentos = null;
			
			if(idNuevoEstadoOrden.equals(6)){
				fp = new FormaPago();
				fp.setIdFormaPago(idFormaPago);
				
				descuentos = new HashMap<String, String>();
				
				descuentos.put("desRepuesto", String.valueOf(desRepuesto) + "-" + tipoDesRepuesto);
				descuentos.put("desManoObra", String.valueOf(desManoObra) + "-" + tipoDesManoObra);
				descuentos.put("desSTercero", String.valueOf(desSTercero) + "-" + tipoDesSTercero);
			}
			
			if(estado.getFinalizacion()){
				tipoObservacion = TipoOrdenObservacionEnum.FINALIZACION;
			}
			
			ordenService.guardarEstadoOrden(ordenEstado, fp, descuentos);
			
			OrdenObservacion observacion = null;
			
			if(idNuevoEstadoOrden.equals(ordenEstado.getEstadoOrden().getIdEstadoOrden())){
				observaciones = ordenService.buscarObservacionesPorIdOrden(orden.getIdOrden());
				
				if(observaciones.size() == 0){
					observacion = new OrdenObservacion();
				} else {
					observacion = observaciones.get(observaciones.size() - 1);
				}
				
			} else { 
				observacion = new OrdenObservacion();
			}
		
			observacion.setOrden(orden);
			observacion.setTipoObservacion(tipoObservacion);
			observacion.setObservacion(observacionEstado);
			
			ordenService.guardarObservacion(observacion);
			
			buscarOrdenes();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Cambio de estado guardado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void limpiarVarsConIva(){
		ivaManoObra = true;
		ivaRepuesto = true;
		ivaSTercero = true;
	}
	
	public void generarReporteResumentOT(){
		try{
			
			Map<String, Boolean> totalesIVA = new HashMap<String, Boolean>();
			
			totalesIVA.put("ivaRepuesto", ivaRepuesto);
			totalesIVA.put("ivaManoObra", ivaManoObra);
			totalesIVA.put("ivaSTercero", ivaSTercero);
			
			byte[] reportePDF = ordenService.generarResumenOT(orden, totalesIVA);
			
			if(reportePDF != null){
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.setContentLength(reportePDF.length);
				response.setContentType("application/pdf");
		        response.setHeader("Content-Disposition", "attachment;filename=ResumenOT_" + orden.getIdOrden().toString() + ".pdf");
		        response.getOutputStream().write(reportePDF);
		        response.getOutputStream().flush();
		        response.getOutputStream().close();
		        FacesContext.getCurrentInstance().responseComplete();
			}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void eliminarOrden(){
		
		try {
			
			EstadoOrden estado = new EstadoOrden();
			estado.setIdEstadoOrden(8);
			OrdenEstado ordenEstado = new OrdenEstado();
			ordenEstado.setEstadoOrden(estado);
			ordenEstado.setFechaInicio(new Date());
			ordenEstado.setObservacion("");
			ordenEstado.setOrden(orden);
			
			ordenService.guardarEstadoOrden(ordenEstado, null, null);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Orden eliminada correctamente"));
			
			this.ordenes.remove(orden);
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void buscarValorHHPorCantidad(TrabajoProductoDto trabajoDto){
		Integer precioHH = trabajoDto.getTrabajo().getPrecioManoObra().intValue();
		trabajoDto.setPrecio((long)Math.round(precioHH * trabajoDto.getHhEstimadas()));
	}
	
	public void buscarPrecioPorCantidad(TrabajoProductoDto productoDto){
		
		OrdenTrabajoProducto producto = ordenTrabajoService.buscarOrdenTrabajoProductoPorOrden(productoDto.getOt().getIdOrdenTrabajo(), productoDto.getProducto().getIdProducto());
		
		boolean tieneStock = false;
		
		if(!producto.getTraidoCliente()){
			
			Stock stock = ordenService.buscarStockPorProducto(producto.getProducto().getIdProducto(), producto.getCantidad(), orden.getIdOrden());
			
			if(stock != null){
				Integer valorVenta = ordenService.buscarValorVentaProductoStock(stock.getIdStock());
				producto.setValor(valorVenta);
				tieneStock = true;
			}
		}
		
		productoDto.setTieneStock(tieneStock);
		
		if(tieneStock){
			productoDto.setPrecio((long)Math.round(producto.getValor().longValue() * productoDto.getCantidad()));
		}
	}
	
	public void abrirEditarTrabajo(){
		
		tnTrabajos = new DefaultTreeNode("root", null);
		
		for(OrdenTrabajo trabajo : orden.getOrdenTrabajos()){
			try{
				TrabajoProductoDto tpDto = new TrabajoProductoDto(trabajo, null, (double)1, trabajo.getHhEstimada(), TrabajoProductoDto.WORK_TYPE, trabajo.getGarantia(), false);
				List<Usuario> mecanicos = new ArrayList<Usuario>();
				
				if(trabajo.getOrdenTrabajoUsuarios() != null){
					for(OrdenTrabajoUsuario otUsuario : trabajo.getOrdenTrabajoUsuarios()){
						mecanicos.add(otUsuario.getUsuario());
					}
				}
				
				tpDto.setMecanicos(mecanicos);
				
				TreeNode trabajoArbol = new DefaultTreeNode(tpDto, tnTrabajos);
				
				for(OrdenTrabajoProducto producto : trabajo.getOrdenTrabajoProductos()){

					boolean tieneStock = false;
					
					if(!producto.getTraidoCliente() && (trabajo.getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(1) || trabajo.getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(2))){
						
						Stock stock = ordenService.buscarStockPorProducto(producto.getProducto().getIdProducto(), producto.getCantidad(), orden.getIdOrden());
						
						if(stock != null){
							if(producto.getValor().equals(0)){
								Integer valorVenta = ordenService.buscarValorVentaProductoStock(stock.getIdStock());
								producto.setValor(valorVenta);
							}
							tieneStock = true;
						}
					} else {
						tieneStock = true;
					}
					TrabajoProductoDto tpDtoArbol = new TrabajoProductoDto(producto.getProducto(), producto.getCantidad(), producto.getValor(), null, TrabajoProductoDto.PRODUCT_TYPE, false, producto.getTraidoCliente(), tieneStock);
					tpDtoArbol.setTrabajo(trabajo.getTrabajo());
					tpDtoArbol.setOt(trabajo);
					
					new DefaultTreeNode(tpDtoArbol, trabajoArbol);
				}
			
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
			}
		}
	}
	
	public void verificarProductoCliente(){
		trabajoProductoDto.setPrecio(0l);
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

	public void abrirBusquedaProducto(){
		this.productos = new ArrayList<Producto>();
	}
	
	public void buscarProductos(){
		try{
			productos =  productoService.buscarProductosPorCodigoDescripcionMarca(productoCodigo, productoDesc, idMarca, idProductoGrupo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	public void buscarStock(){
		abrirEditarTrabajo();
	}
	
	public void guardarOT(){

		List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();
		
		for(TreeNode trabajo : tnTrabajos.getChildren()){
			
			TrabajoProductoDto trabajoProductoDto = (TrabajoProductoDto) trabajo.getData();
			
			
			OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
			
			if(trabajoProductoDto.getOt() != null){
				ordenTrabajo = trabajoProductoDto.getOt();
				ordenTrabajo.setHhEstimada(trabajoProductoDto.getHhEstimadas());
				ordenTrabajo.setGarantia(trabajoProductoDto.isGarantia());
				ordenTrabajo.setPrecioManoObra(trabajoProductoDto.getPrecio().intValue());
			} else {
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
			}
			
			
			if(trabajoProductoDto.getMecanicos() != null && trabajoProductoDto.getMecanicos().size() > 0){
				
				List<OrdenTrabajoUsuario> mecanicos = new ArrayList<OrdenTrabajoUsuario>();
				
				if(ordenTrabajo.getOrdenTrabajoUsuarios() != null){
					ordenTrabajo.getOrdenTrabajoUsuarios().clear();
				}
				
				for(Usuario mecanico : trabajoProductoDto.getMecanicos()){
					OrdenTrabajoUsuario otu = new OrdenTrabajoUsuario();
					otu.setUsuario(mecanico);
					otu.setOrdenTrabajo(ordenTrabajo);
					mecanicos.add(otu);
				}
				
				ordenTrabajo.setOrdenTrabajoUsuarios(mecanicos);
			}
			
			ordenTrabajo.getOrdenTrabajoProductos().clear();
			
			for(TreeNode producto : trabajo.getChildren()){
				TrabajoProductoDto productDto = (TrabajoProductoDto) producto.getData();
				
				OrdenTrabajoProducto ordenTrabajoProducto = new OrdenTrabajoProducto();
				ordenTrabajoProducto.setOrdenTrabajo(ordenTrabajo);
				ordenTrabajoProducto.setTraidoCliente(productDto.isProductoCliente());
				ordenTrabajoProducto.setProducto(productDto.getProducto());
				ordenTrabajoProducto.setCantidad(productDto.getCantidad());
				
				if(productDto.getPrecio() != null){
					ordenTrabajoProducto.setValor(productDto.getPrecio().intValue());
				}
				
				ordenTrabajo.getOrdenTrabajoProductos().add(ordenTrabajoProducto);
				
//				Stock stock = ordenService.buscarStockPorProducto(ordenTrabajoProducto.getProducto().getIdProducto(), ordenTrabajoProducto.getCantidad());
//				if(stock != null){
//					if(stock.getCantidad() > ordenTrabajoProducto.getCantidad()){
//						Integer valorVenta = ordenService.buscarValorVentaProductoStock(stock.getIdStock());
//						ordenTrabajoProducto.setValor(valorVenta);
//					}
//				} else {
//					ordenTrabajoProducto.setValor(null);
//				}
			}
			
			ordenesTrabajo.add(ordenTrabajo);
		}
		
		try {
			
			orden.setOrdenTrabajos(ordenesTrabajo);
			ordenService.guardarOrden(orden);
			buscarOrdenes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Orden de Trabajo modificada correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al guardar la información, intentelo más tarde"));
		}
	}
	
	public void agregarProductos(){
		
		TreeNode workTreeNode = null;
		TrabajoProductoDto wpDtoSelec = null;
		if(trabajoProductoDto.getTrabajo() != null){
			for(TreeNode node : tnTrabajos.getChildren()){
				TrabajoProductoDto wpDto = (TrabajoProductoDto)node.getData();
				if(wpDto.getTrabajo() != null && wpDto.getTrabajo().getIdTrabajo().equals(trabajoProductoDto.getTrabajo().getIdTrabajo())){
					workTreeNode = node;
					wpDtoSelec = wpDto;
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
							
							boolean tieneStock = false;
							
							Stock stock = ordenService.buscarStockPorProducto(product.getIdProducto(), 1d, orden.getIdOrden());
							
							Integer valorVenta = 0;
							
							if(stock != null){
								valorVenta = ordenService.buscarValorVentaProductoStock(stock.getIdStock());
								tieneStock = true;
							}
							
							TrabajoProductoDto tpDtoArbol = new TrabajoProductoDto(product, 1d, valorVenta, null, TrabajoProductoDto.PRODUCT_TYPE, false, false, tieneStock);
							
							tpDtoArbol.setTrabajo(wpDtoSelec.getTrabajo());
							tpDtoArbol.setOt(wpDtoSelec.getOt());
							
							new DefaultTreeNode(tpDtoArbol, workTreeNode);
						}
					}
				}
			}
		}
	}

	public void buscarTrabajo(){
		try{
			trabajos = trabajoService.buscarTrabajoPorDescripcionCodigo(trabajoFiltro.getDesc(), trabajoFiltro.getCodigo(), trabajoFiltro.getTipo(), trabajoFiltro.getSubTipo());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
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
				TrabajoProductoDto tpDto = (TrabajoProductoDto)node.getData();
				if(tpDto.getTrabajo() != null && tpDto.getTrabajo().getIdTrabajo().equals(trabajoProductoDto.getTrabajo().getIdTrabajo())){
					objToRemove.add(node);
				}
			}
			
			for(TreeNode node : objToRemove){
				tnTrabajos.getChildren().remove(node);
			}
		}
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
	
	//Vehiculo
	public void guardarVehiculo(){
		try {
			crearNuevoVehiculo = false;
			vehiculoService.guardarVehiculo(orden.getVehiculoOrden().getVehiculo());
			vehiculoService.guardarVehiculoOrden(orden.getVehiculoOrden());
			buscarOrdenes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Vehículo almacenado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al almacenar la información, intentelo más tarde"));
		}
	}
	
	public void buscarVehiculo(){
		
		crearNuevoVehiculo = false;
		
		try {
			orden.getVehiculoOrden().getVehiculo().setPatente(orden.getVehiculoOrden().getVehiculo().getPatente().toUpperCase());
			Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPatente(orden.getVehiculoOrden().getVehiculo().getPatente());
			
			if(vehiculo != null){
				orden.getVehiculoOrden().setVehiculo(vehiculo);
			} else {
				crearNuevoVehiculo = true;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al buscar la información, intentelo más tarde"));
		}
	}
	
	//Cliente
	
	public void nuevoVehiculo(){
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMarcaVehiculo(new MarcaVehiculo());
		vehiculo.setPatente(orden.getVehiculoOrden().getVehiculo().getPatente());
		orden.getVehiculoOrden().setVehiculo(vehiculo);
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
	
	public void nuevoCliente(){
		Cliente cliente = new Cliente();
		cliente.setRutCliente(orden.getVehiculoOrden().getCliente().getRutCliente());
		orden.getVehiculoOrden().setCliente(cliente);
	}
	
	public void guardarNuevoCliente(){
		try {
			orden.getVehiculoOrden().getCliente().setEstado(true);
			clienteService.guardarCliente(orden.getVehiculoOrden().getCliente());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Cliente almacenado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al almacenar la información, intentelo más tarde"));
		}
	}
	
	public void guardarCliente(){
		try {
			ordenService.guardarClienteVehiculoOrden(orden.getVehiculoOrden().getCliente(), orden.getVehiculoOrden().getIdVehiculoOrden());
			buscarOrdenes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Cliente modificado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un problema al almacenar la información, intentelo más tarde"));
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
	 * @return the estadoOrdenService
	 */
	public IEstadoOrdenService getEstadoOrdenService() {
		return estadoOrdenService;
	}

	/**
	 * @param estadoOrdenService the estadoOrdenService to set
	 */
	public void setEstadoOrdenService(IEstadoOrdenService estadoOrdenService) {
		this.estadoOrdenService = estadoOrdenService;
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
	 * @return the filtroBusqueda
	 */
	public BuscarAdmOrdenFiltro getFiltroBusqueda() {
		return filtroBusqueda;
	}

	/**
	 * @param filtroBusqueda the filtroBusqueda to set
	 */
	public void setFiltroBusqueda(BuscarAdmOrdenFiltro filtroBusqueda) {
		this.filtroBusqueda = filtroBusqueda;
	}

	/**
	 * @return the ordenes
	 */
	public List<Orden> getOrdenes() {
		return ordenes;
	}

	/**
	 * @param ordenes the ordenes to set
	 */
	public void setOrdenes(List<Orden> ordenes) {
		this.ordenes = ordenes;
	}

	/**
	 * @return the estadosOrdenes
	 */
	public List<EstadoOrden> getEstadosOrdenes() {
		return estadosOrdenes;
	}

	/**
	 * @param estadosOrdenes the estadosOrdenes to set
	 */
	public void setEstadosOrdenes(List<EstadoOrden> estadosOrdenes) {
		this.estadosOrdenes = estadosOrdenes;
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
	 * @return the idNuevoEstadoOrden
	 */
	public Integer getIdNuevoEstadoOrden() {
		return idNuevoEstadoOrden;
	}

	/**
	 * @param idNuevoEstadoOrden the idNuevoEstadoOrden to set
	 */
	public void setIdNuevoEstadoOrden(Integer idNuevoEstadoOrden) {
		this.idNuevoEstadoOrden = idNuevoEstadoOrden;
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
	 * @return the observacionEstado
	 */
	public String getObservacionEstado() {
		return observacionEstado;
	}

	/**
	 * @param observacionEstado the observacionEstado to set
	 */
	public void setObservacionEstado(String observacionEstado) {
		this.observacionEstado = observacionEstado;
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
	 * @return the formasPago
	 */
	public List<FormaPago> getFormasPago() {
		return formasPago;
	}

	/**
	 * @param formasPago the formasPago to set
	 */
	public void setFormasPago(List<FormaPago> formasPago) {
		this.formasPago = formasPago;
	}

	/**
	 * @return the idFormaPago
	 */
	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	/**
	 * @param idFormaPago the idFormaPago to set
	 */
	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	/**
	 * @return the observacion
	 */
	public OrdenObservacion getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(OrdenObservacion observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the observaciones
	 */
	public List<OrdenObservacion> getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(List<OrdenObservacion> observaciones) {
		this.observaciones = observaciones;
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
	 * @return the grupoProducto
	 */
	public List<ProductoGrupo> getGruposProducto() {
		return gruposProducto;
	}

	/**
	 * @param grupoProducto the grupoProducto to set
	 */
	public void setGruposProducto(List<ProductoGrupo> gruposProducto) {
		this.gruposProducto = gruposProducto;
	}

	/**
	 * @return the desRepuesto
	 */
	public Double getDesRepuesto() {
		return desRepuesto;
	}

	/**
	 * @param desRepuesto the desRepuesto to set
	 */
	public void setDesRepuesto(Double desRepuesto) {
		this.desRepuesto = desRepuesto;
	}

	/**
	 * @return the desManoObra
	 */
	public Double getDesManoObra() {
		return desManoObra;
	}

	/**
	 * @param desManoObra the desManoObra to set
	 */
	public void setDesManoObra(Double desManoObra) {
		this.desManoObra = desManoObra;
	}

	/**
	 * @return the desSTercero
	 */
	public Double getDesSTercero() {
		return desSTercero;
	}

	/**
	 * @param desSTercero the desSTercero to set
	 */
	public void setDesSTercero(Double desSTercero) {
		this.desSTercero = desSTercero;
	}

	/**
	 * @return the tipoDesRepuesto
	 */
	public String getTipoDesRepuesto() {
		return tipoDesRepuesto;
	}

	/**
	 * @param tipoDesRepuesto the tipoDesRepuesto to set
	 */
	public void setTipoDesRepuesto(String tipoDesRepuesto) {
		this.tipoDesRepuesto = tipoDesRepuesto;
	}

	/**
	 * @return the tipoDesManoObra
	 */
	public String getTipoDesManoObra() {
		return tipoDesManoObra;
	}

	/**
	 * @param tipoDesManoObra the tipoDesManoObra to set
	 */
	public void setTipoDesManoObra(String tipoDesManoObra) {
		this.tipoDesManoObra = tipoDesManoObra;
	}

	/**
	 * @return the tipoDesSTercero
	 */
	public String getTipoDesSTercero() {
		return tipoDesSTercero;
	}

	/**
	 * @param tipoDesSTercero the tipoDesSTercero to set
	 */
	public void setTipoDesSTercero(String tipoDesSTercero) {
		this.tipoDesSTercero = tipoDesSTercero;
	}

	/**
	 * @return the ivaRepuesto
	 */
	public boolean isIvaRepuesto() {
		return ivaRepuesto;
	}

	/**
	 * @param ivaRepuesto the ivaRepuesto to set
	 */
	public void setIvaRepuesto(boolean ivaRepuesto) {
		this.ivaRepuesto = ivaRepuesto;
	}

	/**
	 * @return the ivaManoObra
	 */
	public boolean isIvaManoObra() {
		return ivaManoObra;
	}

	/**
	 * @param ivaManoObra the ivaManoObra to set
	 */
	public void setIvaManoObra(boolean ivaManoObra) {
		this.ivaManoObra = ivaManoObra;
	}

	/**
	 * @return the ivaSTercero
	 */
	public boolean isIvaSTercero() {
		return ivaSTercero;
	}

	/**
	 * @param ivaSTercero the ivaSTercero to set
	 */
	public void setIvaSTercero(boolean ivaSTercero) {
		this.ivaSTercero = ivaSTercero;
	}	
}
