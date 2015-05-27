package pe.com.bbva.seguridad.bo;

import java.util.List;

import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;

public interface UsuarioBO {
	
	public boolean validate(Usuario usuario) throws BOException, DAOException;
	public void save(Usuario usuario,String codigoAntiguo) throws BOException, DAOException;
	public List<Usuario> findUsuarios(Usuario usuario) throws BOException, DAOException;
	public List<Perfil> findPerfilesDisponibles(Usuario usuario)throws BOException, DAOException;
	public List<Perfil> findPerfilesSeleccionados(Usuario usuario)throws BOException, DAOException;
	public Usuario findById(Long id) throws BOException, DAOException;
	public List<Modulo> findModulosByUsuario(Usuario usuario)throws BOException, DAOException;
	public Usuario findByCodigo(String codigo) throws BOException, DAOException;
	public void delete(Usuario usuario)throws BOException;
	public void deleteLog(Class usuario, Long id)throws BOException;
	public Grid<Usuario> findToGrid(Usuario usuario,String order, int page, int rows)
	throws BOException, DAOException;
}
