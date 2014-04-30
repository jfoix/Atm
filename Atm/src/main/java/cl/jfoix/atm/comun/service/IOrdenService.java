package cl.jfoix.atm.comun.service;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.dto.ResumentMecanicoDto;
import cl.jfoix.atm.ot.dto.ResumentOTDto;
import cl.jfoix.atm.ot.entity.Cliente;
import cl.jfoix.atm.ot.entity.EstadoOrden;
import cl.jfoix.atm.ot.entity.FormaPago;
import cl.jfoix.atm.ot.entity.Orden;
import cl.jfoix.atm.ot.entity.OrdenDocumento;
import cl.jfoix.atm.ot.entity.OrdenEstado;
import cl.jfoix.atm.ot.entity.OrdenObservacion;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;
import cl.jfoix.atm.ot.entity.Stock;
import cl.jfoix.atm.ot.entity.VehiculoOrden;

public interface IOrdenService {

	void guardarOrden(Orden orden) throws ViewException;
	
	Orden buscarOrdenPorId(Integer idOrden);

	List<Orden> buscarOrdenAdmin(Integer idOrden, String idMecanico, Date fechaInicio, Date fecheTermino, Integer idEstado, String patente);

	void guardarClienteVehiculoOrden(Cliente cliente, Integer idVehiculoOrden) throws ViewException;

	void guardarEstadoOrden(OrdenEstado ordenEstado, FormaPago formaPago) throws ViewException;

	Stock buscarStockPorProducto(Integer idProducto, Double cantidad, Integer idOT);

	List<FormaPago> buscarFormasPago();

	Integer buscarValorVentaProductoStock(Integer idStock);

	List<OrdenObservacion> buscarObservacionesPorIdOrden(Integer idOrden);

	void guardarObservacion(OrdenObservacion observacion);

	void eliminarObservacion(OrdenObservacion observacion);

	List<OrdenDocumento> buscarDocumentosPorIdOrden(Integer idOrden);

	void guardarDocumento(OrdenDocumento documento);

	void eliminarDocumento(OrdenDocumento documento);

	byte[] generarResumenOT(Orden orden, boolean finalizado);

	ResumentOTDto buscarResumenOT(Orden ot, boolean finalizado);

	List<Orden> consultaOTVehiculo(Integer idOrden, String patente);

	EstadoOrden buscarEstadoOrdenPorId(Integer idEstadoOrden);

	List<VehiculoOrden> consultaClientes(String rutCliente, String patente);

	ResumentMecanicoDto consultaTrabajosMecanico(String nomUsuarioMecanico, Date fechaDesde, Date fechaHasta);
}
