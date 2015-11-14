package cl.jfoix.atm.ot.stock.service;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.ProductoGrupo;
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

	List<Movimiento> buscarMovimientoPorRangoFechasCodigoProducto(Date fechaDesde, Date fechaHasta, String codProducto, Integer idProveedor);

	List<ProductoGrupo> buscarProductoGrupo();

	List<Marca> buscarMarcas();

	List<Producto> buscarProductosFiltros(String desc, Integer idGrupo,
			Integer idMarca);

	List<Movimiento> buscarMovimientoPorIdStock(Integer idStock);

	void modificarStock(Stock stock);

	Stock buscarStockPorIdProductoIdBodega(Integer idProducto, Integer idBodega);

	List<Producto> buscarProductosPorGrupo(Integer idProductoGrupo, String nombreProd, String codigo);
}
