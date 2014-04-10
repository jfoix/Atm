package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IMarcaVehiculoDao;
import cl.jfoix.atm.ot.entity.MarcaVehiculo;

@Repository
public class JPAMarcaVehiculo extends JpaComunDao<MarcaVehiculo, Integer> implements IMarcaVehiculoDao {

	@Autowired
	public JPAMarcaVehiculo(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
