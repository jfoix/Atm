package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IBodegaDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.entity.Bodega;

@Repository
public class JPABodega extends JpaComunDao<Bodega, Integer> implements IBodegaDao {

	@Autowired
	public JPABodega(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
