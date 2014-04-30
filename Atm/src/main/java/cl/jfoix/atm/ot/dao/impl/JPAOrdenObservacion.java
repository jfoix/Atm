package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenObservacionDao;
import cl.jfoix.atm.ot.entity.OrdenObservacion;

@Repository
public class JPAOrdenObservacion extends JpaComunDao<OrdenObservacion, Integer> implements IOrdenObservacionDao {

	@Autowired
	public JPAOrdenObservacion(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
