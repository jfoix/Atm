package cl.jfoix.atm.comun.service;

import java.util.List;

import cl.jfoix.atm.comun.entity.MantencionProgramada;

public interface IMantencionProgramadaService {

	void guardarMantencionProgramada(MantencionProgramada mantencionProgramada) throws Exception;
	
	void modificarMantencionProgramad(MantencionProgramada mantencionProgramada);
	
	List<MantencionProgramada> buscarMantencionesProgramadas();

	List<MantencionProgramada> buscarMantencionProgramadaPorCodigoDescripcion(String codigo, String descripcionMarca);

	MantencionProgramada buscarMantencionProgramadPorId(Integer idMantencionProgramada);
}
