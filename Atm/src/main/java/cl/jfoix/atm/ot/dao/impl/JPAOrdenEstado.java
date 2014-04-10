package cl.jfoix.atm.ot.dao.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IOrdenEstadoDao;
import cl.jfoix.atm.ot.entity.OrdenEstado;

@Repository
public class JPAOrdenEstado extends JpaComunDao<OrdenEstado, Integer> implements IOrdenEstadoDao {

	@Autowired
	public JPAOrdenEstado(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}
	
	@Override
	public OrdenEstado buscarUltimoEstadoOrden(Integer idOrden){
		String query = "Select c From OrdenEstado c Where c.orden.idOrden = ?1 And c.fechaTermino IS NULL";
		
		List<?> resultado = getJpaTemplate().find(query, idOrden);
		
		if(resultado != null && resultado.size() > 0){
			return (OrdenEstado) resultado.get(0);
		}
		
		return null;
	}
}
