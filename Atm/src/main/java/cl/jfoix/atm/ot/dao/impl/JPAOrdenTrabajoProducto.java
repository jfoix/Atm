package cl.jfoix.atm.ot.dao.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoProductoDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;

@Repository
public class JPAOrdenTrabajoProducto extends JpaComunDao<OrdenTrabajoProducto, Integer> implements IOrdenTrabajoProductoDao {

	private static Logger log = Logger.getLogger(JPAOrdenTrabajoProducto.class);
	
	@Autowired
	public JPAOrdenTrabajoProducto(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoProducto> buscarOrdenTrabajosPorProductoEnTrabajo(Integer idProducto) throws DaoException{
		
		try{
			
			return getJpaTemplate().find("SELECT otp FROM OrdenTrabajoProducto otp JOIN otp.ordenTrabajo.orden.ordenEstados estados WHERE "
					+ "estados.estadoOrden.idEstadoOrden NOT IN (5, 6, 7, 8) AND "
					+ "estados.fechaTermino IS NULL AND "
					+ "(otp.valor IS NULL OR otp.valor = 0) AND "
					+ "otp.producto.idProducto = ?1 ORDER BY otp.ordenTrabajo.orden.idOrden ASC", idProducto);
			
		} catch(Exception e){
			throw new DaoException(e, log, "buscarOrdenTrabajosPorProductoEnTrabajo");
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoProducto> buscarOrdenTrabajosPorProductoIdOrdenEnTrabajo(Integer idOrden, Integer idProducto) throws DaoException{
		
		try{
			
			return getJpaTemplate().find("SELECT otp FROM OrdenTrabajoProducto otp JOIN otp.ordenTrabajo.orden.ordenEstados estados WHERE "
					+ "estados.estadoOrden.idEstadoOrden NOT IN (5, 6, 7, 8) AND "
					+ "estados.fechaTermino IS NULL AND "
					+ "(?2 = -1 OR otp.ordenTrabajo.orden.idOrden < ?2) AND "
					+ "otp.producto.idProducto = ?1 ORDER BY otp.ordenTrabajo.orden.idOrden ASC", idProducto, idOrden);
			
		} catch(Exception e){
			throw new DaoException(e, log, "buscarOrdenTrabajosPorProductoEnTrabajo");
		}
	}
}
