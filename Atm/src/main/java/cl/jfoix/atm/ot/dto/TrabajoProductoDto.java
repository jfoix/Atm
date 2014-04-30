package cl.jfoix.atm.ot.dto;

import java.io.Serializable;
import java.util.List;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.entity.Trabajo;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;

public class TrabajoProductoDto implements Serializable {

	private static final long serialVersionUID = 9009179343016080564L;

	public static final Integer WORK_TYPE = 1;
	public static final Integer PRODUCT_TYPE = 2;
	
	private Trabajo trabajo;
	private OrdenTrabajo ot;
	private Producto producto;
	private Double cantidad;
	private Long precio;
	private Double hhEstimadas;
	private Integer tipo;
	private boolean garantia;
	private boolean productoCliente;
	private Integer indice;
	private Integer indicePadre;
	private List<Usuario> mecanicos;
	private boolean tieneStock;
	
	public TrabajoProductoDto(Trabajo trabajo, Producto producto, Double cantidad, Double hhEstimadas, Integer tipo){
		this.trabajo = trabajo;
		this.precio = trabajo != null ? trabajo.getPrecioManoObra() : 0;
		this.producto = producto;
		this.cantidad = cantidad;
		this.hhEstimadas = hhEstimadas;
		this.tipo = tipo;
		this.tieneStock = true;
	}
	
	public TrabajoProductoDto(Trabajo trabajo, Producto producto, Double cantidad, Double hhEstimadas, Integer tipo, boolean garantia, boolean productoCliente){
		this.trabajo = trabajo;
		this.precio = trabajo != null ? trabajo.getPrecioManoObra() : 0;
		this.producto = producto;
		this.cantidad = cantidad;
		this.hhEstimadas = hhEstimadas;
		this.tipo = tipo;
		this.garantia = garantia;
		this.productoCliente = productoCliente;
		this.tieneStock = true;
	}
	
	public TrabajoProductoDto(OrdenTrabajo ot, Producto producto, Double cantidad, Double hhEstimadas, Integer tipo, boolean garantia, boolean productoCliente){
		this.ot = ot;
		this.trabajo = ot.getTrabajo();
		this.precio = ot.getPrecioManoObra().longValue();
		this.producto = producto;
		this.cantidad = cantidad;
		this.hhEstimadas = hhEstimadas;
		this.tipo = tipo;
		this.garantia = garantia;
		this.productoCliente = productoCliente;
		this.tieneStock = true;
	}
	
	public TrabajoProductoDto(Producto producto, Double cantidad, Integer valor, Double hhEstimadas, Integer tipo, boolean garantia, boolean productoCliente, boolean tieneStock){
		this.producto = producto;
		this.cantidad = cantidad;
		
		if(valor != null){
			this.precio = valor.longValue();
		}
		
		this.hhEstimadas = hhEstimadas;
		this.tipo = tipo;
		this.garantia = garantia;
		this.productoCliente = productoCliente;
		this.tieneStock = tieneStock;
	}
	
	public String getTrabajoCodigo(){
		return this.trabajo == null ? "" : this.trabajo.getCodigo();
	}
	
	public String getTrabajoDescripcion(){
		return this.trabajo == null ? "" : this.trabajo.getCodigo() + " - " + this.trabajo.getDescripcion();
	}
	
	public String getProductoDescripcion(){
		return this.producto == null ? "-" : this.producto.getCodigo() + " - " + this.producto.getDescripcion();
	}

	/**
	 * @return the trabajo
	 */
	public Trabajo getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * @return the cantidad
	 */
	public Double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the hhEstimadas
	 */
	public Double getHhEstimadas() {
		return hhEstimadas;
	}

	/**
	 * @param hhEstimadas the hhEstimadas to set
	 */
	public void setHhEstimadas(Double hhEstimadas) {
		this.hhEstimadas = hhEstimadas;
	}

	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the garantia
	 */
	public boolean isGarantia() {
		return garantia;
	}

	/**
	 * @param garantia the garantia to set
	 */
	public void setGarantia(boolean garantia) {
		this.garantia = garantia;
	}

	/**
	 * @return the productoCliente
	 */
	public boolean isProductoCliente() {
		return productoCliente;
	}

	/**
	 * @param productoCliente the productoCliente to set
	 */
	public void setProductoCliente(boolean productoCliente) {
		this.productoCliente = productoCliente;
	}

	/**
	 * @return the indice
	 */
	public Integer getIndice() {
		return indice;
	}

	/**
	 * @param indice the indice to set
	 */
	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	/**
	 * @return the indicePadre
	 */
	public Integer getIndicePadre() {
		return indicePadre;
	}

	/**
	 * @param indicePadre the indicePadre to set
	 */
	public void setIndicePadre(Integer indicePadre) {
		this.indicePadre = indicePadre;
	}

	/**
	 * @return the mecanicos
	 */
	public List<Usuario> getMecanicos() {
		return mecanicos;
	}

	/**
	 * @param mecanicos the mecanicos to set
	 */
	public void setMecanicos(List<Usuario> mecanicos) {
		this.mecanicos = mecanicos;
	}

	/**
	 * @return the ot
	 */
	public OrdenTrabajo getOt() {
		return ot;
	}

	/**
	 * @param ot the ot to set
	 */
	public void setOt(OrdenTrabajo ot) {
		this.ot = ot;
	}

	/**
	 * @return the precio
	 */
	public Long getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	/**
	 * @return the tieneStock
	 */
	public boolean isTieneStock() {
		return tieneStock;
	}

	/**
	 * @param tieneStock the tieneStock to set
	 */
	public void setTieneStock(boolean tieneStock) {
		this.tieneStock = tieneStock;
	}
}
