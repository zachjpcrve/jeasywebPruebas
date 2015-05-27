package pe.com.bbva.mantenimiento.bo;


import java.util.List;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.domain.Tabla;

public interface TablaBO {

	public List<Tabla> findDetalleTabla(Long idTabla) throws BOException, DAOException;
	public List<Tabla> findAllTabla() throws BOException, DAOException;
	public Tabla findTablaById(Long id) throws BOException, DAOException;
	public Tabla findTablaByCodigo(String codigo) throws BOException;
	public List<Tabla> findTablas(Tabla tabla) throws BOException, DAOException;
	public Tabla findDescripcionTabla(Long id, Long idPadre) throws BOException, DAOException;
	public List<Tabla> findTablasPadre()throws BOException, DAOException;
	public List<Tabla> findTablasPadre(Tabla tabla)throws BOException, DAOException;
    public void save(Tabla tabla) throws BOException, DAOException;
	public void deleteLog(Class tabla, Long id) throws BOException;
	public List<Tabla> findTablasPadreRecur()throws BOException;
	public Grid<Tabla> findToGrid(Tabla tabla,String order,int page,int rows) 
	throws BOException,DAOException;
}
