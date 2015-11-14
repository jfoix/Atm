package cl.jfoix.atm.comun.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IEstadoOrdenDao;
import cl.jfoix.atm.comun.dao.IOrdenDao;
import cl.jfoix.atm.comun.dao.IOrdenDocumentoDao;
import cl.jfoix.atm.comun.dao.IParametroGeneralDao;
import cl.jfoix.atm.comun.entity.ParametroGeneral;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IFormaPagoDao;
import cl.jfoix.atm.ot.dao.IMovimientoDao;
import cl.jfoix.atm.ot.dao.IMovimientoIngresoDao;
import cl.jfoix.atm.ot.dao.IOrdenEstadoDao;
import cl.jfoix.atm.ot.dao.IOrdenObservacionDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoProductoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoUsuarioDao;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.dao.IVehiculoOrdenDao;
import cl.jfoix.atm.ot.dto.ResumentMecanicoDto;
import cl.jfoix.atm.ot.dto.ResumentOTDto;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.entity.EstadoOrden;
import cl.jfoix.atm.ot.entity.FormaPago;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenDocumento;
import cl.jfoix.atm.ot.entity.OrdenEstado;
import cl.jfoix.atm.ot.entity.OrdenObservacion;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuarioPK;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.entity.VehiculoOrden;
import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;
import cl.jfoix.atm.ot.util.TipoOrdenObservacionEnum;

@Service("ordenService")
public class OrdenServiceImpl implements IOrdenService {

	@Autowired
	private IFormaPagoDao formaPagoDao;

	@Autowired
	private IOrdenDao ordenDao;

	@Autowired
	private IOrdenObservacionDao ordenObservacionDao;

	@Autowired
	private IParametroGeneralDao parametroGeneralDao;

	@Autowired
	private IStockDao stockDao;
	
	@Autowired
	private IMovimientoIngresoDao movimientoIngresoDao;
	
	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Autowired
	private IOrdenDocumentoDao ordenDocumentoDao;
	
	@Autowired
	private IEstadoOrdenDao estadoOrdenDao;
	
	@Autowired
	private IOrdenEstadoDao ordenEstadoDao;
	
	@Autowired
	private IOrdenTrabajoDao ordenTrabajoDao;
	
	@Autowired
	private IOrdenTrabajoProductoDao ordenTrabajoProductoDao;
	
	@Autowired
	private IVehiculoOrdenDao vehiculoOrdenDao;

	@Autowired
	private IOrdenTrabajoUsuarioDao ordenTrabajoUsuarioDao;
	
