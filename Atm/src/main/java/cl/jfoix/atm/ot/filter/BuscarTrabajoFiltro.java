package cl.jfoix.atm.ot.filter;

import java.io.Serializable;

public class BuscarTrabajoFiltro implements Serializable {

	private static final long serialVersionUID = 7070075978831680192L;

	private String codigo;
	private String desc;
	private Integer tipo;
	private Integer subTipo;
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
	 * @return the subTipo
	 */
	public Integer getSubTipo() {
		return subTipo;
	}
	/**
	 * @param subTipo the subTipo to set
	 */
	public void setSubTipo(Integer subTipo) {
		this.subTipo = subTipo;
	}
}	
