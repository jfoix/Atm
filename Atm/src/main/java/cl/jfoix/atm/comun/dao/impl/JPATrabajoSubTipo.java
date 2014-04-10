package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.ITrabajoSubTipoDao;
import cl.jfoix.atm.comun.entity.TrabajoSubTipo;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPATrabajoSubTipo extends JpaComunDao<TrabajoSubTipo, Integer> implements ITrabajoSubTipoDao {

	@Autowired
	public JPATrabajoSubTipo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
