package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IParametroGeneralDao;
import cl.jfoix.atm.comun.entity.ParametroGeneral;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAParametroGeneral extends JpaComunDao<ParametroGeneral, String> implements IParametroGeneralDao {

	@Autowired
	public JPAParametroGeneral(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
