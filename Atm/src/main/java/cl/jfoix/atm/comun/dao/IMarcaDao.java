package cl.jfoix.atm.comun.dao;

import java.util.List;

import cl.jfoix.atm.comun.entity.Marca;
import cl.jfoix.atm.dbutil.dao.IComunDao;

public interface IMarcaDao extends IComunDao<Marca, Integer> {

	List<Marca> buscarMarcasPorGrupoProducto(Integer idProductoGrupo); }