	@Transactional
	@Override
	public void guardarOrden(Orden orden) throws ViewException {
		try{
			if(orden.getIdOrden() == null){
				vehiculoOrdenDao.guardar(orden.getVehiculoOrden());
				ordenDao.guardar(orden);
				
				EstadoOrden estadoIngresado = estadoOrdenDao.buscarPorId(1);
				
				OrdenEstado ordenEstado = new OrdenEstado();
				ordenEstado.setEstadoOrden(estadoIngresado);
				ordenEstado.setFechaInicio(new Date());
				ordenEstado.setOrden(orden);

				ordenEstadoDao.guardar(ordenEstado);
				
				for(OrdenTrabajo ordenTrabajo : orden.getOrdenTrabajos()){
					
					if(ordenTrabajo.getOrdenTrabajoUsuarios() != null && ordenTrabajo.getOrdenTrabajoUsuarios().size() > 0){
//						if(ordenTrabajo.getIdOrdenTrabajo() != null){
//							List<Filtro> filtros = new ArrayList<Filtro>();
//							filtros.add(new Filtro("c.pk.idOrdenTrabajo", TipoOperacionFiltroEnum.EQUAL, ordenTrabajo.getIdOrdenTrabajo()));
//							
//							ordenTrabajoUsuarioDao.eliminarPorFiltros(filtros);
//						}
						
						for(OrdenTrabajoUsuario ordenTrabajoUsuario : ordenTrabajo.getOrdenTrabajoUsuarios()){
							OrdenTrabajoUsuarioPK pk = new OrdenTrabajoUsuarioPK();
							pk.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
							pk.setNombreUsuario(ordenTrabajoUsuario.getUsuario().getNombreUsuario());
							
							OrdenTrabajoUsuario otu = new OrdenTrabajoUsuario();
							otu.setPk(pk);
							
							ordenTrabajoUsuarioDao.guardar(otu);
						}
					}
				}
				
				if(orden.getOrdenDocumentos() != null){

					ParametroGeneral rutaArchivosPG = parametroGeneralDao.buscarPorId("ruta.archivos");
					String ruta = rutaArchivosPG.getValor() + orden.getIdOrden().toString();
					
					File file = new File(ruta);
					
					if(!file.exists()){
						file.mkdirs();
					}
					
					for(OrdenDocumento documento : orden.getOrdenDocumentos()){
						documento.setRutaNombre(ruta + File.separator + documento.getNombreArchivo());
						ordenDocumentoDao.guardar(documento);
						
				        try {
						    FileOutputStream fileOuputStream = new FileOutputStream(documento.getRutaNombre()); 
						    fileOuputStream.write(documento.getDatosArchivo());
						    fileOuputStream.close();
				        }catch(Exception e){
				            e.printStackTrace();
				        }
					}
				}
			} else {
				ordenDao.modificar(orden);
				
				Orden ordenActual = ordenDao.buscarPorId(orden.getIdOrden());
				
				for(int i = 0; i < orden.getOrdenTrabajos().size(); i++){
					orden.getOrdenTrabajos().get(i).setIdOrdenTrabajo(ordenActual.getOrdenTrabajos().get(i).getIdOrdenTrabajo());
				}
				
				for(OrdenTrabajo ordenTrabajo : orden.getOrdenTrabajos()){
					
					List<Filtro> filtros = new ArrayList<Filtro>();
					filtros.add(new Filtro("ordenTrabajo.idOrdenTrabajo", TipoOperacionFiltroEnum.EQUAL, ordenTrabajo.getIdOrdenTrabajo()));
					
					List<OrdenTrabajoUsuario> trabajosMecanicosOld = ordenTrabajoUsuarioDao.buscarPorFiltros(filtros, null); 
					
					if(trabajosMecanicosOld != null){
						for(OrdenTrabajoUsuario otu : trabajosMecanicosOld){
							
							boolean existeMecanico = false;
							
							if(ordenTrabajo.getOrdenTrabajoUsuarios() != null){
								for(OrdenTrabajoUsuario ordenTrabajoUsuario : ordenTrabajo.getOrdenTrabajoUsuarios()){
									if(otu.getUsuario().getNombreUsuario().equals(ordenTrabajoUsuario.getUsuario().getNombreUsuario())){
										existeMecanico = true;
										break;
									}
								}
							}
							
							if(!existeMecanico){
								ordenTrabajoUsuarioDao.eliminar(otu);
							}
						}
					}
					
					if(ordenTrabajo.getOrdenTrabajoUsuarios() != null){
						for(OrdenTrabajoUsuario ordenTrabajoUsuario : ordenTrabajo.getOrdenTrabajoUsuarios()){
							
							boolean existeMecanico = false;
							
							if(trabajosMecanicosOld != null){
								for(OrdenTrabajoUsuario otu : trabajosMecanicosOld){
									if(otu.getUsuario().getNombreUsuario().equals(ordenTrabajoUsuario.getUsuario().getNombreUsuario())){
										existeMecanico = true;
										break;
									}
								}
							}
							
							if(!existeMecanico){
								OrdenTrabajoUsuarioPK pk = new OrdenTrabajoUsuarioPK();
								pk.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
								pk.setNombreUsuario(ordenTrabajoUsuario.getUsuario().getNombreUsuario());
								
								OrdenTrabajoUsuario otu = new OrdenTrabajoUsuario();
								otu.setPk(pk);
								
								ordenTrabajoUsuarioDao.guardar(otu);
							}
						}
					}
				}
			}
		}catch(DaoException e){
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Transactional
	@Override
	public void guardarEstadoOrden(OrdenEstado ordenEstado, FormaPago formaPago, Map<String, String> descuentos) throws ViewException {
		try {
			
			OrdenEstado ordenEstadoActual = ordenEstadoDao.buscarUltimoEstadoOrden(ordenEstado.getOrden().getIdOrden());
			
			if(!ordenEstadoActual.getEstadoOrden().getIdEstadoOrden().equals(ordenEstado.getEstadoOrden().getIdEstadoOrden())){

				ordenEstadoActual.setFechaTermino(ordenEstado.getFechaInicio());
				
				ordenEstadoDao.guardar(ordenEstadoActual);
				ordenEstadoDao.guardar(ordenEstado);
			} else {
				ordenEstado.setIdOrdenEstado(ordenEstadoActual.getIdOrdenEstado());
				ordenEstadoDao.modificar(ordenEstado);
			}
			
			Orden ordenActual = null;
			
			if(formaPago != null){
				ordenActual = ordenDao.buscarPorId(ordenEstado.getOrden().getIdOrden());
				ordenActual.setFormaPago(formaPago);
				
			}
			
			if(descuentos != null){
				if(ordenActual == null){
					ordenActual = ordenDao.buscarPorId(ordenEstado.getOrden().getIdOrden());
				}
				
				String tipoDescRepuesto = descuentos.get("desRepuesto").split("-")[1];
				Double descRepuesto = Double.parseDouble(descuentos.get("desRepuesto").split("-")[0]);
				
				String tipoDescManoObra = descuentos.get("desManoObra").split("-")[1];
				Double descManoObra = Double.parseDouble(descuentos.get("desManoObra").split("-")[0]);
				
				String tipoDescSTercero = descuentos.get("desSTercero").split("-")[1];
				Double descSTercero = Double.parseDouble(descuentos.get("desSTercero").split("-")[0]);
				
				ordenActual.setTipoDescRepuestos(tipoDescRepuesto);
				ordenActual.setTipoDescManoObra(tipoDescManoObra);
				ordenActual.setTipoDescTerceros(tipoDescSTercero);
				
				ordenActual.setDescuentoRepuestos(descRepuesto);
				ordenActual.setDescuentoManoObra(descManoObra);
				ordenActual.setDescuentoTerceros(descSTercero);
			}
			
			if(ordenActual != null){
				ordenDao.modificar(ordenActual);
			}
		} catch (DaoException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}
	
	@Transactional
	@Override
	public void guardarClienteVehiculoOrden(Cliente cliente, Integer idVehiculoOrden) throws ViewException {
		try{
			VehiculoOrden vehiculoOrden = vehiculoOrdenDao.buscarPorId(idVehiculoOrden);
			vehiculoOrden.setCliente(cliente);
		} catch (DaoException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentalo más tarde");
		}
	}
	
	@Transactional
	@Override
	public Orden buscarOrdenPorId(Integer idOrden) {
		try {
			Orden orden = ordenDao.buscarPorId(idOrden);
			if(orden.getOrdenEstados() != null){
				orden.getOrdenEstados().size();
			}
			
			if(orden.getOrdenTrabajos() != null){
				orden.getOrdenTrabajos().size();
			}
			return orden;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public EstadoOrden buscarEstadoOrdenPorId(Integer idEstadoOrden) {
		try {
			return estadoOrdenDao.buscarPorId(idEstadoOrden);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Transactional
	@Override
	public List<Orden> buscarOrdenAdmin(Integer idOrden, String idMecanico, Date fechaInicio, Date fechaTermino, Integer idEstado, String patente) {
		
		try {		
			List<Orden> ordenes = ordenDao.buscarOrdenAdmin(idOrden, idMecanico, fechaInicio, fechaTermino, idEstado, patente);
			
			if(ordenes != null && ordenes.size() > 0){
				for(Orden orden : ordenes){
					if(orden.getOrdenEstados() != null && orden.getOrdenEstados().size() > 0){
						for(OrdenEstado oe : orden.getOrdenEstados()){
							oe.getIdOrdenEstado();
						}
					}
					
					orden.getOrdenTrabajos().size();
					
					for(OrdenTrabajo ot : orden.getOrdenTrabajos()){
						ot.getOrdenTrabajoProductos().size();
						ot.getOrdenTrabajoUsuarios().size();
						ot.getEstadosOrden().size();
					}
				}
			}
			
			return ordenes;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<FormaPago> buscarFormasPago() {
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return formaPagoDao.buscarPorFiltros(filtros, null);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Stock buscarStockPorProducto(Integer idProducto, Double cantidad, Integer idOT){
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.producto.idProducto", TipoOperacionFiltroEnum.EQUAL, idProducto));
			
			List<Stock> stocks = stockDao.buscarPorFiltros(filtros, null);
			
			Stock stockActual = null;
			
			if(stocks != null && stocks.size() > 0){
				stockActual = stocks.get(0);
			}
			
			List<OrdenTrabajoProducto> otProductos = ordenTrabajoProductoDao.buscarOrdenTrabajosPorProductoIdOrdenEnTrabajo(idOT, idProducto);
			
			Double cantidadProductoOT = 0d;
			
			if(otProductos != null){
				for(OrdenTrabajoProducto otp : otProductos){
					cantidadProductoOT += otp.getCantidad();
				}
			}
			
			if(stockActual != null && stockActual.getCantidad() >= cantidadProductoOT && (stockActual.getCantidad() - cantidadProductoOT) >= cantidad){
				return stockActual;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Integer buscarValorVentaProductoStock(Integer idStock){
		try{
//			List<Filtro> filtros = new ArrayList<Filtro>();
//			filtros.add(new Filtro("c.movimiento.stock.idStock", TipoOperacionFiltroEnum.EQUAL, idStock));
//			filtros.add(new Filtro("c.cantidad", TipoOperacionFiltroEnum.MAYOR_QUE, 0d));
//			
//			List<MovimientoIngreso> movimientosIngreso = movimientoIngresoDao.buscarPorFiltros(filtros, "c.valorVenta DESC");
//			
//			if(movimientosIngreso != null && movimientosIngreso.size() > 0){
//				return movimientosIngreso.get(0).getValorVenta();
//			}
			
			ParametroGeneral param = parametroGeneralDao.buscarPorId("stock.movimientos.limit");
			Integer limite = Integer.parseInt(param.getValor());
			
			List<Movimiento> movimientos = movimientoDao.buscarMovimientos(idStock, limite);
			
			double cantidadActual = 1;
			
			Integer mayorPrecioCompra = null;
			
			int i = 0;
			
			Movimiento movCompra = null;
			
			while(i < movimientos.size()){
				
				if(movimientos.get(i).getTipo().equals(TipoMovimientoEnum.INGRESO) && cantidadActual > 0){
					cantidadActual = cantidadActual - movimientos.get(i).getCantidad().doubleValue();
					
					if(mayorPrecioCompra == null){
						mayorPrecioCompra = movimientos.get(i).getValorUnidad();
						movCompra = movimientos.get(i);
					} else {
						if(mayorPrecioCompra.intValue() < movimientos.get(i).getValorUnidad().intValue()){
							mayorPrecioCompra = movimientos.get(i).getValorUnidad();
							movCompra = movimientos.get(i);
						}
					}
					
				} 
				
				i++;
			}
			
			if(movCompra != null){
				int porcProveedor = (int)Math.round(mayorPrecioCompra * (movCompra.getProveedor().getPorcentajeGanancia() / 100));
				int iva = (int)Math.round((porcProveedor + mayorPrecioCompra) * 0.19);
				return porcProveedor + iva + mayorPrecioCompra;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<OrdenObservacion> buscarObservacionesPorIdOrden(Integer idOrden){
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.orden.idOrden", TipoOperacionFiltroEnum.EQUAL, idOrden));
			
			return ordenObservacionDao.buscarPorFiltros(filtros, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public void guardarObservacion(OrdenObservacion observacion){
		try{
			if(observacion.getIdOrdenObservacion() == null){
				observacion.setFechaRegistro(new Date());
				ordenObservacionDao.guardar(observacion);
			} else {
				ordenObservacionDao.modificar(observacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void eliminarObservacion(OrdenObservacion observacion){
		try{
			OrdenObservacion obActal = ordenObservacionDao.buscarPorId(observacion.getIdOrdenObservacion());
			ordenObservacionDao.eliminar(obActal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<OrdenDocumento> buscarDocumentosPorIdOrden(Integer idOrden){
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.orden.idOrden", TipoOperacionFiltroEnum.EQUAL, idOrden));
			
			List<OrdenDocumento> documentos = ordenDocumentoDao.buscarPorFiltros(filtros, null);
			
			if(documentos != null){
				for(OrdenDocumento doc : documentos){
					File archivo = new File(doc.getRutaNombre());
					doc.setDatosArchivo(IOUtils.toByteArray(new FileInputStream(archivo)));
					doc.setNombreArchivo(doc.getRutaNombre().split(Pattern.quote(File.separator))[doc.getRutaNombre().split(Pattern.quote(File.separator)).length - 1]);
				}
			}
			
			return documentos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public void guardarDocumento(OrdenDocumento documento){
		try{
			ordenDocumentoDao.guardar(documento);
			
			ParametroGeneral rutaArchivosPG = parametroGeneralDao.buscarPorId("ruta.archivos");
			String ruta = rutaArchivosPG.getValor() + documento.getOrden().getIdOrden().toString();
			
			File file = new File(ruta);
			
			if(!file.exists()){
				file.mkdirs();
			}
			
			documento.setRutaNombre(ruta + File.separator + documento.getNombreArchivo());
			ordenDocumentoDao.guardar(documento);
			
	        try {
			    FileOutputStream fileOuputStream = new FileOutputStream(documento.getRutaNombre()); 
			    fileOuputStream.write(documento.getDatosArchivo());
			    fileOuputStream.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void eliminarDocumento(OrdenDocumento documento){
		try{
			OrdenDocumento odActal = ordenDocumentoDao.buscarPorId(documento.getIdOrdenDocumento());
			ordenDocumentoDao.eliminar(odActal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@Override
	public byte[] generarResumenOT(Orden ot, Map<String, Boolean> totalesIVA){
		try{
			
			ResumentOTDto resumen = buscarResumenOT(ot);
			resumen.setTotalesIVA(totalesIVA);
			
			List<ResumentOTDto> datos = new ArrayList<ResumentOTDto>();
			datos.add(resumen);
			
			ParametroGeneral pgIVA = parametroGeneralDao.buscarPorId("porcentaje.iva");
			Double porcentajeIVA = 0d;
			
			if(pgIVA != null){
				porcentajeIVA = Double.parseDouble(pgIVA.getValor());
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("IVA", porcentajeIVA);
			
			ParametroGeneral pgRutaReportes = parametroGeneralDao.buscarPorId("ruta.reportes");
			
			JasperPrint jp = JasperFillManager.fillReport(pgRutaReportes.getValor() + "ResumenOT.jasper", params, new JRBeanCollectionDataSource(datos));
			return JasperExportManager.exportReportToPdf(jp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public ResumentOTDto buscarResumenOT(Orden ot){
		
		try{
		
			Orden orden = ordenDao.buscarPorId(ot.getIdOrden());
			orden.getOrdenEstados().size();
			orden.getOrdenTrabajos().size();
			orden.getVehiculoOrden().getVehiculo().getIdVehiculo();
			orden.getVehiculoOrden().getCliente().getIdCliente();
			
			List<OrdenTrabajo> trabajos = new ArrayList<OrdenTrabajo>();
			List<OrdenTrabajo> trabajosServicioTerceros = new ArrayList<OrdenTrabajo>();
			
			ResumentOTDto resumen = new ResumentOTDto();
			resumen.setOrden(orden);
			
			ParametroGeneral param = parametroGeneralDao.buscarPorId("porcentaje.iva");
			resumen.setIva(Double.parseDouble(param.getValor()));
			
			for(OrdenTrabajo trabajo : orden.getOrdenTrabajos()){
				
				if(trabajo.getTrabajo().getTrabajoSubTipo().getExterno()){
					trabajosServicioTerceros.add(trabajo);
				} else {
					trabajos.add(trabajo);
				}
				
				trabajo.getEstadosOrden().size();
				
				trabajo.getOrdenTrabajoProductos().size();
				resumen.agregarProductos(trabajo.getOrdenTrabajoProductos());
			}
			
			resumen.setTrabajos(trabajos);
			resumen.setTrabajosTerceros(trabajosServicioTerceros);
		
			String observacion = "";
			
			if(orden.getOrdenObservaciones() != null){
				for(OrdenObservacion obs : orden.getOrdenObservaciones()){
					if(obs.getTipoObservacion().equals(TipoOrdenObservacionEnum.FINALIZACION)){
						observacion = obs.getObservacion();
						break;
					}
				}
			}
			
			resumen.setObservacion(observacion);
			
			return resumen;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public List<Orden> consultaOTVehiculo(Integer idOrden, String patente){
		
		try{
		
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(idOrden != null && !idOrden.equals(0)){
				filtros.add(new Filtro("c.idOrden", TipoOperacionFiltroEnum.EQUAL, idOrden));
			}
			
			if(patente != null && !patente.equals("")){
				filtros.add(new Filtro("c.vehiculoOrden.vehiculo.patente", TipoOperacionFiltroEnum.EQUAL, patente));
			}
			
			List<Orden> ordenes = ordenDao.buscarPorFiltros(filtros, null);
			
			for(Orden orden : ordenes){
				orden.getOrdenEstados().size();
				for(OrdenTrabajo ot : orden.getOrdenTrabajos()){
					ot.getEstadosOrden().size();
				}
			}
			
			return ordenes;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public List<VehiculoOrden> consultaClientes(String rutCliente, String patente){
		
		try{
		
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			
			if(rutCliente != null && !rutCliente.equals("")){
				filtros.add(new Filtro("c.cliente.rutCliente", TipoOperacionFiltroEnum.EQUAL, rutCliente));
			}
			
			if(patente != null && !patente.equals("")){
				filtros.add(new Filtro("c.vehiculo.patente", TipoOperacionFiltroEnum.EQUAL, patente));
			}
			
			List<VehiculoOrden> veOrd = vehiculoOrdenDao.buscarPorFiltros(filtros, "c.idVehiculoOrden,c.cliente.rutCliente ASC");
			
			for(VehiculoOrden vo : veOrd){
				vo.getOrdenes().size();
			}
			
			return veOrd;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public ResumentMecanicoDto consultaTrabajosMecanico(String nomUsuarioMecanico, Date fechaDesde, Date fechaHasta){
		
		try{
		
			List<Filtro> filtros = new ArrayList<Filtro>();

			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaHasta);
			cal.add(Calendar.HOUR_OF_DAY, 23);
			cal.add(Calendar.MINUTE, 59);
			cal.add(Calendar.SECOND, 59);
			
			filtros.add(new Filtro("c.usuario.nombreUsuario", TipoOperacionFiltroEnum.EQUAL, nomUsuarioMecanico));
			filtros.add(new Filtro("c.ordenTrabajo.orden.fechaOrden", TipoOperacionFiltroEnum.MAYOR_IGUAL_QUE, fechaDesde));
			filtros.add(new Filtro("c.ordenTrabajo.orden.fechaOrden", TipoOperacionFiltroEnum.MENOR_IGUAL_QUE, cal.getTime()));
			
			List<OrdenTrabajoUsuario> ordenTrabajos = ordenTrabajoUsuarioDao.buscarPorFiltros(filtros, null);
			
			
			if(ordenTrabajos != null && ordenTrabajos.size() > 0){
				
				ResumentMecanicoDto resumen = new ResumentMecanicoDto();
				
				resumen.setUsuario(ordenTrabajos.get(0).getUsuario());
				
				for(OrdenTrabajoUsuario otu : ordenTrabajos){
					if(otu.getOrdenTrabajo().getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(1)){
						resumen.getIngresado().add(otu.getOrdenTrabajo());
					} else if(otu.getOrdenTrabajo().getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(2)){
						resumen.getEnProceso().add(otu.getOrdenTrabajo());
					} else if(otu.getOrdenTrabajo().getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(3)){
						resumen.getFinalizados().add(otu.getOrdenTrabajo());
					} else if(otu.getOrdenTrabajo().getUltimoEstado().getEstadoTrabajo().getIdEstadoTrabajo().equals(4)){
						resumen.getCancelados().add(otu.getOrdenTrabajo());
					}
				}
				
				return resumen;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
