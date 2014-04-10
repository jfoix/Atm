package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IPermisoDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.login.entity.Permiso;

@Repository
public class JPAPermiso extends JpaComunDao<Permiso, Integer> implements IPermisoDao {

	@Autowired
	public JPAPermiso(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
