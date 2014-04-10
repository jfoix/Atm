package cl.jfoix.atm.comun.service;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.entity.FormaPago;
import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenEstado;
import cl.jfoix.atm.ot.entity.Stock;

public interface IOrdenService {

	void guardarOrden(Orden orden) throws ViewException;
	
	Orden buscarOrdenPorId(Integer idOrden);

	List<Orden> buscarOrdenAdmin(Integer idOrden, String idMecanico, Date fechaInicio, Date fecheTermino, Integer idEstado, String patente);

	void guardarClienteVehiculoOrden(Cliente cliente, Integer idVehiculoOrden) throws ViewException;

	void guardarEstadoOrden(OrdenEstado ordenEstado, FormaPago formaPago) throws ViewException;

	Stock buscarStockPorProducto(Integer idProducto, Double cantidad, Integer idOT);

	List<FormaPago> buscarFormasPago();

	Integer buscarValorVentaProductoStock(Integer idStock);
}
