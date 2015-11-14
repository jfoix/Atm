package cl.jfoix.atm.login.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.jfoix.atm.comun.dao.IParametroGeneralDao;
import cl.jfoix.atm.comun.entity.ParametroGeneral;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.login.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private IParametroGeneralDao parametroGeneralDao;
	
	@Override
	public Usuario login(String userName, String password) {
		
		Usuario user = new Usuario();
		user.setNombreUsuario(userName);
		user.setClave(password);
		user.setFechaIngreso(new Date());
		
		return user;
	}
	
	@Override
	public boolean ipLeapMotion(String ip){
		
		try{
			
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("codigo", TipoOperacionFiltroEnum.EQUAL, "ip.leap.motion"));
			
			List<ParametroGeneral> parametros = parametroGeneralDao.buscarPorFiltros(filtros, null);
			
			if(parametros != null && parametros.size() > 0){
				String[] ipsLeapMotion = parametros.get(0).getValor().split(",");
				for(String ipLm : ipsLeapMotion){
					if(ipLm.equals(ip)){
						return true;
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
}
