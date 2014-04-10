package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IProveedorDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.entity.Proveedor;

@Repository
public class JPAProveedor extends JpaComunDao<Proveedor, Integer> implements IProveedorDao {

	@Autowired
	public JPAProveedor(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
