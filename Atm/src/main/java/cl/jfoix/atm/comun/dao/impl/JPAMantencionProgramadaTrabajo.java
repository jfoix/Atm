package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IMantencionProgramadaTrabajoDao;
import cl.jfoix.atm.comun.entity.MantencionProgramadaTrabajo;
import cl.jfoix.atm.comun.entity.MantencionProgramadaTrabajoPK;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAMantencionProgramadaTrabajo extends JpaComunDao<MantencionProgramadaTrabajo, MantencionProgramadaTrabajoPK> implements IMantencionProgramadaTrabajoDao {

	@Autowired
	public JPAMantencionProgramadaTrabajo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
