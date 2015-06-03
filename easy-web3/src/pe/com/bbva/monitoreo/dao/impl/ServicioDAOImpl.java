package pe.com.bbva.monitoreo.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.dao.GenericDAOImpl;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.monitoreo.dao.ServicioDAO;
import pe.com.bbva.monitoreo.domain.Servicio;

@Service("servicioDAO")
public class ServicioDAOImpl extends GenericDAOImpl<Servicio> implements ServicioDAO{
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ServicioDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Servicio> findServicios(Servicio servicio) throws Exception,
			DAOException {
		String where = "";

		if (servicio.getNombre() == null) {
			servicio.setNombre("");
		}
		where = where + " upper(nombre) like upper('%"
				+ servicio.getNombre() + "%')";
		if (servicio.getUrl() != null && !servicio.getUrl().trim().equals("")) {
			where = where + " and upper(url) like upper('%"
					+ servicio.getUrl() + "%')";
		}
		if (servicio.getTipoAmbiente() != null
				&& servicio.getTipoAmbiente().getId() != null) {
			where = where + " and tipoAmbiente.id ="
					+ servicio.getTipoAmbiente().getId();
		}
		if(servicio.getTipoAplicativo()!=null
				&& servicio.getTipoAplicativo().getId() !=null){
			where = where + " and tipoAplicativo.id ="
					+servicio.getTipoAplicativo().getId();
		}
		if(servicio.getEstado()!= null &&
				   !servicio.getEstado().equals("")){
					where = where +" and upper(estado) like upper('%" +
					servicio.getEstado()+
							"%') ";
		}
		String orders = " order by fechaCreacion desc, url, tipoAmbiente.id, tipoAplicativo.id,nombre";
		if (where.length() > 0) {
			where = " where " + where;
		}
		List<Servicio> listaServicios = super.executeQuery(Servicio.class, where, orders);
		return listaServicios;
	}

}
