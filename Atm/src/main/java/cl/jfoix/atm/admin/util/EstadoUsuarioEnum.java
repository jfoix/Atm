package cl.jfoix.atm.admin.util;

public enum EstadoUsuarioEnum {

	ACTIVO("Activo"),
	INACTIVO("Inactivo"),
	ELIMINADO("Eliminado");
	
	private final String descripcion;
	
	EstadoUsuarioEnum(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescription(){
		return descripcion;
	}
}
