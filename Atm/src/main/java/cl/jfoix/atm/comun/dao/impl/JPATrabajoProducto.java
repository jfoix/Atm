package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.ITrabajoProductoDao;
import cl.jfoix.atm.comun.entity.TrabajoProducto;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPATrabajoProducto extends JpaComunDao<TrabajoProducto, Integer> implements ITrabajoProductoDao {

	@Autowired
	public JPATrabajoProducto(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
