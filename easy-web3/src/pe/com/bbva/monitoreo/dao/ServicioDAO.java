package pe.com.bbva.monitoreo.dao;

import java.util.List;

import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.domain.Tabla;
import pe.com.bbva.monitoreo.domain.Servicio;

public interface ServicioDAO {
	public List<Servicio> findServicios(Servicio servicio) throws Exception,DAOException;
//	public List<Servicio> findServiciosByAmbiente(Tabla tipoAmbiente)throws BOException, DAOException;
}
