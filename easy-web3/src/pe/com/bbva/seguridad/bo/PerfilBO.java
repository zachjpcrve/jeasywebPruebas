package pe.com.bbva.seguridad.bo;

import java.util.List;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;

public interface PerfilBO {

	public void save(Perfil perfil, String codigoAntiguo) throws BOException, DAOException;	
	public List<Perfil> findPerfiles(Perfil perfil) throws BOException, DAOException;
	public Perfil findById(Long id) throws BOException, DAOException;
	public List<Modulo> findModulosDisponibles(Perfil perfil) throws DAOException, Exception;
	public List<Modulo> findModulosSeleccionados(Perfil perfil)	throws BOException, DAOException;
	public void delete(Perfil perfil)throws BOException;
	public void deleteLog(Class perfil, Long id)throws BOException;
	public Grid<Perfil> findToGrid(Perfil perfil,String order, int page, int rows)
	throws BOException, DAOException;
	
}
