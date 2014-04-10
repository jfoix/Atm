package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IProductoDao;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAProducto extends JpaComunDao<Producto, Integer> implements IProductoDao {

	@Autowired
	public JPAProducto(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
