package cl.jfoix.atm.ot.stock.dto;

public class FiltroBusquedaStockDto {

	private Integer idProducto;
	private Integer idProveedor;
	private Integer idBodega;
	private Integer idStock;
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
		return idBodega;
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
}
