package cl.jfoix.atm.dbutil.dao;

import java.io.Serializable;
import java.util.List;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;

public interface IComunDao<T extends Serializable, ID extends Serializable> {

	/**
	 * Método encargado de crear la entidad que se pasa como parámetro<br>
	 * 
	 * @param T entity, donde T es una clase de tipo entidad
	 * @exception DaoException, devuelve si ocurre algún error al persistir la entidad
	 */
	void guardar(T entity) throws DaoException;

	/**
	 * Método encargado de modificar la entidad que se pasa como parámetro<br>
	 * 
	 * @param T entity, donde T es una clase de tipo entidad
	 * @exception DaoException, devuelve si ocurre algún error al realizar el merge de la entidad
	 */
	void modificar(T entity) throws DaoException;

	/**
	 * Método encargado de eliminar la entidad que se pasa como parámetro<br>
	 * 
	 * @param T entity, donde T es una clase de tipo entidad. El objeto debe estar atachado a la transacción
	 * @exception DaoException, devuelve si ocurre algún error al remover la entidad
	 */
	void eliminar(T entity) throws DaoException;

	/**
	 * Método encargado de buscar una entidad del tipo de clase enviada y que corresponda al id entregado<br>
	 * 
	 * @param Object id, representa a @Id de la entidad T que se desea buscar
	 * @return T, representa a la entidad encontrada por el id entregado
	 * @exception DaoException, devuelve si ocurre algún error buscar la entidad por Id
	 */
	T buscarPorId(ID id) throws DaoException;

	/**
	 * Método encargado de buscar una lista de entidades del tipo de clase enviada y que corresponda a los filtros entregados<br>
	 * 
	 * @param List<Filtro> filtros, representa a un listado de filtros que se realizarán a la consulta
	 * @param String clausulaOrden, contiene la claúsula que continua luego del 'order by'
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	List<T> buscarPorFiltros(List<Filtro> filtros, String clausulaOrden, String... joins) throws DaoException;

	/**
	 * Método encargado de buscar una lista de entidades del tipo de clase enviada<br>
	 * Este método retorna todos los registros del tipo de entidad, ya que no van con filtros
	 * 
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	List<T> buscarTodos() throws DaoException;

	/**
	 * Método encargado de buscar una lista de entidades del tipo de clase enviada ordenada según lo enviado<br>
	 * Este método retorna todos los registros del tipo de entidad, ya que no van con filtros
	 * 
	 * @param String clausulaOrden, contiene la claúsula que continua luego del 'order by'
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	List<T> buscarTodosOrdenados(String clausulaOrden) throws DaoException;

	/**
	 * Método encargado de eliminar una lista de entidades del tipo de clase enviada y que corresponda a los filtros entregados<br>
	 * 
	 * @param List<Filtro> filtros, representa a un listado de filtros que se realizarán a la consulta
	 * @param String clausulaOrden, contiene la claúsula que continua luego del 'order by'
	 * @return List<T>, representa a la lista de entidades encontradas al ejecutar la consulta
	 * @exception DaoException, devuelve si ocurre algún error al ejecutar la consulta
	 */
	void eliminarPorFiltros(List<Filtro> filtros) throws DaoException;

	List<T> buscarPorFiltros(List<Filtro> filtros, String clausulaOrden, boolean distinct, String...joins) throws DaoException;

}
