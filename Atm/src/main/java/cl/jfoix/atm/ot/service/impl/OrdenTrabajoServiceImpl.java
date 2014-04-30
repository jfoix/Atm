package cl.jfoix.atm.ot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IEstadoTrabajoDao;
import cl.jfoix.atm.comun.dao.IProductoDao;
import cl.jfoix.atm.comun.entity.EstadoTrabajo;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IMovimientoDao;
import cl.jfoix.atm.ot.dao.IMovimientoIngresoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudProductoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoUsuarioDao;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.MovimientoIngreso;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoEstado;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProductoPK;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.service.IOrdenTrabajoService;
import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;

@Service("ordenTrabajoService")
public class OrdenTrabajoServiceImpl implements IOrdenTrabajoService {

	@Autowired
	private IOrdenTrabajoDao ordenTrabajoDao;
	
	@Autowired
	private IStockDao stockDao;
	
	@Autowired
	private IMovimientoDao movimientoDao;

	@Autowired
	private IMovimientoIngresoDao movimientoIngresoDao;

	@Autowired
	private IOrdenTrabajoSolicitudDao ordenTrabajoSolicitudDao;
	
	@Autowired
	private IOrdenTrabajoSolicitudProductoDao ordenTrabajoSolicitudProductoDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IOrdenTrabajoUsuarioDao ordenTrabajoUsuarioDao;
	
	@Autowired
	private IEstadoTrabajoDao estadoTrabajoDao;

	@Transactional
	@Override
	public void gaurdarOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ViewException {
		try {
			if(ordenTrabajo.getIdOrdenTrabajo() == null){
				ordenTrabajoDao.guardar(ordenTrabajo);
			} else {
				ordenTrabajoDao.modificar(ordenTrabajo);
			}
		} catch (DaoException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}
	
	@Transactional
	@Override
	public void guardarOrdenTrabajoSolicitud(OrdenTrabajoSolicitud solicitud) throws ViewException {
		try {
			ordenTrabajoSolicitudDao.guardar(solicitud);
			
			for(OrdenTrabajoSolicitudProducto otsp : solicitud.getProductos()){
				OrdenTrabajoSolicitudProductoPK pk = new OrdenTrabajoSolicitudProductoPK();
				pk.setIdOrdenTrabajoSolicitud(solicitud.getIdOrdenTrabajoSolicitud());
				pk.setIdProducto(otsp.getProducto().getIdProducto());
				otsp.setPk(pk);
				
				ordenTrabajoSolicitudProductoDao.guardar(otsp);
			}
		} catch (DaoException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}
	
	@Override
	@Transactional
	public OrdenTrabajo cambiarEstadoOrdenTrabajo(EstadoTrabajo estadoTrabajo, Integer idOrdenTrabajo) throws ViewException {
		try {
			OrdenTrabajo ot = ordenTrabajoDao.buscarPorId(idOrdenTrabajo);
			
			Date fechaEstado = new Date();
			
			OrdenTrabajoEstado otEstado = new OrdenTrabajoEstado();
			otEstado.setFechaInicio(fechaEstado);
			otEstado.setOrdenTrabajo(ot);
			otEstado.setEstadoTrabajo(estadoTrabajo);
			ot.getEstadosOrden().get(ot.getEstadosOrden().size() - 1).setFechaTermino(fechaEstado);
			ot.getEstadosOrden().add(otEstado);
			
			if(!estadoTrabajo.getIdEstadoTrabajo().equals(2)){
				otEstado.setFechaTermino(new Date());
				ot.setFechaTermino(new Date());
			}
			
			if(estadoTrabajo.getIdEstadoTrabajo().equals(3)){
				
				for(OrdenTrabajoProducto otp : ot.getOrdenTrabajoProductos()){
				
					if(!otp.getTraidoCliente()){
						Movimiento mov = new Movimiento();
						mov.setFecha(new Date());
						mov.setTipo(TipoMovimientoEnum.EGRESO);
						
						List<Filtro> filtros = new ArrayList<Filtro>();
						filtros.add(new Filtro("c.producto.idProducto", TipoOperacionFiltroEnum.EQUAL, otp.getProducto().getIdProducto()));
						
						List<Stock> stocks = stockDao.buscarPorFiltros(filtros, null);
						
						if(stocks != null && stocks.size() > 0){
							Stock stock = stocks.get(0);
							stock.setCantidad(stock.getCantidad() - otp.getCantidad());
							
							mov.setStock(stock);
							mov.setCantidad(otp.getCantidad());
							mov.setValorUnidad(otp.getValor());
							
							movimientoDao.guardar(mov);
							
							stockDao.modificar(stock);
							
							List<Filtro> filtrosMov = new ArrayList<Filtro>();
							filtrosMov.add(new Filtro("c.movimiento.stock.idStock", TipoOperacionFiltroEnum.EQUAL, stock.getIdStock()));
							filtrosMov.add(new Filtro("c.cantidad", TipoOperacionFiltroEnum.MAYOR_QUE, 0d));
							
							List<MovimientoIngreso> movsIngreso = movimientoIngresoDao.buscarPorFiltros(filtrosMov, null);
							
							Double cantidadAux = otp.getCantidad();
							
							while(cantidadAux > 0){
								for(MovimientoIngreso movIngreso : movsIngreso){
									if(movIngreso.getCantidad() > cantidadAux){
										movIngreso.setCantidad(movIngreso.getCantidad() -  cantidadAux);
										cantidadAux = 0d;
									} else {
										cantidadAux = cantidadAux - movIngreso.getCantidad();
										movIngreso.setCantidad(0d);
									}
									
									movimientoIngresoDao.modificar(movIngreso);
									
									if(cantidadAux.equals(0)){
										break;
									}
								}
							}
						}
					}
				}
			}
			
			ordenTrabajoDao.modificar(ot);
			ot.getOrdenTrabajoProductos().size();
			
			return ot;
		} catch (DaoException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}
	
	@Override
	@Transactional
	public List<Producto> buscarProductos(String codProducto, String descProducto){
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, codProducto));
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, descProducto));
			
