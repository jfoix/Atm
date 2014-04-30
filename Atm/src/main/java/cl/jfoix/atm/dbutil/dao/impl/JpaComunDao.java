/**
 * 
 */
package cl.jfoix.atm.dbutil.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.IComunDao;
import cl.jfoix.atm.dbutil.dao.util.Filtro;

/**
 * Clase dao común con las operaciones básicas del DAO<br>
 * Las funcionalidades incluidas son :<br>
 * <code>crear, modificar, eliminar, buscarPorId, buscarPorFiltros</code>
 * 
 * @param T, tipo generico que represneta a una entidad
 * @param ID, tipo que generico que representa al tipo id de la entidad
 * 
 * @author César Abarza S.
 * @version 1.0
 */
//@Repository
public class JpaComunDao<T extends Serializable, ID extends Serializable> extends JpaDaoSupport implements IComunDao<T, ID> {

	private static Logger log = Logger.getLogger(JpaComunDao.class);	
	/**
	 * Asocia la instancia del entityManagewrFactory para trabajar con las funcionalidades JPA
	 * 
	 * @param entityManagerFactory
	 */
	@Autowired
	public JpaComunDao(EntityManagerFactory entityManagerFactory){
		super();
		setEntityManagerFactory(entityManagerFactory);
	}
	
	/**
	 * Método encargado de crear la entidad que se pasa como parámetro<br>
	 * 
	 * @param T entity, donde T es una clase de tipo entidad
	 * @exception DaoException, devuelve si ocurre algún error al persistir la entidad
	 */
	@Override
	public void guardar(T entity) throws DaoException{
		log.debug("[crear] Se va a crear la entidad" + entity);
		try{
			getJpaTemplate().persist(entity);
		}catch (Exception e) {
			log.error("[crear] Error al crear la entidad" + entity, e);
			throw new DaoException(e, log, "crear");
		}
		log.debug("[crear] Se crea la entidad" + entity);
	}
	
	/**
	 * Método encargado de modificar la entidad que se pasa como parámetro<br>
	 * 
	 * @param T entity, donde T es una clase de tipo entidad
	 * @exception DaoException, devuelve si ocurre algún error al realizar el merge de la entidad
	 */
	@Override
	public void modificar(T entity) throws DaoException{
		log.debug("[modificar] Se va a modificar la entidad" + entity);
		try{
			getJpaTemplate().merge(entity);
		}catch (Exception e) {
			log.error("[modificar] Error al modificar la entidad" + entity, e);
			throw new DaoException(e, log, "modificar");
		}
		log.debug("[modificar] Se modifica la entidad" + entity);
	}
	
	/**
	 * Método encargado de eliminar la entidad que se pasa como parámetro<br>
	 * 
	 * @param T entity, donde T es una clase de tipo entidad. El objeto debe estar atachado a la transacción
	 * @exception DaoException, devuelve si ocurre algún error al remover la entidad
	 */
	@Override
	public void eliminar(T entity) throws DaoException{
		log.debug("[eliminar] Se va a eliminar la entidad" + entity);
		try{
			getJpaTemplate().remove(entity);
		}catch (Exception e) {
			log.error("[eliminar] Error al eliminar la entidad" + entity, e);
			throw new DaoException(e, log, "eliminar");
		}
		log.debug("[eliminar] Se elimina la entidad" + entity);
	}
	
