package pe.com.bbva.seguridad.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.dao.PerfilDAO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Perfil;

@Service("perfilDAO")
public class PerfilDAOImpl extends GenericDAOImpl<Perfil> implements PerfilDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public PerfilDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Perfil> findPerfiles(Perfil perfil) throws BOException,
			DAOException {
		String where = " where descripcion like '%"
				+ (perfil.getDescripcion() == null ? "" : perfil
						.getDescripcion()) + "%' ";
		if (perfil.getCodigo() != null && !perfil.getCodigo().trim().equals("")) {
			where = where + " and codigo like '%" + perfil.getCodigo() + "%'";
		}
		if (perfil.getEstado() != null && !perfil.getEstado().equals("")) {
			where = where + " and upper(estado) like upper('%"
					+ perfil.getEstado() + "%') ";
		}
		String orders = " order by fechaCreacion,codigo , descripcion";
		List<Perfil> listaPerfiles = super.executeQuery(Perfil.class, where,
				orders);
		return listaPerfiles;
	}
}
