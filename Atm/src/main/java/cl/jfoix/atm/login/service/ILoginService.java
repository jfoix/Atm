package cl.jfoix.atm.login.service;

import cl.jfoix.atm.login.entity.Usuario;

public interface ILoginService {

	public Usuario login(String userName, String password);
}
