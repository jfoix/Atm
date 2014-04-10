package cl.jfoix.atm.ot.stock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IBodegaDao;
import cl.jfoix.atm.comun.dao.IProductoDao;
import cl.jfoix.atm.comun.dao.IProveedorDao;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IMovimientoDao;
import cl.jfoix.atm.ot.dao.IMovimientoIngresoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoProductoDao;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.entity.Bodega;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.MovimientoIngreso;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.stock.dto.FiltroBusquedaStockDto;
import cl.jfoix.atm.ot.stock.service.IAdminStockService;
import cl.jfoix.atm.ot.stock.util.TipoMovimientoEnum;

@Service("adminStockService")
public class AdminStockServiceImpl implements IAdminStockService {

	@Autowired
	private IBodegaDao bodegaDao;

	@Autowired
	private IProveedorDao proveedorDao;

	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IStockDao stockDao;
	
	@Autowired
	private IOrdenTrabajoProductoDao ordenTrabajoProductoDao;
	
	@Autowired
	private IMovimientoDao movimientoDao;

	@Autowired
	private IMovimientoIngresoDao movimientoIngresoDao;

	@Autowired
	private IOrdenService ordenService;
	
	@Override
	public List<Bodega> buscarBodegas() {
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return bodegaDao.buscarPorFiltros(filtros, null);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Proveedor> buscarProveedores() {
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return proveedorDao.buscarPorFiltros(filtros, null);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Producto> buscarProductos() {
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return productoDao.buscarPorFiltros(filtros, null);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Stock buscarStockPorIdProducto(Integer idProducto) {
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.producto.idProducto", TipoOperacionFiltroEnum.EQUAL, idProducto));
			
			List<Stock> stocks = stockDao.buscarPorFiltros(filtros, null);
			
			if(stocks != null && stocks.size() > 0){
				return stocks.get(0);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<Integer> actualizarProductoStockValor(Movimiento movimiento) {
		try{
			List<OrdenTrabajoProducto> ordenProductos = ordenTrabajoProductoDao.buscarOrdenTrabajosPorProductoEnTrabajo(movimiento.getStock().getProducto().getIdProducto());
			
			Double cantidadMovAux = movimiento.getStock().getCantidad();

			List<Integer> otsSinStock = new ArrayList<Integer>();
			
			for(OrdenTrabajoProducto otp : ordenProductos){
				if(!cantidadMovAux.equals(0d) && cantidadMovAux >= otp.getCantidad()){
					Integer valorVenta = ordenService.buscarValorVentaProductoStock(movimiento.getStock().getIdStock());
					otp.setValor(valorVenta);
					
					cantidadMovAux = cantidadMovAux - otp.getCantidad();
					
					ordenTrabajoProductoDao.modificar(otp);
				} else {
					otsSinStock.add(otp.getOrdenTrabajo().getOrden().getIdOrden());
				}
			}
			
			return otsSinStock;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Stock> buscarStock(FiltroBusquedaStockDto filtro) {
		List<Stock> stocks = null;
		
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(filtro.getIdProducto() != null && !filtro.getIdProducto().equals(-1)){
				filtros.add(new Filtro("c.producto.idProducto", TipoOperacionFiltroEnum.EQUAL, filtro.getIdProducto()));
			}
			
			if(filtro.getIdBodega() != null && !filtro.getIdBodega().equals(-1)){
				filtros.add(new Filtro("c.bodega.idBodega", TipoOperacionFiltroEnum.EQUAL, filtro.getIdBodega()));
			}
			
			if(filtros.size() > 0){
				stocks = stockDao.buscarPorFiltros(filtros, null);
			} else {
				stocks = stockDao.buscarTodos();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return stocks;
	}

	@Transactional
	@Override
	public void guardarStock(Stock stock, Movimiento movimiento){
		
		try{
			
			movimiento.setFecha(new Date());
			movimiento.setStock(stock);
			
			if(stock.getIdStock() == null){
				stockDao.guardar(stock);
			} else {
				stockDao.modificar(stock);
			}
			
			movimientoDao.guardar(movimiento);
			
			if(movimiento.getTipo().equals(TipoMovimientoEnum.INGRESO) && movimiento.getCantidad() > 0){
				MovimientoIngreso movIngreso = new MovimientoIngreso();
				movIngreso.setMovimiento(movimiento);
				movIngreso.setValorVenta((int)(((movimiento.getProveedor().getPorcentajeGanancia()/100) + 1) * movimiento.getValorUnidad()));
				movIngreso.setCantidad(movimiento.getCantidad());
				
				movimientoIngresoDao.guardar(movIngreso);
			} else if(movimiento.getTipo().equals(TipoMovimientoEnum.INGRESO) && movimiento.getCantidad() < 0){
				List<Filtro> filtros = new ArrayList<Filtro>();
				filtros.add(new Filtro("c.movimiento.stock.idStock", TipoOperacionFiltroEnum.EQUAL, stock.getIdStock()));
				filtros.add(new Filtro("c.cantidad", TipoOperacionFiltroEnum.MAYOR_QUE, 0));
				
				List<MovimientoIngreso> movsIngreso = movimientoIngresoDao.buscarPorFiltros(filtros, "ORDER BY idMovimientoIngreso ASC");
				
				Double cantidadDescuento = movimiento.getCantidad() * -1;
				
				for(MovimientoIngreso movIng : movsIngreso){
					if(cantidadDescuento > movIng.getCantidad()){
						cantidadDescuento = cantidadDescuento - movIng.getCantidad(); 
						movIng.setCantidad(0d);
						movimientoIngresoDao.modificar(movIng);
					} else {
						movIng.setCantidad(movIng.getCantidad() - cantidadDescuento);
						movimientoIngresoDao.modificar(movIng);
						cantidadDescuento = 0d;
					}
					
					if(cantidadDescuento.equals(0)){
						break;
					}
				}
				movimiento.setTipo(TipoMovimientoEnum.MODIFICACION);
				movimientoDao.modificar(movimiento);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Transactional
	@Override
	public void eliminarStock(Stock stock){
		try{
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			filtros.add(new Filtro("c.stock.idStock", TipoOperacionFiltroEnum.EQUAL, stock.getIdStock()));
			
			movimientoDao.eliminarPorFiltros(filtros);
			
			Stock stockElim = stockDao.buscarPorId(stock.getIdStock());
			
			stockDao.eliminar(stockElim);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
