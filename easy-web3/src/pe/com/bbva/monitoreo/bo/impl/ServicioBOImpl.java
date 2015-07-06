package pe.com.bbva.monitoreo.bo.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
		return true;
	}
	
	public String testByUrl(Servicio servicio, String urlAntiguo)throws BOException, DAOException{
		String estadotestByUrl=servicioDAO.testByUrl(servicio,urlAntiguo);
		return estadotestByUrl;
	}
	
	public List<String> Listaids(List<String> listaIds){
		return listaIds;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Servicio servicio , 
					 String urlAntiguo) throws BOException, DAOException {
		this.urlAntiguo = urlAntiguo;
		super.save(servicio);
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

	@Override
	public String mensajetestByUrl(Servicio servicio,String urlAntiguo) {
		// TODO Auto-generated method stub
		String mensajetestByUrl=servicioDAO.MensajetestByUrl(servicio,urlAntiguo);
		return mensajetestByUrl;
	}

	@Override
	public void mensaje(String mensajetestByUrl) {
		// TODO Auto-generated method stub
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ya funciono"));
	}

//	@Override
//	public List<Servicio> refreshServicios(Servicio servicio, String urlAntiguo)
//			throws Exception {
//		// TODO Auto-generated method stub
//		List<Servicio> listaServiciosEstado=servicioDAO.refreshServicios(servicio,urlAntiguo);
//		return listaServiciosEstado;
//	}
}
