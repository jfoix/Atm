package cl.jfoix.atm.ot.util;

public enum TipoOrdenObservacionEnum {

	INGRESO("Ingreso OT"),
	EN_TRABAJO("En Trabajo"),
	CAMBIO_ESTADO("Cambio Estado"),
	FINALIZACION("Final");
	
	private final String descripcion;
	
	TipoOrdenObservacionEnum(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescription(){
		return descripcion;
	}
}
