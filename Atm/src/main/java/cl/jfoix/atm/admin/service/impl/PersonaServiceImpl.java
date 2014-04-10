package cl.jfoix.atm.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.admin.dao.IPersonaDao;
import cl.jfoix.atm.admin.service.IPersonaService;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.login.entity.Persona;

@Service("personaService")
public class PersonaServiceImpl implements IPersonaService {

	private static Logger log = Logger.getLogger(PersonaServiceImpl.class);

	@Autowired
	private IPersonaDao personaDao;

	@Transactional
	@Override
	public void guardarPersona(Persona persona) throws ViewException {
		try{
			if(persona.getIdPersona() == null){
				persona.setEstado(true);
				persona.setFechaRegistro(new Date());
				personaDao.guardar(persona);
			} else {
				personaDao.modificar(persona);
			}
		}catch(DaoException e){
			log.error("Erro al guardar la persona", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
		
	}

	@Override
	public List<Persona> buscarTodasPersonas() {
		
		try {
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return personaDao.buscarPorFiltros(filtros, null);
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Persona> buscarPersonaPorNombre(String nombre) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!nombre.equals("")){
				filtros.add(new Filtro("nombres", TipoOperacionFiltroEnum.LIKE_COMPLETO, nombre));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			return personaDao.buscarPorFiltros(filtros, null);
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Persona buscarPersonaPorRut(String rut) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			filtros.add(new Filtro("rut", TipoOperacionFiltroEnum.EQUAL, rut));
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));

			List<?> resultado = personaDao.buscarPorFiltros(filtros, null);
			
			if(resultado != null && resultado.size() >0){
				return (Persona) resultado.get(0);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
