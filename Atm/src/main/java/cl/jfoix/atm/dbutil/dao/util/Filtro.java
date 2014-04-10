/**
 * 
 */
package cl.jfoix.atm.dbutil.dao.util;


/**
 * Clase que representa los filtros dinámicos para
 * el <strong>JpaComunDao.buscarPorFiltros</strong>.
 * 
 * @author César Abarza S.
 * @version 1.0
 *
 */
public class Filtro {
	/**
	 * Representa al nombre de la propiedad en donde se aplicará el filtro
	 */
	private String propiedad;
	/**
	 * Representa a la operación a aplicar con el filtro
	 * @see TipoParametroEnum
	 */
	private TipoOperacionFiltroEnum tipoOperacion;
	/**
	 * Representa al filtro como tal
	 * Se explica lo que se espera para cada tipo de operación<br> 
	 * <code>
	 * 	EQUAL ("=") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) <br>
	 *	NOT_EQUAL ("!=") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) <br>
	 *	IN ("in") Se espera un arreglo de valores, (String[], Date[], Integer[], Long[], Double[], Float[], entre otros) <br>
	 *	NOT_IN ("not in") Se espera un arreglo de valores, (String[], Date[], Integer[], Long[], Double[], Float[], entre otros) <br>
	 *	BETWEEN ("between")Se espera un arreglo o lista de valores ordenados, debe contener 2 valores (String, Date, Integer, Long, Double, Float, entre otros)<br>
	 *	MAYOR_QUE (">") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) <br>
	 *	MAYOR_IGUAL_QUE (">=") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) <br>
	 *	MENOR_QUE ("<") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) <br>
	 *	MENOR_IGUAL_QUE ("<=") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) <br>
	 *  LIKE_INICIO (" like ") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) Ejemplo, x like '%valor'<br>
	 *	LIKE_FIN (" like ") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) Ejemplo, x like 'valor%'<br>
	 *	LIKE_COMPLETO (" like ") Se espera un valor único, (String, Date, Integer, Long, Double, Float, entre otros) Ejemplo, x like '%valor%'<br>
	 * </code>
	 */
	private Object valorFiltro;
	
	
	public String getPropiedad() {
		return propiedad;
	}
	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}
	public TipoOperacionFiltroEnum getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(TipoOperacionFiltroEnum tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public Object getValorFiltro() {
		return valorFiltro;
	}
	public void setValorFiltro(Object valorFiltro) {
		this.valorFiltro = valorFiltro;
	}
	
	public Filtro(){
		super();
	}
	
	public Filtro(String propiedad, TipoOperacionFiltroEnum tipoOperacion, Object valorFiltro) {
		super();
		this.propiedad = propiedad;
		this.tipoOperacion = tipoOperacion;
		this.valorFiltro = valorFiltro;
	}
	@Override
	public String toString() {
		return "Filtro:{ propiedad:"+propiedad+", tipoOperacion:"+tipoOperacion.name()+", valorFiltro:"+valorFiltro.toString()+"}\n";
	}
	
}
