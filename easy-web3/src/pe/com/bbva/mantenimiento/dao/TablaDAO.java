package pe.com.bbva.mantenimiento.dao;

import java.util.List;

import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.domain.Tabla;

public interface TablaDAO {
	public List<Tabla> findDetalleTabla(Long idTabla) throws BOException, DAOException;
	public List<Tabla> findTablas(Tabla tabla) throws BOException, DAOException;
	public Tabla findDescripcionTabla(Long id, Long idPadre) throws BOException, DAOException;
	public List<Tabla> findTablasPadre(Tabla tabla)throws BOException, DAOException;
}
