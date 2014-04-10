package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IEstadoTrabajoDao;
import cl.jfoix.atm.comun.entity.EstadoTrabajo;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAEstadoTrabajo extends JpaComunDao<EstadoTrabajo, Integer> implements IEstadoTrabajoDao {

	@Autowired
	public JPAEstadoTrabajo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
