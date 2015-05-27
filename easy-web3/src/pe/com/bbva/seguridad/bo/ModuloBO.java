package pe.com.bbva.seguridad.bo;

import java.util.List;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;

public interface ModuloBO {

	public List<Modulo> findModulos(Modulo modulo) throws Exception;
	public boolean validate(Modulo modulo) throws BOException, DAOException;
	public void save(Modulo modulo,String codigoAntiguo) throws BOException, DAOException;
	public List<Modulo> findPadres() throws BOException, DAOException;
	public Modulo findById(Long id) throws BOException, DAOException;
	public void delete(Modulo modulo)throws BOException;
	public void deleteLog(Class modulo, Long id)throws BOException;
	public Grid<Modulo> findToGrid(Modulo modulo,String order, int page, int rows)
	throws Exception;
}
