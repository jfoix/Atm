package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IMarcaDao;
import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAMarca extends JpaComunDao<Marca, Integer> implements IMarcaDao {

	@Autowired
	public JPAMarca(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
