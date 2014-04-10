package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IBodegaDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IBodegaService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.entity.Bodega;

@Service("bodegaService")
public class BodegaServiceImpl implements IBodegaService {

	private static Logger log = Logger.getLogger(BodegaServiceImpl.class);

	@Autowired
	private IBodegaDao bodegaDao;

	@Transactional
	@Override
	public void guardarBodega(Bodega bodega) throws ViewException {
		try{
			if(bodega.getIdBodega() == null){
				bodega.setEstado(true);
				bodega.setPrincipal(false);
				bodegaDao.guardar(bodega);
			} else {
				bodegaDao.modificar(bodega);
			}
		}catch(DaoException e){
			log.error("Erro al guardar la bodega", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Override
	public List<Bodega> buscarTodasBodegas() {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return bodegaDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Bodega> buscarBodegasPorDescripcion(String descripcionBodega) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcionBodega.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcionBodega));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return bodegaDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
