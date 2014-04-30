package cl.jfoix.atm.ot.dto;

import java.util.ArrayList;
import java.util.List;

import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;

public class ResumentOTDto {

	private Orden orden;
	private List<OrdenTrabajo> trabajos;
	private List<OrdenTrabajoProducto> productos;
	private List<OrdenTrabajo> trabajosTerceros;
	
	private String observacion;
	
	public void agregarProductos(List<OrdenTrabajoProducto> productos){
		if(this.productos == null){
			this.productos = new ArrayList<OrdenTrabajoProducto>();
		}
		this.productos.addAll(productos);
	}
	
	public Integer getTotalTrabajos(){
		if(trabajos != null){
			
			Integer totalTrabajos = 0;
			
			for(OrdenTrabajo ot : trabajos){
				totalTrabajos += ot.getPrecioManoObra();
			}
			
			return totalTrabajos;
		} else {
			return 0;
		}
	}
	
	public Integer getTotalTrabajosServicioTerceros(){
		if(trabajosTerceros != null){
			
			Integer totalTrabajos = 0;
			
			for(OrdenTrabajo ot : trabajosTerceros){
				totalTrabajos += ot.getPrecioManoObra();
			}
			
			return totalTrabajos;
		} else {
			return 0;
		}
	}
	
	public Integer getTotalProductos(){
		if(productos != null){
			
			Integer totalProductos = 0;
			
			for(OrdenTrabajoProducto otp : productos){
				totalProductos += otp.getValor();
			}
			
			return totalProductos;
		} else {
			return 0;
		}
	}
	
	/**
	 * @return the orden
	 */
	public Orden getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Orden orden) {
		this.orden = orden;
	}
	/**
	 * @return the trabajos
	 */
	public List<OrdenTrabajo> getTrabajos() {
		return trabajos;
	}
	/**
	 * @param trabajos the trabajos to set
	 */
	public void setTrabajos(List<OrdenTrabajo> trabajos) {
		this.trabajos = trabajos;
	}
	/**
	 * @return the productos
	 */
	public List<OrdenTrabajoProducto> getProductos() {
		return productos;
	}
	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<OrdenTrabajoProducto> productos) {
		this.productos = productos;
	}

	/**
	 * @return the trabajosTerceros
	 */
	public List<OrdenTrabajo> getTrabajosTerceros() {
		return trabajosTerceros;
	}

	/**
	 * @param trabajosTerceros the trabajosTerceros to set
	 */
	public void setTrabajosTerceros(List<OrdenTrabajo> trabajosTerceros) {
		this.trabajosTerceros = trabajosTerceros;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
