package cl.jfoix.atm.ot.stock.util;

public enum TipoMovimientoEnum {

	INGRESO("Ingreso"),
	EGRESO("Egreso"),
	AJUSTE("Ajuste");
	
	private final String descripcion;
	
	TipoMovimientoEnum(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescription(){
		return descripcion;
	}
}
