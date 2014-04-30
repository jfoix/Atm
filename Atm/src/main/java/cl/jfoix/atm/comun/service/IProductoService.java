package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.excepcion.view.ViewException;

public interface IProductoService {

	public void guardarProducto(Producto producto) throws ViewException;
	
	public List<Producto> buscarProductosPorCodigoDescripcionMarca(String codigo, String descripcion, Integer idMarca) throws Exception;

	public List<TrabajoProducto> buascarTrabajoProductosPorCodigoDescripcionMarca(String codigo, String descripcion, Integer idMarca) throws Exception;

	boolean validarProductoPorCodigo(Integer idProducto, String codigoProducto);
}
