package cl.jfoix.atm.ot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IMarcaVehiculoDao;
import cl.jfoix.atm.ot.entity.MarcaVehiculo;
import cl.jfoix.atm.ot.service.IMarcaVehiculoService;

@Service("marcaVehiculoService")
public class MarcaVehiculoServiceImpl implements IMarcaVehiculoService {

	private static Logger log = Logger.getLogger(MarcaVehiculoServiceImpl.class);

	@Autowired
	private IMarcaVehiculoDao marcaVehiculoDao;

	@Override
	public List<MarcaVehiculo> buscarTodasMarcasVehiculo() {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			return marcaVehiculoDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			log.error("Error al buscar las marcas de vehiculo", e);
		}
		
		return null;
	}
}
