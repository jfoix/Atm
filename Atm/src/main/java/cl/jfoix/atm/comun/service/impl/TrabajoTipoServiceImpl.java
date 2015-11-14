package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.ITrabajoTipoDao;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.ITrabajoTipoService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("trabajoTipoService")
public class TrabajoTipoServiceImpl implements ITrabajoTipoService {

//	private static Logger log = Logger.getLogger(TrabajoTipoServiceImpl.class);

	@Autowired
	private ITrabajoTipoDao trabajoTipoDao;

	@Transactional
	@Override
	public void guardarTrabajoTipo(TrabajoTipo trabajoTipo) throws ViewException {
		try {
			if(trabajoTipo.getIdTrabajoTipo() == null){
				trabajoTipo.setEstado(true);
				trabajoTipoDao.guardar(trabajoTipo);
			} else {
				trabajoTipoDao.modificar(trabajoTipo);
			}
		} catch (DaoException e) {
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Override
	public List<TrabajoTipo> buscarTodosTrabajosTipo() {
		
		try {
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			return trabajoTipoDao.buscarPorFiltros(filtros, "descripcion asc");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TrabajoTipo> buscarTrabajoTipoPorDescripcion(String descripcion) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return trabajoTipoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
