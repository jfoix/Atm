package cl.jfoix.atm.admin.service;

import java.util.List;

import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.login.entity.Persona;

public interface IPersonaService {

	public void guardarPersona(Persona persona) throws ViewException;
	
	public List<Persona> buscarTodasPersonas();

	public List<Persona> buscarPersonaPorNombre(String nombre);

	public Persona buscarPersonaPorRut(String rut);

}
