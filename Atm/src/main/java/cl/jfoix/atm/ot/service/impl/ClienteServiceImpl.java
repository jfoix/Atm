package cl.jfoix.atm.ot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.jfoix.atm.comun.excepcion.dao.DaoException;
import cl.jfoix.atm.dbutil.dao.util.Filtro;
import cl.jfoix.atm.dbutil.dao.util.TipoOperacionFiltroEnum;
import cl.jfoix.atm.ot.dao.IClienteDao;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.service.IClienteService;

@Service("clienteService")
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;

	@Override
	public Cliente buscarClientePorRut(String rut) {
		
		List<Cliente> clientes = null;
		
		try {
			List<Filtro> filtros = new ArrayList<Filtro>();
			filtros.add(new Filtro("rutCliente", TipoOperacionFiltroEnum.EQUAL, rut));
			
			clientes = clienteDao.buscarPorFiltros(filtros, null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return clientes == null || clientes.size() == 0 ? null : clientes.get(0);
	}

	@Transactional
	@Override
	public void guardarCliente(Cliente cliente) throws Exception {
		if(cliente.getIdCliente() == null){
			cliente.setEstado(true);
			clienteDao.guardar(cliente);
		} else {
			clienteDao.modificar(cliente);
		}
	}
}
