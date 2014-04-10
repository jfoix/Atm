package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.entity.Stock;

@Repository
public class JPAStock extends JpaComunDao<Stock, Integer> implements IStockDao {

	@Autowired
	public JPAStock(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
