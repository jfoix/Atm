package cl.jfoix.atm.ot.dao;

import java.util.List;

import cl.jfoix.atm.dbutil.dao.IComunDao;
import cl.jfoix.atm.ot.entity.Stock;

public interface IStockDao extends IComunDao<Stock, Integer> {

	List<Stock> buscarProductosPorFiltros(Integer idProductoGrupo,
			Integer idMarca, String codigo, Boolean estado, String descripcion,
			Integer idBodega,
			String coordBodega); 
}