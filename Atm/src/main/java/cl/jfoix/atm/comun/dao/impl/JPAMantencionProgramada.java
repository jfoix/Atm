package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IMantencionProgramadaDao;
import cl.jfoix.atm.comun.entity.MantencionProgramada;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAMantencionProgramada extends JpaComunDao<MantencionProgramada, Integer> implements IMantencionProgramadaDao {

	@Autowired
	public JPAMantencionProgramada(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
