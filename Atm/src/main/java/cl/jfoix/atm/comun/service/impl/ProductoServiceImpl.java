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

@Service("productoService")
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
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

	@Override
	public List<Producto> buscarProductosPorCodigoDescripcionMarca(String codigo, String descripcion, Integer idMarca) throws Exception {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!codigo.equals("")){
				filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			}
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			if(idMarca != -1){
				filtros.add(new Filtro("c.marca.idMarca", TipoOperacionFiltroEnum.EQUAL, idMarca));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return productoDao.buscarPorFiltros(filtros, null);
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
