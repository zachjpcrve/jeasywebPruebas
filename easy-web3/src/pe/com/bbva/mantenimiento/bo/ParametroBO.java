package pe.com.bbva.mantenimiento.bo;

import java.util.List;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.domain.Parametro;

public interface ParametroBO {

	public List<Parametro> findParametros(Parametro parametro)throws BOException, DAOException ;
	public Parametro findById(Long id) throws BOException, DAOException;
	public Parametro findByNombreParametro(String codigo) throws BOException;
	public void save(Parametro parametro ,String codigoAntiguo) throws BOException, DAOException;
	public void delete(Parametro parametro)throws BOException;
	public void deleteLog(Class parametro, Long id)throws BOException;
	public Grid<Parametro> findToGrid(Parametro parametro,String order, int page, int rows)
	throws BOException, DAOException;
	public List<Parametro> findParamListByDescrip(String descripcion) throws BOException;
	
}
