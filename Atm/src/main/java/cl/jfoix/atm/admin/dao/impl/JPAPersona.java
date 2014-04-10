package cl.jfoix.atm.admin.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.admin.dao.IPersonaDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.login.entity.Persona;

@Repository
public class JPAPersona extends JpaComunDao<Persona, Integer> implements IPersonaDao {

	@Autowired
	public JPAPersona(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
