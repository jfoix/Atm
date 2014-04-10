package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoSolicitudProductoDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProductoPK;

@Repository
public class JPAOrdenTrabajoSolicitudProducto extends JpaComunDao<OrdenTrabajoSolicitudProducto, OrdenTrabajoSolicitudProductoPK> implements IOrdenTrabajoSolicitudProductoDao {

	@Autowired
	public JPAOrdenTrabajoSolicitudProducto(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
