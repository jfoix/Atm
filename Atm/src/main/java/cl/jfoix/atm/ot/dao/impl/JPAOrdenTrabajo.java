package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;

@Repository
public class JPAOrdenTrabajo extends JpaComunDao<OrdenTrabajo, Integer> implements IOrdenTrabajoDao {

	@Autowired
	public JPAOrdenTrabajo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
