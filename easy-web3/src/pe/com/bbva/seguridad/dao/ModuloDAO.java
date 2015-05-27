package pe.com.bbva.seguridad.dao;

import java.util.List;

import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Usuario;

public interface ModuloDAO {
	
	public List<Modulo> findModulos(Modulo modulo) throws Exception,DAOException;
	public List<Modulo> findModulosByUsuario(Usuario usuario)throws BOException, DAOException;
}
