package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;

@Repository
public class JPAOrdenTrabajoSolicitud extends JpaComunDao<OrdenTrabajoSolicitud, Integer> implements IOrdenTrabajoSolicitudDao {

	@Autowired
	public JPAOrdenTrabajoSolicitud(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
