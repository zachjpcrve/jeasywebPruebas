package pe.com.bbva.seguridad.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.dao.UsuarioDAO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;
import pe.com.bbva.seguridad.domain.Usuario;

@Service("usuarioDAO")
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario> implements
		UsuarioDAO {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public UsuarioDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Usuario> findUsuarios(Usuario usuario) throws BOException,
			DAOException {
		String where = "";

		if (usuario.getCodigo() == null) {
			usuario.setCodigo("");
		}
		where = where + " upper(codigo) like upper('%" + usuario.getCodigo()
				+ "%')";
		if (usuario.getEstado() != null && !usuario.getEstado().equals("")) {
			where = where + " and upper(estado) like upper('%"
					+ usuario.getEstado() + "%') ";
		}
		String orders = " order by fechaCreacion desc,codigo";
		if (where.length() > 0) {
			where = " where " + where;
		}
		List<Usuario> listaUsuarios = super.executeQuery(Usuario.class, where,
				orders);
		return listaUsuarios;
	}

}
