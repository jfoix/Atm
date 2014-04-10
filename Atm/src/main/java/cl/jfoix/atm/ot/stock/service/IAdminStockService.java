package cl.jfoix.atm.ot.stock.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.ot.entity.Bodega;
import cl.jfoix.atm.ot.entity.Movimiento;
import cl.jfoix.atm.ot.entity.Proveedor;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.stock.dto.FiltroBusquedaStockDto;

public interface IAdminStockService {

	List<Bodega> buscarBodegas();
	
	List<Proveedor> buscarProveedores();
	
	List<Producto> buscarProductos();
	
	List<Stock> buscarStock(FiltroBusquedaStockDto filtro);

	void guardarStock(Stock stock, Movimiento movimiento);

	void eliminarStock(Stock stock);

	Stock buscarStockPorIdProducto(Integer idProducto);

	List<Integer> actualizarProductoStockValor(Movimiento movimiento);
}
