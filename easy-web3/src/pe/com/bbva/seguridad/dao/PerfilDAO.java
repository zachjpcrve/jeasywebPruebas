package pe.com.bbva.seguridad.dao;

import java.util.List;

import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.domain.Perfil;

public interface PerfilDAO {

	public List<Perfil> findPerfiles(Perfil perfil) throws BOException, DAOException;
		
}
