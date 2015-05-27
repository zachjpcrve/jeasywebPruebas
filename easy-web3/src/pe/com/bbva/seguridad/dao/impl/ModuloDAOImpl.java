package pe.com.bbva.seguridad.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.seguridad.dao.ModuloDAO;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.seguridad.domain.Usuario;

@Service("moduloDAO")
public class ModuloDAOImpl extends GenericDAOImpl<Modulo> implements ModuloDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	private static String LISTAR_MODULOS_POR_USUARIO = "SELECT A.* FROM (SELECT MO.* FROM MONAPP.TMONAPP_USUARIO US "
			+ "LEFT JOIN MONAPP.TMONAPP_USUARIO_PERFIL UP "
			+ "ON US.ID_USUARIO = UP.ID_USUARIO "
			+ "LEFT JOIN MONAPP.TMONAPP_PERFIL_MODULO PM "
			+ "ON PM.ID_PERFIL = UP.ID_PERFIL "
			+ "LEFT JOIN MONAPP.TMONAPP_MODULO MO "
			+ "ON MO.ID_MODULO = PM.ID_MODULO "
			+ "WHERE US.CODIGO_USUARIO = '%s' AND"
			+ " PM.ID_MODULO IS NOT NULL ) A "
			+ " START WITH A.SUPERIOR_ID IS NULL "
			+ " CONNECT BY PRIOR A.ID_MODULO = A.SUPERIOR_ID";

	@Autowired
	public ModuloDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Modulo> findModulos(Modulo modulo) throws Exception,
			DAOException {
		String where = "";

		if (modulo.getDescripcion() == null) {
			modulo.setDescripcion("");
		}
		where = where + " upper(descripcion) like upper('%"
				+ modulo.getDescripcion() + "%')";
		if (modulo.getCodigo() != null && !modulo.getCodigo().trim().equals("")) {
			where = where + " and upper(codigo) like upper('%"
					+ modulo.getCodigo() + "%')";
		}
		if (modulo.getTipoModulo() != null
				&& modulo.getTipoModulo().getId() != null) {
			where = where + " and tipoModulo.id ="
					+ modulo.getTipoModulo().getId();
		}
		if(modulo.getEstado()!= null &&
				   !modulo.getEstado().equals("")){
					where = where +" and upper(estado) like upper('%" +
					modulo.getEstado()+
							"%') ";
		}
		String orders = " order by fechaCreacion desc,codigo, tipoModulo.id, descripcion";
		if (where.length() > 0) {
			where = " where " + where;
		}
		List<Modulo> listaModulos = super.executeQuery(Modulo.class, where, orders);
		return listaModulos;
	}

	public List<Modulo> findModulosByUsuario(Usuario usuario)
			throws BOException, DAOException {
		String query = String.format(LISTAR_MODULOS_POR_USUARIO, usuario
				.getCodigo());
		System.out.println("CONSULTANDO MODULOS");
		List<Modulo> listaModulo = super.executeSQL(query, Modulo.class);
		System.out.println("CANTIDAD DE MODULOS="+listaModulo.size());
		return listaModulo;
	}

}
