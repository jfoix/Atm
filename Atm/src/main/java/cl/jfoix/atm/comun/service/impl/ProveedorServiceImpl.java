package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IProveedorDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IProveedorService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.entity.Proveedor;

@Service("proveedorService")
public class ProveedorServiceImpl implements IProveedorService {

	private static Logger log = Logger.getLogger(ProveedorServiceImpl.class);

	@Autowired
	private IProveedorDao proveedorDao;

	@Transactional
	@Override
	public void guardarProveedor(Proveedor proveedor) throws ViewException {
		try{
			if(proveedor.getIdProveedor() == null){
				proveedor.setEstado(true);
				proveedorDao.guardar(proveedor);
			} else {
				proveedorDao.modificar(proveedor);
			}
		}catch(DaoException e){
			log.error("Erro al guardar el proveedor", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Override
	public List<Proveedor> buscarTodosProveedores() {
		
		try {
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return proveedorDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Proveedor> buscarProveedoresPorDescripcion(String descripcionProveedor) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcionProveedor.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcionProveedor));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return proveedorDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
