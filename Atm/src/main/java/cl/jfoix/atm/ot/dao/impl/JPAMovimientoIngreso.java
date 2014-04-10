package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IMovimientoIngresoDao;
import cl.jfoix.atm.ot.entity.MovimientoIngreso;

@Repository
public class JPAMovimientoIngreso extends JpaComunDao<MovimientoIngreso, Integer> implements IMovimientoIngresoDao {

	@Autowired
	public JPAMovimientoIngreso(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
