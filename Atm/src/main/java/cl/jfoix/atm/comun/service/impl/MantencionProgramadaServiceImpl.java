package cl.jfoix.atm.comun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IMantencionProgramadaDao;
import cl.jfoix.atm.comun.dao.IMantencionProgramadaTrabajoDao;
import cl.jfoix.atm.comun.entity.MantencionProgramada;
import cl.jfoix.atm.comun.entity.MantencionProgramadaTrabajo;
import cl.jfoix.atm.comun.entity.MantencionProgramadaTrabajoPK;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IMantencionProgramadaService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("mantencionProgramadaService")
public class MantencionProgramadaServiceImpl implements IMantencionProgramadaService {

	private static Logger log = Logger.getLogger(MantencionProgramadaServiceImpl.class);

	@Autowired
	private IMantencionProgramadaDao mantencionProgramadaDao;
	
	@Autowired
	private IMantencionProgramadaTrabajoDao mantencionProgramadaTrabajoDao;
	
	@Transactional
	@Override
	public void guardarMantencionProgramada(MantencionProgramada mantencionProgramada) throws ViewException {
		try{
			if(mantencionProgramada.getIdMantencionProgramada() == null){
				mantencionProgramada.setEstado(true);
				mantencionProgramadaDao.guardar(mantencionProgramada);
			} else {
				mantencionProgramadaDao.modificar(mantencionProgramada);
			}
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("c.pk.idMantencionProgramada", TipoOperacionFiltroEnum.EQUAL, mantencionProgramada.getIdMantencionProgramada()));
			
			mantencionProgramadaTrabajoDao.eliminarPorFiltros(filtros);
			
			if(mantencionProgramada.getMantencionTrabajos() != null && mantencionProgramada.getMantencionTrabajos().size() > 0){
				for(MantencionProgramadaTrabajo mpt : mantencionProgramada.getMantencionTrabajos()){
					MantencionProgramadaTrabajoPK pk = new MantencionProgramadaTrabajoPK();
					pk.setIdMantencionProgramada(mantencionProgramada.getIdMantencionProgramada());
					pk.setIdTrabajo(mpt.getTrabajo().getIdTrabajo());
					mpt.setPk(pk);
					mantencionProgramadaTrabajoDao.guardar(mpt);
				}
			}
		}catch(DaoException e){
			log.error("Erro al guardar la mantencion programada", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Override
	@Transactional
	public void modificarMantencionProgramad(MantencionProgramada mantencionProgramada) {
		try {
			mantencionProgramadaDao.modificar(mantencionProgramada);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public List<MantencionProgramada> buscarMantencionesProgramadas() {
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			List<MantencionProgramada> mantenciones = mantencionProgramadaDao.buscarPorFiltros(filtros, null);
			
			for(MantencionProgramada mp : mantenciones){
				mp.getMantencionTrabajos().size();
			}
			
			return mantenciones;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public MantencionProgramada buscarMantencionProgramadPorId(Integer idMantencionProgramada) {
		
		try {
			return mantencionProgramadaDao.buscarPorId(idMantencionProgramada);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	@Transactional
	public List<MantencionProgramada> buscarMantencionProgramadaPorCodigoDescripcion(String codigo, String descripcion) {
		
		try {

			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(!descripcion.equals("")){
				filtros.add(new Filtro("descripcion", TipoOperacionFiltroEnum.LIKE_COMPLETO, descripcion));
			}
			
			if(!codigo.equals("")){
				filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			}
			
			filtros.add(new Filtro("estado", TipoOperacionFiltroEnum.EQUAL, true));
			
			List<MantencionProgramada> mantenciones = mantencionProgramadaDao.buscarPorFiltros(filtros, null);
			
			for(MantencionProgramada mp : mantenciones){
				mp.getMantencionTrabajos().size();
			}
			
			return mantenciones;
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
