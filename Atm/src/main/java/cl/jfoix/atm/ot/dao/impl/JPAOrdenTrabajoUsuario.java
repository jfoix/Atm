package cl.jfoix.atm.ot.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenTrabajoUsuarioDao;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuarioPK;

@Repository
public class JPAOrdenTrabajoUsuario extends JpaComunDao<OrdenTrabajoUsuario, OrdenTrabajoUsuarioPK> implements IOrdenTrabajoUsuarioDao {

	@Autowired
	public JPAOrdenTrabajoUsuario(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
