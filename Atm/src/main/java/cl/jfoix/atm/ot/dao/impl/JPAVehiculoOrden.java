package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IVehiculoOrdenDao;
import cl.jfoix.atm.ot.entity.VehiculoOrden;

@Repository
public class JPAVehiculoOrden extends JpaComunDao<VehiculoOrden, Integer> implements IVehiculoOrdenDao {

	@Autowired
	public JPAVehiculoOrden(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