			return productoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Transactional
	@Override
	public List<OrdenTrabajoUsuario> buscarTrabajosUsuario(String nombreUsuario, Integer idOT, Date fecha, Integer idEstadoTrabajo) {
		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.usuario.nombreUsuario", TipoOperacionFiltroEnum.EQUAL, nombreUsuario));
			
			if(fecha != null){
				filtros.add(new Filtro("c.ordenTrabajo.fechaInicio", TipoOperacionFiltroEnum.MAYOR_IGUAL_QUE, fecha));
			}
			
			if(idEstadoTrabajo != null && !idEstadoTrabajo.equals(-1)){
				filtros.add(new Filtro("estOrden.estadoTrabajo.idEstadoTrabajo", TipoOperacionFiltroEnum.EQUAL, idEstadoTrabajo));
			}
			
			if(idOT != null && !idOT.equals(0)){
				filtros.add(new Filtro("c.ordenTrabajo.orden.idOrden", TipoOperacionFiltroEnum.EQUAL, idOT));
			}
			
			filtros.add(new Filtro("estOrden.fechaTermino", TipoOperacionFiltroEnum.IS_NULL, null));
			filtros.add(new Filtro("ordenEstado.estadoOrden.idEstadoOrden", TipoOperacionFiltroEnum.NOT_IN, new Integer[] { 6, 7, 8}));
			filtros.add(new Filtro("ordenEstado.fechaTermino", TipoOperacionFiltroEnum.IS_NULL, null));
			
			List<OrdenTrabajoUsuario> trabajos = ordenTrabajoUsuarioDao.buscarPorFiltros(filtros, null, "JOIN c.ordenTrabajo.estadosOrden estOrden", "JOIN c.ordenTrabajo.orden.ordenEstados ordenEstado");
			
			for(OrdenTrabajoUsuario otUsuario : trabajos){
				otUsuario.getOrdenTrabajo().getOrdenTrabajoProductos().size();
				otUsuario.getOrdenTrabajo().getEstadosOrden().size();
			}
			
			return trabajos;
		} catch(DaoException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public List<EstadoTrabajo> obtenerEstadosTrabajo() {
		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return estadoTrabajoDao.buscarPorFiltros(filtros, null);
		} catch(DaoException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
