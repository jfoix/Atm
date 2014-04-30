package cl.jfoix.atm.dbutil.dao.util;

/**
 * Enum que representa a las posibles operaciones soportadas<br>
 * por <strong>JpaComunDao.buscarPorFiltros</strong>.
 * <code>
 * 	EQUAL ("=") <br>
 *	NOT_EQUAL ("!=") <br>
 *	IN ("in")<br>
 *	NOT_IN ("not in")<br>
 *	BETWEEN ("between")<br>
 *	MAYOR_QUE (">")<br>
 *	MAYOR_IGUAL_QUE (">=")<br>
 *	MENOR_QUE ("<")<br>
 *	MENOR_IGUAL_QUE ("<=")<br>
 *  LIKE_INICIO ("like")<br>
 *	LIKE_FIN ("like")<br>
 *	LIKE_COMPLETO ("like")<br>
 * </code>
 * 
 * @author César Abarza S.
 * @version 1.0
 */
public enum TipoOperacionFiltroEnum {
	EQUAL (" = "),
	NOT_EQUAL (" != "),
	IN (" in "),
	NOT_IN (" not in "),
	BETWEEN (" between "),
	MAYOR_QUE (" > "),
	MAYOR_IGUAL_QUE (" >= "),
	MENOR_QUE (" < "),
	MENOR_IGUAL_QUE (" <= "),
	LIKE_INICIO (" like "),
	LIKE_FIN (" like "),
	LIKE_COMPLETO (" like "),
	IS_NULL (" IS NULL "),
	EXISTS (" exists "),
	NOT_EXISTS (" not exists ");
	
	private String operacion;
	
	TipoOperacionFiltroEnum(String operacion){
		this.setOperacion(operacion);
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
}
