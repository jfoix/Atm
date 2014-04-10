package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IVehiculoDao;
import cl.jfoix.atm.ot.entity.Vehiculo;

@Repository
public class JPAVehiculo extends JpaComunDao<Vehiculo, Integer> implements IVehiculoDao {

	@Autowired
	public JPAVehiculo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
