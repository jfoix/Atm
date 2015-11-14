package cl.jfoix.atm.ot.stock.dto;

public class FiltroBusquedaStockDto {

	private Integer idProducto;
	private Integer idProveedor;
	private Integer idBodega;
	private Integer idStock;
	private Integer idProductoGrupo;
	private Integer idMarca;	
	private String codigo;
	private String descripcion;
	private String coordBodega;
	
	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	/**
	 * @return the idProveedor
	 */
	public Integer getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the idBodega
	 */
	public Integer getIdBodega() {
		return idBodega != null && idBodega.equals(-1) ? null : idBodega;
	}
	/**
	 * @param idBodega the idBodega to set
	 */
	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}
	/**
	 * @return the idStock
	 */
	public Integer getIdStock() {
		return idStock;
	}
	/**
	 * @param idStock the idStock to set
	 */
	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo != null && codigo.trim().equals("") ? null : codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion != null && descripcion.trim().equals("") ? null : descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the idProductoGrupo
	 */
	public Integer getIdProductoGrupo() {
		return idProductoGrupo != null && idProductoGrupo.equals(-1) ? null : idProductoGrupo;
	}
	/**
	 * @param idProductoGrupo the idProductoGrupo to set
	 */
	public void setIdProductoGrupo(Integer idProductoGrupo) {
		this.idProductoGrupo = idProductoGrupo;
	}
	/**
	 * @return the idMarca
	 */
	public Integer getIdMarca() {
		return idMarca != null && idMarca.equals(-1) ? null : idMarca;
	}
	/**
	 * @param idMarca the idMarca to set
	 */
	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}
	/**
	 * @return the coordBodega
	 */
	public String getCoordBodega() {
		return coordBodega != null && !coordBodega.equals("") ? coordBodega : null;
	}
	/**
	 * @param coordBodega the coordBodega to set
	 */
	public void setCoordBodega(String coordBodega) {
		this.coordBodega = coordBodega;
	}
}
