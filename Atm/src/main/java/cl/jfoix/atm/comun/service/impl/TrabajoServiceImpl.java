package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.ITrabajoDao;
import cl.jfoix.atm.comun.dao.ITrabajoProductoDao;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.comun.entity.TrabajoProductoPK;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.ITrabajoService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("trabajoService")
public class TrabajoServiceImpl implements ITrabajoService {

	@Autowired
	private ITrabajoDao trabajoDao;
	
	@Autowired
	private ITrabajoProductoDao trabajoProductoDao;
	
	@Override
	@Transactional
	public void guardarTrabajo(Trabajo trabajo) throws ViewException {
		try{
			if(trabajo.getIdTrabajo() == null){
				trabajo.setEstado(true);
				trabajoDao.guardar(trabajo);
				
				if(trabajo.getTrabajoProductos() != null){
					for(TrabajoProducto tp : trabajo.getTrabajoProductos()){
						TrabajoProductoPK pk = new TrabajoProductoPK();
						pk.setIdProducto(tp.getProducto().getIdProducto());
						pk.setIdTrabajo(trabajo.getIdTrabajo());
						
						tp.setPk(pk);
						
						trabajoProductoDao.guardar(tp);
					}
				}
				
			} else {
				
				trabajoDao.modificar(trabajo);
				
				List<Filtro> filtros = new ArrayList<Filtro>();
				filtros.add(new Filtro("c.pk.idTrabajo", TipoOperacionFiltroEnum.EQUAL, trabajo.getIdTrabajo()));
				
				List<TrabajoProducto> trabajoProductosActuales = trabajoProductoDao.buscarPorFiltros(filtros, null);
				
				for(TrabajoProducto tpAc : trabajoProductosActuales){
					
					boolean existe = false;
					
					for(TrabajoProducto tp : trabajo.getTrabajoProductos()){
						if(tpAc.getProducto().getIdProducto().equals(tp.getProducto().getIdProducto())){
							existe = true;
							break;
						}
					}
					
					if(!existe){
						trabajoProductoDao.eliminar(tpAc);
					}
				}
				
				for(TrabajoProducto tp : trabajo.getTrabajoProductos()){
					
					boolean existe = false;
					
					for(TrabajoProducto tpAc : trabajoProductosActuales){
						if(tpAc.getProducto().getIdProducto().equals(tp.getProducto().getIdProducto())){
							existe = true;
							break;
						}
					}
					
					if(!existe){
						TrabajoProductoPK pk = new TrabajoProductoPK();
						pk.setIdProducto(tp.getProducto().getIdProducto());
						pk.setIdTrabajo(trabajo.getIdTrabajo());
						
						tp.setPk(pk);
						
						trabajoProductoDao.guardar(tp);
					}
				}
			}
		} catch(DaoException e){
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Transactional
	@Override
	public List<Trabajo> buscarTrabajoPorDescripcionCodigo(String descripcion, String codigo, Integer idTrabajoTipo, Integer idTrabajoSubTipo) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			if(!codigo.equals("")){
				filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			}
			
			if(idTrabajoTipo != -1){
				filtros.add(new Filtro("c.trabajoSubTipo.trabajoTipo.idTrabajoTipo", TipoOperacionFiltroEnum.EQUAL, idTrabajoTipo));
			}
			
			if(idTrabajoSubTipo != -1){
				filtros.add(new Filtro("c.trabajoSubTipo.idTrabajoSubTipo", TipoOperacionFiltroEnum.EQUAL, idTrabajoSubTipo));
			}
			
			filtros.add(new Filtro("c.estado", TipoOperacionFiltroEnum.EQUAL, true));

			List<Trabajo> trabajos = trabajoDao.buscarPorFiltros(filtros, null);
			
			if(trabajos != null){
				for(Trabajo trabajo : trabajos){
					if(trabajo.getTrabajoProductos() != null){
						trabajo.getTrabajoProductos().size();
					}
				}
			}
			
			return trabajos;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public Trabajo buscarTrabajoPorId(Integer idTrabajoTipo) {
		
		try {
			Trabajo trabajo = trabajoDao.buscarPorId(idTrabajoTipo);
			
			trabajo.getTrabajoProductos().size();
			
			return trabajo;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TrabajoProducto> buscarProductosPorIdTrabajo(Integer idTrabajo) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.trabajo.idTrabajo", TipoOperacionFiltroEnum.EQUAL, idTrabajo));

			return trabajoProductoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}