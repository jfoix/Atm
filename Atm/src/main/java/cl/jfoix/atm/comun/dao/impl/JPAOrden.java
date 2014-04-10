package cl.jfoix.atm.comun.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IOrdenDao;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.entity.Orden;

@Repository
public class JPAOrden extends JpaComunDao<Orden, Integer> implements IOrdenDao {

	private static Logger log = Logger.getLogger(JPAOrden.class);

	@Autowired
	public JPAOrden(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orden> buscarOrdenAdmin(Integer idOrden, String idMecanico, Date fechaInicio, Date fechaTermino, Integer idEstado, String patente) throws DaoException {
		try{
			return getJpaTemplate().find("SELECT distinct o FROM Orden o JOIN o.ordenEstados status JOIN o.ordenTrabajos trabajos LEFT JOIN trabajos.ordenTrabajoUsuarios mecanicos WHERE "
					+ "(?1 = 0 OR o.idOrden = ?1) AND "
					+ "(?2 IS NULL OR o.fechaOrden >= ?2) AND "
					+ "(?3 IS NULL OR o.fechaOrden <= ?3) AND "
					+ "(?6 = '' OR mecanicos.usuario.nombreUsuario = ?6) AND "
					+ "status.fechaTermino IS NULL AND "
					+ "(?4 = -1 OR status.estadoOrden.idEstadoOrden = ?4) AND "
					+ "(?5 = '' OR o.vehiculoOrden.vehiculo.patente = ?5) ORDER BY o.idOrden DESC ", idOrden, fechaInicio, fechaTermino, idEstado, patente, idMecanico);
		} catch(Exception e){
			throw new DaoException(e, log, "buscarOrdenAdmin");
		}
	}
}
