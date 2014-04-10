package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IOrdenDocumentoDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.entity.OrdenDocumento;

@Repository
public class JPAOrdenDocumento extends JpaComunDao<OrdenDocumento, Integer> implements IOrdenDocumentoDao {

	@Autowired
	public JPAOrdenDocumento(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
