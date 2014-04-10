package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.ITrabajoDao;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPATrabajo extends JpaComunDao<Trabajo, Integer> implements ITrabajoDao {

	@Autowired
	public JPATrabajo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
