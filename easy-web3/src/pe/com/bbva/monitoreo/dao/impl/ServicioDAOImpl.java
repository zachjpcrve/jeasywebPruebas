package pe.com.bbva.monitoreo.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
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
		if(servicio.getEstado_serv()!= null &&
				   !servicio.getEstado_serv().equals("")){
					where = where +" and upper(estado_serv) like upper('%" +
					servicio.getEstado_serv()+
							"%') ";
		}
		String orders = " order by fechaCreacion desc, url, tipoAmbiente.id, tipoAplicativo.id,nombre";
		if (where.length() > 0) {
			where = " where " + where;
		}
		List<Servicio> listaServicios = super.executeQuery(Servicio.class, where, orders);
		return listaServicios;
	}

	@Override
	public String testByUrl(Servicio servicio, String urlAntiguo) {
		// TODO Auto-generated method stub
		String patronExito = "Hi there, this is a Web service!";
		String resultadoExito = "0";
		StringBuilder stbResultadoRespuesta = new StringBuilder();
		try{
			URL urlMonitor = new URL(urlAntiguo);
			URLConnection yc = urlMonitor.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			
		while ((inputLine = in.readLine()) != null){
			stbResultadoRespuesta.append(inputLine);
		}
		in.close();
		resultadoExito = stbResultadoRespuesta.toString().contains(patronExito)?"1":resultadoExito;
		}catch(ConnectException e){
			resultadoExito = "0";
			stbResultadoRespuesta.append(e.getMessage());
		}catch(FileNotFoundException e){
			resultadoExito = "0";
			stbResultadoRespuesta.append(e.getMessage());
		}catch(IOException e){
			resultadoExito="1";
			stbResultadoRespuesta.append(e.getMessage());
		}
		return resultadoExito;
	}

	@Override
	public String MensajetestByUrl(Servicio servicio, String urlAntiguo) {
		// TODO Auto-generated method stub
		String patronExito = "Hi there, this is a Web service!";
		String resultadoExito = "0";
		StringBuilder stbResultadoRespuesta = new StringBuilder();
		try{
			URL urlMonitor = new URL(urlAntiguo);
			URLConnection yc = urlMonitor.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			
		while ((inputLine = in.readLine()) != null){
			stbResultadoRespuesta.append(inputLine);
		}
		in.close();
		resultadoExito = stbResultadoRespuesta.toString().contains(patronExito)?"1":resultadoExito;
		}catch(ConnectException e){
			resultadoExito = "0";
			stbResultadoRespuesta.append(e.getMessage());
		}catch(FileNotFoundException e){
			resultadoExito = "0";
			stbResultadoRespuesta.append(e.getMessage());
		}catch(IOException e){
			resultadoExito="1";
			stbResultadoRespuesta.append(e.getMessage());
		}
		return stbResultadoRespuesta.toString();
	}
}
