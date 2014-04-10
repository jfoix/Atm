package cl.jfoix.atm.ot.stock.util;

public enum TipoMovimientoEnum {

	INGRESO("Ingreso"),
	EGRESO("Egreso"),
	MODIFICACION("Modificacion");
	
	private final String descripcion;
	
	TipoMovimientoEnum(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescription(){
		return descripcion;
	}
}
