package pe.com.bbva.monitoreo.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pe.com.bbva.core.bo.GenericBOImpl;
import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.monitoreo.bo.ServicioBO;
import pe.com.bbva.monitoreo.dao.ServicioDAO;
import pe.com.bbva.monitoreo.domain.Servicio;

@Service("servicioBO")
public class ServicioBOImpl extends GenericBOImpl<Servicio> implements ServicioBO{
private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ServicioDAO servicioDAO;
	
	public List<Servicio> findServicios(Servicio servicio) throws Exception {
		
		List<Servicio> listaServicios =servicioDAO.findServicios(servicio);
		return listaServicios;
	}

	public List<Servicio> findPadres() throws BOException, DAOException {
		List<Servicio> listaPadres = super.executeListNamedQuery("listaPadres", new ArrayList<String>());
		return listaPadres;
	}

	public Servicio findById(Long id) throws BOException, DAOException {
		Servicio servicio = super.findById(Servicio.class, id);
		return servicio;
	}

	@Override
	public Grid<Servicio> findToGrid(Servicio servicio,String order, int page, int rows)
	throws Exception {
		String where="where ";	
		
		List<Servicio> listaServicio= findServicios(servicio);
		
		return super.findToGridList(listaServicio,where, " order by "+order,page,rows);	
	}
}
