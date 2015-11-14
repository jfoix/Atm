package cl.jfoix.atm.ot.dao.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;
import cl.jfoix.atm.ot.dao.IStockDao;
import cl.jfoix.atm.ot.entity.Stock;

@Repository
public class JPAStock extends JpaComunDao<Stock, Integer> implements IStockDao {

	@Autowired
	public JPAStock(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	@Override
	public List<Stock> buscarProductosPorFiltros(
			Integer idProductoGrupo,
			Integer idMarca,
			String codigo, 
			Boolean estado, 
			String descripcion,
			Integer idBodega,
			String coordBodega){
		
		StringBuilder stbQuery = new StringBuilder();
		stbQuery.append(" SELECT s FROM Stock s WHERE 1=1");
		
		if(idBodega != null){
			stbQuery.append(" AND s.bodega.idBodega = " + idBodega.toString() + " ");
		}
		
		if(idProductoGrupo != null){
			stbQuery.append(" AND s.producto.productoGrupo.idProductoGrupo = " + idProductoGrupo.toString() + " ");
		}
		
		if(idMarca != null){
			stbQuery.append(" AND s.producto.marca.idMarca = " + idMarca.toString() + " ");
		}
		
		if(estado != null){
			stbQuery.append(" AND s.producto.estado = " + estado.toString() + " ");
		}
		
		if(descripcion != null){
			stbQuery.append(" AND s.producto.descripcion LIKE '%" + descripcion.toString() + "%' ");
		}
		
		if(codigo != null){
			stbQuery.append(" AND CONCAT(s.producto.productoGrupo.codigo, s.producto.codigo) LIKE '%" + codigo.toString() + "%' ");
		}
		
		if(coordBodega != null){
			stbQuery.append(" AND s.coordBodega LIKE '%" + coordBodega + "%' ");
		}
		
		stbQuery.append(" ORDER BY s.producto.productoGrupo.codigo, s.producto.codigo ASC");
		
		return getJpaTemplate().find(stbQuery.toString());
	}
}
