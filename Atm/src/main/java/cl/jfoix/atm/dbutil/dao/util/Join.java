package cl.jfoix.atm.dbutil.dao.util;

/**
 * Clase que representa la union entre una tabla y la union con una OneToMany
 * @author Jangenni
 *
 */
public class Join {
	
	/**
	 * Clausula join sin alias
	 */
	private String joinName;

	/**
	 * @return the joinName
	 */
	public String getJoinName() {
		return joinName;
	}

	/**
	 * @param joinName the joinName to set
	 */
	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}
}

