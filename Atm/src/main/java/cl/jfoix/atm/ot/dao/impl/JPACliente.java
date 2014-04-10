package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IClienteDao;
import cl.jfoix.atm.ot.entity.Cliente;

@Repository
public class JPACliente extends JpaComunDao<Cliente, Integer> implements IClienteDao {

	@Autowired
	public JPACliente(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
