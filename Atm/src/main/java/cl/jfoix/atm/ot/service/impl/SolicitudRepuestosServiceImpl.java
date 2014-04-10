package cl.jfoix.atm.ot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudProductoDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.service.ISolicitudRepuestosService;

@Service("solicitudRepuestosService")
public class SolicitudRepuestosServiceImpl implements ISolicitudRepuestosService {

	@Autowired
	private IOrdenTrabajoSolicitudDao ordenTrabajoSolicitudDao;
	
	@Autowired
	private IOrdenTrabajoSolicitudProductoDao ordenTrabajoSolicitudProductoDao;
	
	@Override
	@Transactional
	public void cambiarEstadoSolicitudRepuesto(OrdenTrabajoSolicitud odenTrabajoSolicitud) {
		try{
			
			odenTrabajoSolicitud = ordenTrabajoSolicitudDao.buscarPorId(odenTrabajoSolicitud.getIdOrdenTrabajoSolicitud());
			odenTrabajoSolicitud.setEstado(false);
			ordenTrabajoSolicitudDao.modificar(odenTrabajoSolicitud);
			
		} catch(DaoException e){
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public List<OrdenTrabajoSolicitud> buscarSolicitudesRepuestos(Date fechaSolicitud, Integer idProducto, Boolean estado, String nombreUsuario) {

		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();

			if(fechaSolicitud != null){
				filtros.add(new Filtro("c.fechaSolicitud",TipoOperacionFiltroEnum.MAYOR_IGUAL_QUE, fechaSolicitud));
			}
			
			if(nombreUsuario != null){
				filtros.add(new Filtro("c.nombreUsuario",TipoOperacionFiltroEnum.EQUAL, nombreUsuario));
			}
			
			if(estado != null){
				filtros.add(new Filtro("c.estado",TipoOperacionFiltroEnum.EQUAL, estado));
			}
			
			if(idProducto != null){
				filtros.add(new Filtro("producto.producto.idProducto",TipoOperacionFiltroEnum.EQUAL, idProducto));
			}
			
			List<OrdenTrabajoSolicitud> ots;
			
			if(filtros.size() == 0){
				ots = ordenTrabajoSolicitudDao.buscarTodos();
			} else {
				ots = ordenTrabajoSolicitudDao.buscarPorFiltros(filtros, null, true, "JOIN c.productos producto");
			}
			
			if(ots != null){
				for(OrdenTrabajoSolicitud otSol : ots){
					otSol.getProductos().size();
				}
			}
			
			return ots;
			
		} catch(DaoException e){
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@Transactional
	public void cambiarEstadoSolicitudRepuestoProducto(OrdenTrabajoSolicitudProducto solProducto) {

		try{
			
			solProducto = ordenTrabajoSolicitudProductoDao.buscarPorId(solProducto.getPk());
			solProducto.setEstado(false);
			ordenTrabajoSolicitudProductoDao.modificar(solProducto);
			
		} catch(DaoException e){
			e.printStackTrace();
		}
	}
}
