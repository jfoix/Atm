package cl.jfoix.atm.ot.dto;

import java.util.ArrayList;
import java.util.List;

import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;

public class ResumentMecanicoDto {

	private Usuario usuario;
	private List<OrdenTrabajo> ingresado;
	private List<OrdenTrabajo> enProceso;
	private List<OrdenTrabajo> cancelados;
	private List<OrdenTrabajo> finalizados;
	
	/**
	 * @return the ingresado
	 */
	public List<OrdenTrabajo> getIngresado() {
		if(ingresado == null){
			ingresado = new ArrayList<OrdenTrabajo>();
		}
		return ingresado;
	}
	/**
	 * @param ingresado the ingresado to set
	 */
	public void setIngresado(List<OrdenTrabajo> ingresado) {
		this.ingresado = ingresado;
	}
	/**
	 * @return the enProceso
	 */
	public List<OrdenTrabajo> getEnProceso() {
		if(enProceso == null){
			enProceso = new ArrayList<OrdenTrabajo>();
		}
		return enProceso;
	}
	/**
	 * @param enProceso the enProceso to set
	 */
	public void setEnProceso(List<OrdenTrabajo> enProceso) {
		this.enProceso = enProceso;
	}
	/**
	 * @return the cancelados
	 */
	public List<OrdenTrabajo> getCancelados() {
		if(cancelados == null){
			cancelados = new ArrayList<OrdenTrabajo>();
		}
		return cancelados;
	}
	/**
	 * @param cancelados the cancelados to set
	 */
	public void setCancelados(List<OrdenTrabajo> cancelados) {
		this.cancelados = cancelados;
	}
	/**
	 * @return the finalizados
	 */
	public List<OrdenTrabajo> getFinalizados() {
		if(finalizados == null){
			finalizados = new ArrayList<OrdenTrabajo>();
		}
		return finalizados;
	}
	/**
	 * @param finalizados the finalizados to set
	 */
	public void setFinalizados(List<OrdenTrabajo> finalizados) {
		this.finalizados = finalizados;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
