package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IFormaPagoDao;
import cl.jfoix.atm.ot.entity.FormaPago;

@Repository
public class JPAFormaPago extends JpaComunDao<FormaPago, Integer> implements IFormaPagoDao {

	@Autowired
	public JPAFormaPago(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
