package cl.jfoix.atm.ot.service;

import java.util.Date;
import java.util.List;

import cl.jfoix.atm.comun.entity.Producto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitud;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProducto;
import cl.jfoix.atm.ot.entity.OrdenTrabajoSolicitudProductoGrupo;

public interface ISolicitudRepuestosService {

	void cambiarEstadoSolicitudRepuesto(OrdenTrabajoSolicitud odenTrabajoSolicitud);
	
	List<OrdenTrabajoSolicitud> buscarSolicitudesRepuestos(Date fechaSolicitud, Integer idProducto, Boolean estado, String nombreUsuario);
	
	void cambiarEstadoSolicitudRepuestoProducto(OrdenTrabajoSolicitudProducto solProducto);

	void guardarSolicitudRepuesto(OrdenTrabajoSolicitud odenTrabajoSolicitud);

	void guardarProductosSolicitud(OrdenTrabajoSolicitudProductoGrupo otspg,
			List<Producto> productos);
}
