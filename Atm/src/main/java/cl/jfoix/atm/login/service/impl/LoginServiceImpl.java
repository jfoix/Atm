package cl.jfoix.atm.login.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.login.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Override
	public Usuario login(String userName, String password) {
		
		Usuario user = new Usuario();
		user.setNombreUsuario(userName);
		user.setClave(password);
		user.setFechaIngreso(new Date());
		
		return user;
	}
}
