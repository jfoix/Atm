package cl.jfoix.atm.comun.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IEstadoOrdenDao;
import cl.jfoix.atm.comun.dao.IOrdenDao;
import cl.jfoix.atm.comun.dao.IOrdenDocumentoDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IFormaPagoDao;
import cl.jfoix.atm.ot.dao.IMovimientoIngresoDao;
import cl.jfoix.atm.ot.dao.IOrdenEstadoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoProductoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoUsuarioDao;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.dao.IVehiculoOrdenDao;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.entity.EstadoOrden;
import cl.jfoix.atm.ot.entity.FormaPago;
import cl.jfoix.atm.ot.entity.MovimientoIngreso;
import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenDocumento;
import cl.jfoix.atm.ot.entity.OrdenEstado;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuarioPK;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.entity.VehiculoOrden;

@Service("ordenService")
public class OrdenServiceImpl implements IOrdenService {

	@Autowired
	private IFormaPagoDao formaPagoDao;

	@Autowired
	private IOrdenDao ordenDao;
	
	@Autowired
	private IStockDao stockDao;
	
	@Autowired
	private IMovimientoIngresoDao movimientoIngresoDao;
	
	@Autowired
	private IOrdenDocumentoDao ordenDocumentoDao;
	
	@Autowired
	private IEstadoOrdenDao estadoOrdenDao;
	
	@Autowired
	private IOrdenEstadoDao ordenEstadoDao;
	
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
						if(ordenTrabajo.getIdOrdenTrabajo() != null){
							List<Filtro> filtros = new ArrayList<Filtro>();
							filtros.add(new Filtro("c.pk.idOrdenTrabajo", TipoOperacionFiltroEnum.EQUAL, ordenTrabajo.getIdOrdenTrabajo()));
							
							ordenTrabajoUsuarioDao.eliminarPorFiltros(filtros);
						}
						
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
				
				String ruta = "D:\\ArchivosAtm\\upload\\" + orden.getIdOrden().toString();
				
				File file = new File(ruta);
				
				if(!file.exists()){
					file.mkdirs();
				}
				
				if(orden.getOrdenDocumentos() != null){
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
					
					if(ordenTrabajo.getOrdenTrabajoUsuarios() != null && ordenTrabajo.getOrdenTrabajoUsuarios().size() > 0){
						if(ordenTrabajo.getIdOrdenTrabajo() != null){
							List<Filtro> filtros = new ArrayList<Filtro>();
							filtros.add(new Filtro("c.pk.idOrdenTrabajo", TipoOperacionFiltroEnum.EQUAL, ordenTrabajo.getIdOrdenTrabajo()));
							
							ordenTrabajoUsuarioDao.eliminarPorFiltros(filtros);
						}
						
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
			}
		}catch(DaoException e){
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Transactional
	@Override
	public void guardarEstadoOrden(OrdenEstado ordenEstado, FormaPago formaPago) throws ViewException {
		try {
			
			OrdenEstado ordenEstadoActual = ordenEstadoDao.buscarUltimoEstadoOrden(ordenEstado.getOrden().getIdOrden());
			ordenEstadoActual.setFechaTermino(ordenEstado.getFechaInicio());
			
			ordenEstadoDao.guardar(ordenEstadoActual);
			ordenEstadoDao.guardar(ordenEstado);
			
			if(formaPago != null){
				Orden ordenActual = ordenDao.buscarPorId(ordenEstado.getOrden().getIdOrden());
				ordenActual.setFormaPago(formaPago);
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
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.movimiento.stock.idStock", TipoOperacionFiltroEnum.EQUAL, idStock));
			filtros.add(new Filtro("c.cantidad", TipoOperacionFiltroEnum.MAYOR_QUE, 0d));
			
			List<MovimientoIngreso> movimientosIngreso = movimientoIngresoDao.buscarPorFiltros(filtros, "c.valorVenta DESC");
			
			if(movimientosIngreso != null && movimientosIngreso.size() > 0){
				return movimientosIngreso.get(0).getValorVenta();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
