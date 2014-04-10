package cl.jfoix.atm.admin.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.admin.dao.IUsuarioDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.login.entity.Usuario;

@Repository
public class JPAUsuario extends JpaComunDao<Usuario, String> implements IUsuarioDao {

	@Autowired
	public JPAUsuario(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
