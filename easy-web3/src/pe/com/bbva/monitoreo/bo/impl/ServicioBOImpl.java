package pe.com.bbva.monitoreo.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.bbva.core.bo.GenericBOImpl;
import pe.com.bbva.core.bo.Grid;
import pe.com.bbva.core.exceptions.BOException;
import pe.com.bbva.core.exceptions.DAOException;
import pe.com.bbva.monitoreo.bo.ServicioBO;
import pe.com.bbva.monitoreo.dao.ServicioDAO;
import pe.com.bbva.monitoreo.domain.Servicio;
import pe.com.bbva.seguridad.domain.Modulo;
import pe.com.bbva.util.Constantes;

@Service("servicioBO")
public class ServicioBOImpl extends GenericBOImpl<Servicio> implements ServicioBO{
private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ServicioDAO servicioDAO;
	
	private String urlAntiguo;
	
	public List<Servicio> findServicios(Servicio servicio) throws Exception {
		
		List<Servicio> listaServicios =servicioDAO.findServicios(servicio);
		return listaServicios;
	}
	
	public boolean validate(Servicio servicio) throws BOException{
		if(!servicio.getUrl().equals(urlAntiguo)){
			HashMap<String, String> parametros = new HashMap<String,String>();
			parametros.put("url", servicio.getUrl());
			List<Servicio> listaTemp = super.findByParams(Servicio.class, parametros);
			if(!listaTemp.isEmpty()){
				throw new BOException("El url ya esta registrado");
			}
		}
		if(servicio.getTipoAmbiente().getId().equals(Constantes.VAL_TIPO_TEST) ||
		   servicio.getTipoAmbiente().getId().equals(Constantes.VAL_TIPO_CALIDAD) || 
		   servicio.getTipoAmbiente().getId().equals(Constantes.VAL_TIPO_PRODUCCION)){
			if(servicio.getSuperior() == null){
				throw new BOException("Seleccione un padre");
			}
		}
		return true;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Servicio servicio , 
					 String urlAntiguo) throws BOException, DAOException {
		this.urlAntiguo = urlAntiguo;
		super.save(servicio);
	}
	
	public List<Servicio> findPadres() throws BOException, DAOException {
		List<Servicio> listaPadresServ = super.executeListNamedQuery("listaPadresServ", new ArrayList<String>());
		return listaPadresServ;
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
