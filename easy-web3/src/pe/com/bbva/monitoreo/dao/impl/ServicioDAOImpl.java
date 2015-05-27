package pe.com.bbva.monitoreo.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.mantenimiento.domain.Tabla;
import pe.com.bbva.monitoreo.dao.ServicioDAO;
import pe.com.bbva.monitoreo.domain.Servicio;

@Service("servicioDAO")
public class ServicioDAOImpl extends GenericDAOImpl<Servicio> implements ServicioDAO{
	private Logger logger = Logger.getLogger(this.getClass());

	private static String LISTAR_SERVICIOS_POR_AMBIENTE = "SELECT A.* FROM (SELECT MO.* FROM MONAPP.TMONAPP_USUARIO US "
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
	public ServicioDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Servicio> findServicios(Servicio servicio) throws Exception,
			DAOException {
//		String where = "";
//
//		if (servicio.getDescripcion() == null) {
//			servicio.setDescripcion("");
//		}
//		where = where + " upper(descripcion) like upper('%"
//				+ servicio.getDescripcion() + "%')";
//		if (servicio.getCodigo() != null && !servicio.getCodigo().trim().equals("")) {
//			where = where + " and upper(codigo) like upper('%"
//					+ servicio.getCodigo() + "%')";
//		}
//		if (servicio.getTipoModulo() != null
//				&& servicio.getTipoModulo().getId() != null) {
//			where = where + " and tipoModulo.id ="
//					+ servicio.getTipoModulo().getId();
//		}
//		if(servicio.getEstado()!= null &&
//				   !servicio.getEstado().equals("")){
//					where = where +" and upper(estado) like upper('%" +
//					servicio.getEstado()+
//							"%') ";
//		}
//		String orders = " order by fechaCreacion desc,codigo, tipoModulo.id, descripcion";
//		if (where.length() > 0) {
//			where = " where " + where;
//		}
//		List<Servicio> listaServicios = super.executeQuery(Servicio.class, where, orders);
//		return listaServicios;
		return null;
	}

	public List<Servicio> findServiciosByAmbiente(Tabla tipoTabla)
			throws BOException, DAOException {
		String query = String.format(LISTAR_SERVICIOS_POR_AMBIENTE, tipoTabla
				.getCodigo());
		System.out.println("CONSULTANDO SERVICIOS");
		List<Servicio> listaServicio = super.executeSQL(query, Servicio.class);
		System.out.println("CANTIDAD DE SERVICIOS="+listaServicio.size());
		return listaServicio;
	}
}
