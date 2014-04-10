package cl.jfoix.atm.ot.service;

import cl.jfoix.atm.ot.entity.Vehiculo;
import cl.jfoix.atm.ot.entity.VehiculoOrden;

public interface IVehiculoService {

	Vehiculo buscarVehiculoPorPatente(String patente) throws Exception;

	void guardarVehiculo(Vehiculo vehiculo) throws Exception;

	void guardarVehiculoOrden(VehiculoOrden vehiculoOrden) throws Exception;
}
