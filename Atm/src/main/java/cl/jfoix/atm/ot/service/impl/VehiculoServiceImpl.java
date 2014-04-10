package cl.jfoix.atm.ot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IMarcaVehiculoDao;
import cl.jfoix.atm.ot.dao.IVehiculoDao;
import cl.jfoix.atm.ot.dao.IVehiculoOrdenDao;
import cl.jfoix.atm.ot.entity.MarcaVehiculo;
import cl.jfoix.atm.ot.entity.Vehiculo;
import cl.jfoix.atm.ot.entity.VehiculoOrden;
import cl.jfoix.atm.ot.service.IVehiculoService;

@Service("vehiculoService")
public class VehiculoServiceImpl implements IVehiculoService {

	@Autowired
	private IVehiculoDao vehiculoDao;
	
	@Autowired
	private IMarcaVehiculoDao marcaVehiculoDao;
	
	@Autowired
	private IVehiculoOrdenDao vehiculoOrdenDao;
	
	@Override
	public Vehiculo buscarVehiculoPorPatente(String patente) throws Exception {
		
		String patenteLimpia = patente;// == null ? "" : patente.replaceAll("-", "");
		
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new Filtro("patente", TipoOperacionFiltroEnum.EQUAL, patenteLimpia));

		List<Vehiculo> vehiculos = vehiculoDao.buscarPorFiltros(filtros, null);
		
		return vehiculos == null || vehiculos.size() == 0 ? null : vehiculos.get(0);
	}

	@Override
	@Transactional
	public void guardarVehiculo(Vehiculo vehiculo) throws Exception {
		if(vehiculo.getIdVehiculo() == null){
			vehiculoDao.guardar(vehiculo);
		} else {
			vehiculoDao.modificar(vehiculo);
		}
		
		MarcaVehiculo marca = marcaVehiculoDao.buscarPorId(vehiculo.getMarcaVehiculo().getIdMarcaVehiculo());
		vehiculo.setMarcaVehiculo(marca);
	}
	
	@Override
	@Transactional
	public void guardarVehiculoOrden(VehiculoOrden vehiculoOrden) throws Exception {
		if(vehiculoOrden.getIdVehiculoOrden() == null){
			vehiculoOrdenDao.guardar(vehiculoOrden);
		} else {
			vehiculoOrdenDao.modificar(vehiculoOrden);
		}
	}
}
