package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.ITrabajoTipoDao;
import cl.jfoix.atm.comun.entity.TrabajoTipo;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPATrabajoTipo extends JpaComunDao<TrabajoTipo, Integer> implements ITrabajoTipoDao {

	@Autowired
	public JPATrabajoTipo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}