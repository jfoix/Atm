package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IMovimientoDocumentoDao;
import cl.jfoix.atm.ot.entity.MovimientoDocumento;

@Repository
public class JPAMovimientoDocumento extends JpaComunDao<MovimientoDocumento, Integer> implements IMovimientoDocumentoDao {

	@Autowired
	public JPAMovimientoDocumento(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