	/**
	 * Método encargado de buscar una entidad del tipo de clase enviada y que corresponda al id entregado<br>
	 * 
	 * @param Object id, representa a @Id de la entidad T que se desea buscar
	 * @return T, representa a la entidad encontrada por el id entregado
	 * @exception DaoException, devuelve si ocurre algún error buscar la entidad por Id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T buscarPorId(ID id) throws DaoException{
		T resultado = null;
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		log.debug("[buscarPorId] Se va a buscar por id " + id + " la entidad" + entityClass.getSimpleName());
		try{
			resultado = getJpaTemplate().find(entityClass, id);
		}catch (Exception e) {
			log.error("[buscarPorId] Error al buscar la entidad por Id", e);
			throw new DaoException(e, log, "buscarPorId");
		}
		log.debug("[buscarPorId] Se ha encontrado la entidad" + resultado);
		return resultado;
	}
	
	/**
	 * Método encargado de buscar una lista de entidades del tipo de clase enviada y que corresponda a los filtros entregados<br>
	 * 
	 * @param List<Filtro> filtros, representa a un listado de filtros que se realizarán a la consulta
	 * @param String clausulaOrden, contiene la claúsula que continua luego del 'order by'
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarPorFiltros(List<Filtro> filtros, String clausulaOrden, boolean distinct, String... joins) throws DaoException{
		List<T> resultado = null;
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		log.debug("[buscarPorFiltros] Se va a buscar la entidad " + entityClass.getSimpleName() + " con los filtros " + (filtros!=null?filtros.toString():""));
		try{
			StringBuilder consulta = new StringBuilder("SELECT " + (distinct ? "distinct" : "") + " c FROM "+entityClass.getSimpleName()+" c ");
			
			if(joins != null && joins.length > 0){
				for(String join : joins){
					consulta.append(" ");
					consulta.append(join);
					consulta.append(" ");
				}
			}
			
			consulta.append(" WHERE 1=1 ");
			List<Object> values = new ArrayList<Object>();
			if(filtros!=null && !filtros.isEmpty()){
				for(Filtro filtro: filtros){
					consulta.append(" AND ");
					consulta.append(filtro.getPropiedad());
					consulta.append(filtro.getTipoOperacion().getOperacion());
					switch (filtro.getTipoOperacion()) {
					case BETWEEN:
						if(filtro.getValorFiltro() instanceof List){
							List<?> aux = (List<?>)filtro.getValorFiltro();
							values.add(aux.get(0));
							values.add(aux.get(1));
						}else{
							values.add(((Object[])filtro.getValorFiltro())[0]);
							values.add(((Object[])filtro.getValorFiltro())[1]);
						}
						consulta.append(" ? AND ? ");
						break;
					case IN:
					case NOT_IN:
						String valores = Arrays.toString((Object[])filtro.getValorFiltro());
			            valores = valores.substring(1, valores.length()-1);
			            consulta.append("(");
			            consulta.append(valores);
			            consulta.append(")");
						break;
					case LIKE_COMPLETO:
						consulta.append("'%");
			            consulta.append(filtro.getValorFiltro().toString());
			            consulta.append("%'");
						break;
					case LIKE_INICIO:
						consulta.append("'%");
			            consulta.append(filtro.getValorFiltro().toString());
			            consulta.append("'");
						break;
					case LIKE_FIN:
						consulta.append("'");
			            consulta.append(filtro.getValorFiltro().toString());
			            consulta.append("%'");
						break;
					case IS_NULL:
						break;
					case EXISTS:
					case NOT_EXISTS:
						consulta.append("(");
			            consulta.append((String)filtro.getValorFiltro());
			            consulta.append(")");
					default:
						values.add(filtro.getValorFiltro());
						consulta.append("?"+(values.size()));
						break;
					}
				}
			}
			if(clausulaOrden!=null && clausulaOrden.length()>0){
				consulta.append(" order by ");
				consulta.append(clausulaOrden);
			}
			if(values.isEmpty()){
				resultado = getJpaTemplate().find(consulta.toString());
			}else{
				resultado = getJpaTemplate().find(consulta.toString(), values.toArray());
			}
		}catch (Exception e) {
			log.error("[buscarPorId] Error al buscar la entidad por filtros "+filtros.toString(), e);
			throw new DaoException(e, log, "buscarPorId");
		}
		log.debug("[buscarPorId] Se ha encontrado la entidad" + resultado);
		return resultado;
	}
	
	/**
	 * Método encargado de buscar una lista de entidades del tipo de clase enviada<br>
	 * Este método retorna todos los registros del tipo de entidad, ya que no van con filtros
	 * 
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	@Override
	public List<T> buscarTodos() throws DaoException{
		return this.buscarPorFiltros(null, null);
	}
	
	/**
	 * Método encargado de buscar una lista de entidades del tipo de clase enviada ordenada según lo enviado<br>
	 * Este método retorna todos los registros del tipo de entidad, ya que no van con filtros
	 * 
	 * @param String clausulaOrden, contiene la claúsula que continua luego del 'order by'
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	@Override
	public List<T> buscarTodosOrdenados(String clausulaOrden) throws DaoException{
		return this.buscarPorFiltros(null, clausulaOrden);
	}
	
	@Override
	public List<T> buscarPorFiltros(List<Filtro> filtros, String clausulaOrden, String... joins) throws DaoException{
		return this.buscarPorFiltros(filtros, clausulaOrden, false, joins);
	}
	
	/**
	 * Método encargado de eliminar una lista de entidades del tipo de clase enviada y que corresponda a los filtros entregados<br>
	 * 
	 * @param List<Filtro> filtros, representa a un listado de filtros que se realizarán a la consulta
	 * @param String clausulaOrden, contiene la claúsula que continua luego del 'order by'
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eliminarPorFiltros(List<Filtro> filtros) throws DaoException{
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		log.debug("[eliminarPorFiltros] Se va a eliminar las entidades " + entityClass.getSimpleName() + " con los filtros " + (filtros!=null?filtros.toString():""));
		try{
			StringBuilder consulta = new StringBuilder("DELETE FROM "+entityClass.getSimpleName()+" c WHERE 1=1 ");
			List<Object> values = new ArrayList<Object>();
			if(filtros!=null && !filtros.isEmpty()){
				for(Filtro filtro: filtros){
					consulta.append(" AND ");
					consulta.append(filtro.getPropiedad());
					consulta.append(filtro.getTipoOperacion().getOperacion());
					switch (filtro.getTipoOperacion()) {
					case BETWEEN:
						if(filtro.getValorFiltro() instanceof List){
							List<?> aux = (List<?>)filtro.getValorFiltro();
							consulta.append(aux.get(0));
							consulta.append(" and ");
							consulta.append(aux.get(1));
						}else{
							consulta.append(((Object[])filtro.getValorFiltro())[0]);
							consulta.append(" and ");
							consulta.append(((Object[])filtro.getValorFiltro())[1]);
						}
						break;
					case IN:
					case NOT_IN:
						String valores = Arrays.toString((Object[])filtro.getValorFiltro());
			            valores = valores.substring(1, valores.length()-1);
			            consulta.append("(");
			            consulta.append(valores);
			            consulta.append(")");
						break;
					case LIKE_COMPLETO:
						consulta.append("'%");
			            consulta.append(filtro.getValorFiltro().toString());
			            consulta.append("%'");
						break;
					case LIKE_INICIO:
						consulta.append("'%");
			            consulta.append(filtro.getValorFiltro().toString());
			            consulta.append("'");
						break;
					case LIKE_FIN:
						consulta.append("'");
			            consulta.append(filtro.getValorFiltro().toString());
			            consulta.append("%'");
						break;
					default:
						values.add(filtro.getValorFiltro());
						consulta.append("?"+(values.size()));
						break;
					}
				}
			}
			Query query = getJpaTemplate().getEntityManagerFactory().createEntityManager().createQuery(consulta.toString());
			if(!values.isEmpty()){
				int i=1;
				for(Object obj: values){
					query.setParameter(i, obj);
				}
			}
			query.executeUpdate();
			
			getJpaTemplate().flush();
			
		}catch (Exception e) {
			log.error("[eliminarPorFiltros] Error al eliminar las entidades por filtros "+filtros.toString(), e);
			throw new DaoException(e, log, "buscarPorId");
		}
	}
}
