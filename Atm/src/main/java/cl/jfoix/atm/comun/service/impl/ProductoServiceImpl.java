package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IProductoDao;
import cl.jfoix.atm.comun.dao.ITrabajoProductoDao;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IProductoService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.entity.Stock;

@Service("productoService")
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IStockDao stockDao;
	
	@Autowired
	private ITrabajoProductoDao trabajoProductoDao;

	@Transactional
	@Override
	public void guardarProducto(Producto producto) throws ViewException {
		try {
			if(producto.getIdProducto() == null){
				producto.setEstado(true);
				productoDao.guardar(producto);
			} else {
				productoDao.modificar(producto);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public boolean validarProductoPorCodigo(Integer idProducto, String codigoProducto){
//		try {
//			
//			List<Filtro> filtros = new ArrayList<Filtro>();
//			
//			if(idProducto != null){
//				filtros.add(new Filtro("idProducto", TipoOperacionFiltroEnum.NOT_EQUAL, idProducto));
//			}
//			
//			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, codigoProducto));
//			
//			List<?> resultado  = productoDao.buscarPorFiltros(filtros, null);
//			
//			return resultado == null || resultado.size() == 0;
//		} catch (DaoException e) {
//			e.printStackTrace();
//		}
//		
//		return false;
//	}
	
	@Override
	public boolean validarProductoPorCodigoGrupo(Integer idProducto, Integer idProductoGrupo, String codigoProducto, Integer idMarca){
		try {
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(idProducto != null){
				filtros.add(new Filtro("idProducto", TipoOperacionFiltroEnum.NOT_EQUAL, idProducto));
			}
			
			filtros.add(new Filtro("c.productoGrupo.idProductoGrupo", TipoOperacionFiltroEnum.EQUAL, idProductoGrupo));
			filtros.add(new Filtro("c.marca.idMarca", TipoOperacionFiltroEnum.EQUAL, idMarca));
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, codigoProducto));
			
			List<?> resultado  = productoDao.buscarPorFiltros(filtros, null);
			
			return resultado == null || resultado.size() == 0;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Producto buscarProductoPorCodigo(String codigoProducto){
		try {
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, codigoProducto));
			
			List<Producto> resultado = productoDao.buscarPorFiltros(filtros, null);
			
			if(resultado != null){
				return resultado.get(0);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Producto buscarProductoPorId(Integer idProducto){
		try {
			return productoDao.buscarPorId(idProducto);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Producto> buscarProductosPorCodigoDescripcionMarca(String codigo, String descripcion, Integer idMarca, Integer idProductoGrupo) throws Exception {
		
		try {
//			List<Filtro> filtros = new ArrayList<Filtro>();
//			
//			if(codigo != null && !codigo.equals("")){
//				filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
//			}
//			
//			if(descripcion != null && !descripcion.equals("")){
//				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
//			}
//			
//			if(idMarca != null && idMarca != -1){
//				filtros.add(new Filtro("c.marca.idMarca", TipoOperacionFiltroEnum.EQUAL, idMarca));
//			}
//			
//			if(idProductoGrupo != null && idProductoGrupo != -1){
//				filtros.add(new Filtro("c.productoGrupo.idProductoGrupo", TipoOperacionFiltroEnum.EQUAL, idProductoGrupo));
//			}
//			
//			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
//			return productoDao.buscarPorFiltros(filtros, "c.productoGrupo.codigo, c.codigo ASC");
			
			return productoDao.buscarProductosPorFiltros(
					null, 
					(idProductoGrupo != null && idProductoGrupo != -1 ? idProductoGrupo : null), 
					(idMarca != null && idMarca != -1 ? idMarca : null), 
					(codigo != null && !codigo.equals("") ? codigo : null), 
					true, 
					(descripcion != null && !descripcion.equals("") ? descripcion : null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Stock> buscarStockProductosPorCodigoDescripcionMarca(String codigo, String descripcion, Integer idMarca, Integer idProductoGrupo) throws Exception {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!codigo.equals("")){
				filtros.add(new Filtro("c.producto.codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			}
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("c.producto.descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			if(idMarca != -1){
				filtros.add(new Filtro("c.producto.marca.idMarca", TipoOperacionFiltroEnum.EQUAL, idMarca));
			}
			
			if(idProductoGrupo != -1){
				filtros.add(new Filtro("c.producto.productoGrupo.idProductoGrupo", TipoOperacionFiltroEnum.EQUAL, idProductoGrupo));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return stockDao.buscarPorFiltros(filtros, "c.producto.productoGrupo.codigo, c.producto.codigo ASC");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TrabajoProducto> buascarTrabajoProductosPorCodigoDescripcionMarca(String codigo, String descripcion, Integer idMarca) throws Exception {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!codigo.equals("")){
				filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			}
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			if(idMarca != -1){
				filtros.add(new Filtro("c.producto.marca.idMarca", TipoOperacionFiltroEnum.EQUAL, idMarca));
			}
			
			return trabajoProductoDao.buscarPorFiltros(filtros, null);
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
