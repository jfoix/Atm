package cl.jfoix.atm.ot.service;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.entity.EstadoTrabajo;
import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoUsuario;

public interface IOrdenTrabajoService {

	void gaurdarOrdenTrabajo(OrdenTrabajo ordenTrabajo) throws ViewException;
	
	List<OrdenTrabajoUsuario> buscarTrabajosUsuario(String nombreUsuario, Integer idOT, Date fecha, Integer idEstadoTrabajo);

	List<EstadoTrabajo> obtenerEstadosTrabajo();

	OrdenTrabajo cambiarEstadoOrdenTrabajo(EstadoTrabajo estadoTrabajo, Integer idOrdenTrabajo) throws ViewException;

	List<Producto> buscarProductos(String codProducto, String descProducto);

	void guardarOrdenTrabajoSolicitud(OrdenTrabajoSolicitud solicitud) throws ViewException;
}
