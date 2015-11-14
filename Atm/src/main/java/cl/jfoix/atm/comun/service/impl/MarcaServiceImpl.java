package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IMarcaDao;
import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IMarcaService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("marcaService")
public class MarcaServiceImpl implements IMarcaService {

	private static Logger log = Logger.getLogger(MarcaServiceImpl.class);

	@Autowired
	private IMarcaDao marcaDao;

	@Transactional
	@Override
	public void guardarMarca(Marca marca) throws ViewException {
		try{
			if(marca.getIdMarca() == null){
				marca.setEstado(true);
				marcaDao.guardar(marca);
			} else {
				marcaDao.modificar(marca);
			}
		}catch(DaoException e){
			log.error("Erro al guardar la marca", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Override
	public List<Marca> buscarTodasMarcas() {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return marcaDao.buscarPorFiltros(filtros, "descripcion asc");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Marca> buscarMarcasPorDescripcion(String descripcionMarca) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcionMarca.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcionMarca));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return marcaDao.buscarPorFiltros(filtros, "descripcion asc");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
