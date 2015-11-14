package cl.jfoix.atm.ot.dto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoProducto;

public class ResumentOTDto {

	private Orden orden;
	private List<OrdenTrabajo> trabajos;
	private List<OrdenTrabajoProducto> productos;
	private List<OrdenTrabajo> trabajosTerceros;
	private Integer descuentoRepuesto = 0;
	private Integer descuentoManoObra = 0;
	private Integer descuentoSTercero = 0;
	private Double iva;
	private boolean ivaRepuesto;
	private boolean ivaManoObra;
	private boolean ivaSTercero;
	
	private String tipoDescRepuesto = "";
	private String tipoDescManoObra = "";
	private String tipoDescSTercero = "";
	
	private Map<String, Boolean> totalesIVA;
	
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

	/**
	 * @return the descuentoRepuesto
	 */
	public Integer getDescuentoRepuesto() {
		
		Integer totalP = getTotalProductos();
		
		if(orden.getDescuentoRepuestos() != null && !orden.getDescuentoRepuestos().equals(0d)){
			if(orden.getTipoDescRepuestos().equals("%")){
				descuentoRepuesto = (int)Math.round(totalP * (orden.getDescuentoRepuestos() / 100));
			} else { 
				descuentoRepuesto = orden.getDescuentoRepuestos().intValue();
			}
		}
		
		return descuentoRepuesto;
	}

	/**
	 * @param descuentoRepuesto the descuentoRepuesto to set
	 */
	public void setDescuentoRepuesto(Integer descuentoRepuesto) {
		this.descuentoRepuesto = descuentoRepuesto;
	}

	/**
	 * @return the descuentoManoObra
	 */
	public Integer getDescuentoManoObra() {
		
		Integer totalM = getTotalTrabajos();
		
		if(orden.getDescuentoManoObra() != null && !orden.getDescuentoManoObra().equals(0d)){
			if(orden.getTipoDescManoObra().equals("%")){
				descuentoManoObra = (int)Math.round(totalM * (orden.getDescuentoManoObra() / 100));
			} else { 
				descuentoManoObra = orden.getDescuentoManoObra().intValue();
			}
		}
		
		return descuentoManoObra;
	}

	/**
	 * @param descuentoManoObra the descuentoManoObra to set
	 */
	public void setDescuentoManoObra(Integer descuentoManoObra) {
		this.descuentoManoObra = descuentoManoObra;
	}

	/**
	 * @return the descuentoSTercero
	 */
	public Integer getDescuentoSTercero() {
		
		Integer totalT = getTotalTrabajosServicioTerceros();
		
		if(orden.getDescuentoTerceros() != null && !orden.getDescuentoTerceros().equals(0d)){
			if(orden.getTipoDescTerceros().equals("%")){
				descuentoSTercero = (int)Math.round(totalT * (orden.getDescuentoTerceros() / 100));
			} else { 
				descuentoSTercero = orden.getDescuentoTerceros().intValue();
			}
		}
		
		return descuentoSTercero;
	}

	/**
	 * @param descuentoSTercero the descuentoSTercero to set
	 */
	public void setDescuentoSTercero(Integer descuentoSTercero) {
		this.descuentoSTercero = descuentoSTercero;
	}

	/**
	 * @return the iva
	 */
	public Double getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
	}

	/**
	 * @return the ivaRepuesto
	 */
	public boolean isIvaRepuesto() {
		ivaRepuesto = !totalesIVA.containsKey("ivaRepuesto") ? false : totalesIVA.get("ivaRepuesto");
		return ivaRepuesto;
	}

	/**
	 * @param ivaRepuesto the ivaRepuesto to set
	 */
	public void setIvaRepuesto(boolean ivaRepuesto) {
		this.ivaRepuesto = ivaRepuesto;
	}

	/**
	 * @return the ivaManoObra
	 */
	public boolean isIvaManoObra() {
		ivaManoObra = !totalesIVA.containsKey("ivaManoObra") ? false : totalesIVA.get("ivaManoObra");
		return ivaManoObra;
	}

	/**
	 * @param ivaManoObra the ivaManoObra to set
	 */
	public void setIvaManoObra(boolean ivaManoObra) {
		this.ivaManoObra = ivaManoObra;
	}

	/**
	 * @return the ivaSTercero
	 */
	public boolean isIvaSTercero() {
		ivaSTercero = !totalesIVA.containsKey("ivaSTercero") ? false : totalesIVA.get("ivaSTercero"); 
		return ivaSTercero;
	}

	/**
	 * @param ivaSTercero the ivaSTercero to set
	 */
	public void setIvaSTercero(boolean ivaSTercero) {
		this.ivaSTercero = ivaSTercero;
	}

	/**
	 * @return the totalesIVA
	 */
	public Map<String, Boolean> getTotalesIVA() {
		return totalesIVA;
	}

	/**
	 * @param totalesIVA the totalesIVA to set
	 */
	public void setTotalesIVA(Map<String, Boolean> totalesIVA) {
		this.totalesIVA = totalesIVA;
	}

	/**
	 * @return the tipoDescRepuesto
	 */
	public String getTipoDescRepuesto() {
		
		if(orden.getDescuentoRepuestos() != null && !orden.getDescuentoRepuestos().equals(0d)){
			tipoDescRepuesto = "(" + (orden.getTipoDescRepuestos().equals("%") ? orden.getDescuentoRepuestos().toString() + "%" : "$ " + NumberFormat.getInstance().format(orden.getDescuentoRepuestos())) + ")";
		} else {
			tipoDescRepuesto = "";
		}
		
		return tipoDescRepuesto;
	}

	/**
	 * @param tipoDescRepuesto the tipoDescRepuesto to set
	 */
	public void setTipoDescRepuesto(String tipoDescRepuesto) {
		this.tipoDescRepuesto = tipoDescRepuesto;
	}

	/**
	 * @return the tipoDescManoObra
	 */
	public String getTipoDescManoObra() {
		
		if(orden.getDescuentoManoObra() != null && !orden.getDescuentoManoObra().equals(0d)){
			tipoDescManoObra = "(" + (orden.getTipoDescManoObra().equals("%") ? orden.getDescuentoManoObra().toString() + "%" : "$ " + NumberFormat.getInstance().format(orden.getDescuentoManoObra())) + ")";
		} else {
			tipoDescManoObra = "";
		}
		
		return tipoDescManoObra;
	}

	/**
	 * @param tipoDescManoObra the tipoDescManoObra to set
	 */
	public void setTipoDescManoObra(String tipoDescManoObra) {
		this.tipoDescManoObra = tipoDescManoObra;
	}

	/**
	 * @return the tipoDescSTercero
	 */
	public String getTipoDescSTercero() {
		
		if(orden.getDescuentoTerceros() != null && !orden.getDescuentoTerceros().equals(0d)){
			tipoDescSTercero = "(" + (orden.getTipoDescTerceros().equals("%") ? orden.getDescuentoTerceros().toString() + "%" : "$ " + NumberFormat.getInstance().format(orden.getDescuentoTerceros())) + ")";
		} else {
			tipoDescSTercero = "";
		}
		
		return tipoDescSTercero;
	}

	/**
	 * @param tipoDescSTercero the tipoDescSTercero to set
	 */
	public void setTipoDescSTercero(String tipoDescSTercero) {
		this.tipoDescSTercero = tipoDescSTercero;
	}
}
