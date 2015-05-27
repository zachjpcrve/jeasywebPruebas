package pe.com.bbva.mantenimiento.dao;

import java.util.List;

import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.domain.Parametro;



public interface ParametroDAO {
	
	public List<Parametro> findParametros(Parametro parametro)throws BOException, DAOException;
	
}
