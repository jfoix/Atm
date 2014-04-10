package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IMovimientoDao;
import cl.jfoix.atm.ot.entity.Movimiento;

@Repository
public class JPAMovimiento extends JpaComunDao<Movimiento, Integer> implements IMovimientoDao {

	@Autowired
	public JPAMovimiento(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
