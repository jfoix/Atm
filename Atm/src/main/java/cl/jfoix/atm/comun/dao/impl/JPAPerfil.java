package cl.jfoix.atm.comun.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IPerfilDao;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.login.entity.Perfil;

@Repository
public class JPAPerfil extends JpaComunDao<Perfil, Integer> implements IPerfilDao {

	@Autowired
	public JPAPerfil(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
}
