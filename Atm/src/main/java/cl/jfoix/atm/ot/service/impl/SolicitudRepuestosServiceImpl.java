package cl.jfoix.atm.ot.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoProductoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudProductoDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudProductoGrupoDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProductoGrupo;
import cl.jfoix.atm.ot.service.ISolicitudRepuestosService;

@Service("solicitudRepuestosService")
public class SolicitudRepuestosServiceImpl implements ISolicitudRepuestosService {

	@Autowired
	private IOrdenTrabajoSolicitudDao ordenTrabajoSolicitudDao;
	
	@Autowired
	private IOrdenTrabajoProductoDao ordenTrabajoProductoDao;
	
	@Autowired
	private IOrdenTrabajoSolicitudProductoDao ordenTrabajoSolicitudProductoDao;
	
	@Autowired
	private IOrdenTrabajoSolicitudProductoGrupoDao ordenTrabajoSolicitudProductoGrupoDao;

	@Override
	@Transactional
	public void guardarSolicitudRepuesto(OrdenTrabajoSolicitud odenTrabajoSolicitud) {
		try{
			ordenTrabajoSolicitudDao.guardar(odenTrabajoSolicitud);
		} catch(DaoException e){
			e.printStackTrace();
		}
	}
	
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
	public List<OrdenTrabajoSolicitud> buscarSolicitudesRepuestos(Date fechaSolicitud, Integer idProductoGrupo, Boolean estado, String nombreUsuario) {

		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();

			if(fechaSolicitud != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaSolicitud);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				filtros.add(new Filtro("c.fechaSolicitud",TipoOperacionFiltroEnum.MAYOR_IGUAL_QUE, cal.getTime()));
			}
			
			if(nombreUsuario != null){
				filtros.add(new Filtro("c.nombreUsuario",TipoOperacionFiltroEnum.EQUAL, nombreUsuario));
			}
			
			if(estado != null){
				filtros.add(new Filtro("c.estado",TipoOperacionFiltroEnum.EQUAL, estado));
			}
			
			if(idProductoGrupo != null){
				filtros.add(new Filtro("pg.productoGrupo.idProductoGrupo",TipoOperacionFiltroEnum.EQUAL, idProductoGrupo));
			}
			
			List<OrdenTrabajoSolicitud> ots;
			
			if(filtros.size() == 0){
				ots = ordenTrabajoSolicitudDao.buscarTodos();
			} else {
				ots = ordenTrabajoSolicitudDao.buscarPorFiltros(filtros, null, true, "JOIN c.productosGrupo pg");
			}
			
			if(ots != null){
				for(OrdenTrabajoSolicitud otSol : ots){
					if(otSol.getProductosGrupo() != null){
						otSol.getProductosGrupo().size();
					}
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
			Double cantidadAgregada = solProducto.getCantidadAgregada();
			solProducto = ordenTrabajoSolicitudProductoDao.buscarPorId(solProducto.getPk());
			solProducto.setEstado(false);
			solProducto.setCantidadAgregada(cantidadAgregada);
			ordenTrabajoSolicitudProductoDao.modificar(solProducto);
			
		} catch(DaoException e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void guardarProductosSolicitud(OrdenTrabajoSolicitudProductoGrupo otspg, List<Producto> productos){
		try{
			otspg.setEstado(true); //terminado
			ordenTrabajoSolicitudProductoGrupoDao.modificar(otspg);
			
			otspg.getSolicitud().setEstado(false);
			otspg.getSolicitud().setFechaTermino(new Date());
			ordenTrabajoSolicitudDao.modificar(otspg.getSolicitud());
			
			for(Producto prod : productos){
				OrdenTrabajoProducto otp = new OrdenTrabajoProducto();
				otp.setCantidad(prod.getCantidad());
				otp.setOrdenTrabajo(otspg.getSolicitud().getOrdenTrabajo());
				otp.setProducto(prod);
				otp.setTraidoCliente(false);
				otp.setValor(0);
				
				ordenTrabajoProductoDao.guardar(otp);
			}
		} catch(DaoException e){
			e.printStackTrace();
		}
	}
}
