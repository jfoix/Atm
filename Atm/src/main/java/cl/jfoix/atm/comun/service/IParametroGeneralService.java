package cl.jfoix.atm.comun.service;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.entity.ParametroGeneral;

public interface IParametroGeneralService {

	public void guardarParametroGeneral(ParametroGeneral parametroGeneral) throws Exception;
	
	public List<ParametroGeneral> buscarPorGrupo(String codGrupo);

	public String buscarString(String codigo);
	
	public Integer buscarInteger(String codigo);
	
	public Date buscarFecha(String codigo);

	List<ParametroGeneral> buscarPorCodigoGrupo(Integer tipoBusquda, String codigo);
}
