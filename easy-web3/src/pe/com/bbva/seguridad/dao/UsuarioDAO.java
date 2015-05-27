package pe.com.bbva.seguridad.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.domain.Usuario;

public interface UsuarioDAO {

	public List<Usuario> findUsuarios(Usuario usuario) throws BOException, DAOException;
}
