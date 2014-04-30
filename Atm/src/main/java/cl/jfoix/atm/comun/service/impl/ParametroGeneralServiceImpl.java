package cl.jfoix.atm.comun.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.dao.IParametroGeneralDao;
import cl.jfoix.atm.comun.entity.ParametroGeneral;
import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IParametroGeneralService;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;

@Service("parametroGeneralService")
public class ParametroGeneralServiceImpl implements IParametroGeneralService {

	private static Logger log = Logger.getLogger(ParametroGeneralServiceImpl.class);

	@Autowired
	private IParametroGeneralDao parametroGeneralDao;
	
	@Transactional
	@Override
	public void guardarParametroGeneral(ParametroGeneral parametroGeneral) throws Exception {
		try{
			parametroGeneralDao.modificar(parametroGeneral);
		}catch(DaoException e){
			log.error("Erro al guardar el Parametro General", e);
			throw new ViewException("Ocurrió un problema al guardar la información, intentelo más tarde");
		}
	}

	@Override
	public List<ParametroGeneral> buscarPorCodigoGrupo(Integer tipoBusquda, String codigo) {
		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			
			if(tipoBusquda.equals(0)){
				filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			} else {
				filtros.add(new Filtro("grupo", TipoOperacionFiltroEnum.LIKE_COMPLETO, codigo));
			}
			
			return parametroGeneralDao.buscarPorFiltros(filtros, null);
			
		}catch(DaoException e){
			log.error("Erro al guardar el Parametro General", e);
		}
		
		return null;
	}
	
	@Override
	public List<ParametroGeneral> buscarPorGrupo(String codGrupo) {
		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("grupo", TipoOperacionFiltroEnum.EQUAL, codGrupo));
			
			return parametroGeneralDao.buscarPorFiltros(filtros, null);
		}catch(DaoException e){
			log.error("Erro al guardar el Parametro General", e);
		}
		
		return null;
	}

	@Override
	public String buscarString(String codigo) {

		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, codigo));
			
			List<ParametroGeneral> pGenerales = parametroGeneralDao.buscarPorFiltros(filtros, null);
			
			if(pGenerales != null){
				return pGenerales.get(0).getValor();
			}
		}catch(DaoException e){
			log.error("Erro al guardar el Parametro General", e);
		}
		
		return null;
	}

	@Override
	public Integer buscarInteger(String codigo) {

		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, codigo));
			
			List<ParametroGeneral> pGenerales = parametroGeneralDao.buscarPorFiltros(filtros, null);
			
			if(pGenerales != null){
				return Integer.parseInt(pGenerales.get(0).getValor());
			}
		}catch(Exception e){
			log.error("Erro al guardar el Parametro General", e);
		}
		return null;
	}

	@Override
	public Date buscarFecha(String codigo) {

		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, codigo));
			
			List<ParametroGeneral> pGenerales = parametroGeneralDao.buscarPorFiltros(filtros, null);
			
			if(pGenerales != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.parse(pGenerales.get(0).getValor());
			}
		}catch(Exception e){
			log.error("Erro al guardar el Parametro General", e);
		}
		return null;
	}
}
