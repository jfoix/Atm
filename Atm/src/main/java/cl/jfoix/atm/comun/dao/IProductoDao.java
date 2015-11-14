package cl.jfoix.atm.comun.dao;

import java.util.List;

import cl.jfoix.atm.comun.dto.ProductoDto;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.dbutil.dao.IComunDao;

public interface IProductoDao extends IComunDao<Producto, Integer> {

	List<ProductoDto> buscarProductosAgrupados(Integer idOrdenTrabajo);

	List<Producto> buscarProductosPorFiltros(Integer idProducto,
			Integer idProductoGrupo, Integer idMarca, String codigo,
			Boolean estado, String descripcion); 
}