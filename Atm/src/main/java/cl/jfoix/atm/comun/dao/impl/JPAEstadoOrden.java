package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IEstadoOrdenDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.entity.EstadoOrden;

@Repository
public class JPAEstadoOrden extends JpaComunDao<EstadoOrden, Integer> implements IEstadoOrdenDao {

	@Autowired
	public JPAEstadoOrden(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
