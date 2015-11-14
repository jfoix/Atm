package cl.jfoix.atm.comun.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.jfoix.atm.comun.dao.IProductoDao;
import cl.jfoix.atm.comun.dto.ProductoDto;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.dbutil.dao.impl.JpaComunDao;

@Repository
public class JPAProducto extends JpaComunDao<Producto, Integer> implements IProductoDao {

	@Autowired
	public JPAProducto(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	@Override
	public List<ProductoDto> buscarProductosAgrupados(Integer idOrdenTrabajo){
		
		StringBuilder stbQuery = new StringBuilder();
		stbQuery.append(" SELECT otp.producto.codigo, otp.producto.descripcion, otp.producto.productoGrupo.codigo, otp.ordenTrabajo.orden.idOrden, otp.producto.idProducto, SUM(otp.cantidad) AS cantidad ");
		stbQuery.append(" FROM OrdenTrabajoProducto otp ");
		stbQuery.append(" WHERE ordenTrabajo.idOrdenTrabajo = ?1 ");
		stbQuery.append(" GROUP BY otp.producto.codigo, otp.producto.descripcion, otp.producto.productoGrupo.codigo ");
		
		List<?> result = getJpaTemplate().find(stbQuery.toString(), idOrdenTrabajo);
		
		if(result != null && result.size() > 0){
			
			List<ProductoDto> productos = new ArrayList<ProductoDto>();
			
			for(Object obj : result){
				
				Object[] fila = (Object[]) obj;
				
				ProductoDto producto = new ProductoDto();
				producto.setCodigo(fila[0].toString());
				producto.setNombreProducto(fila[1].toString());
				producto.setCodigoGrupo(fila[2].toString());
				producto.setIdOrden(Integer.parseInt(fila[3].toString()));
				producto.setIdProducto(Integer.parseInt(fila[4].toString()));
				producto.setCantidad(Double.parseDouble(fila[5].toString()));
				
				productos.add(producto);
			}
			
			return productos;
		} 
		
		return null;
	}
	
	@Override
	public List<Producto> buscarProductosPorFiltros(Integer idProducto,
			Integer idProductoGrupo,
			Integer idMarca,
			String codigo, 
			Boolean estado, 
			String descripcion){
		
		StringBuilder stbQuery = new StringBuilder();
		stbQuery.append(" SELECT p FROM Producto p WHERE 1=1");
		
		if(idProducto != null){
			stbQuery.append(" AND p.idProducto = " + idProducto.toString() + " ");
		}
		
		if(idProductoGrupo != null){
			stbQuery.append(" AND p.productoGrupo.idProductoGrupo = " + idProductoGrupo.toString() + " ");
		}
		
		if(idMarca != null){
			stbQuery.append(" AND p.marca.idMarca = " + idMarca.toString() + " ");
		}
		
		if(estado != null){
			stbQuery.append(" AND p.estado = " + estado.toString() + " ");
		}
		
		if(descripcion != null){
			stbQuery.append(" AND p.descripcion LIKE '%" + descripcion.toString() + "%' ");
		}
		
		if(codigo != null){
			stbQuery.append(" AND CONCAT(p.productoGrupo.codigo, p.codigo) LIKE '%" + codigo.toString() + "%' ");
		}
		
		stbQuery.append(" ORDER BY p.productoGrupo.codigo, p.codigo ASC");
		
		return getJpaTemplate().find(stbQuery.toString());
	}
}
