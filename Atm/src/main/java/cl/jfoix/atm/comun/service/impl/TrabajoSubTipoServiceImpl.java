package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.ITrabajoSubTipoDao;
import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.ITrabajoSubTipoService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("trabajoSubTipoService")
public class TrabajoSubTipoServiceImpl implements ITrabajoSubTipoService {

//	private static Logger log = Logger.getLogger(TrabajoSubTipoServiceImpl.class);

	@Autowired
	private ITrabajoSubTipoDao trabajoSubTipoDao;

	@Transactional
	@Override
	public void guardarTrabajoSubTipo(TrabajoSubTipo trabajoSubTipo) throws ViewException {
		try {
			if(trabajoSubTipo.getIdTrabajoSubTipo() == null){
				trabajoSubTipo.setEstado(true);
				trabajoSubTipoDao.guardar(trabajoSubTipo);
			} else {
				trabajoSubTipoDao.modificar(trabajoSubTipo);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TrabajoSubTipo> buscarTodosTrabajosSubTipo() {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return trabajoSubTipoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TrabajoSubTipo> buscarTrabajoSubTipoPorDescripcionIdTrabajoTipo(String descripcion, Integer idTrabajoTipo) {

		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			if(idTrabajoTipo != -1){
				filtros.add(new Filtro("c.trabajoTipo.idTrabajoTipo", TipoOperacionFiltroEnum.EQUAL, idTrabajoTipo));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return trabajoSubTipoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TrabajoSubTipo> buscarTrabajoSubTipoPorIdTrabajoTipo(Integer idTrabajoTipo) {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			filtros.add(new Filtro("c.trabajoTipo.idTrabajoTipo", TipoOperacionFiltroEnum.EQUAL, idTrabajoTipo));
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return trabajoSubTipoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public TrabajoSubTipo buscarTrabajoSubTipo(Integer idTrabajoSubTipo) {
		
		try {
			return trabajoSubTipoDao.buscarPorId(idTrabajoSubTipo);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
