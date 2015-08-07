package pe.com.bbva.monitoreo.servicios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

public class consultarEstadoURL {
	public String consultandoEstadoURL(String url_servicio,String user,String password){
		String patronExito = "Hi there, this is a Web service!";
		String resultadoExito = "0";
		StringBuilder stbResultadoRespuesta = new StringBuilder();
		try {
			URL url = new URL(url_servicio);
		      URLConnection con = url.openConnection();
		 
		      Authenticator au = new Authenticator() {
		         @Override
		         protected PasswordAuthentication
		            getPasswordAuthentication() {
		            return new PasswordAuthentication
		               (user, password.toCharArray());
		         }
		      };
		      Authenticator.setDefault(au);
		 
		      BufferedReader in = new BufferedReader(
		         new InputStreamReader(con.getInputStream()));
		 
		      String linea;
		      while ((linea = in.readLine()) != null) {
		         System.out.println(linea);
		      }
			
		      resultadoExito = stbResultadoRespuesta.toString().contains(patronExito)?"1":resultadoExito;
		} catch (MalformedURLException e) {
			// TODO: handle exception
			resultadoExito="0";
			stbResultadoRespuesta.append(e.getMessage());
		} catch(ConnectException e){
			resultadoExito = "0";
			stbResultadoRespuesta.append(e.getMessage());
		}catch(FileNotFoundException e){
			resultadoExito = "0";
			stbResultadoRespuesta.append(e.getMessage());
		}catch(IOException e){
			resultadoExito="1";
			stbResultadoRespuesta.append(e.getMessage());
		}
		String resultadoCompleto=resultadoExito+stbResultadoRespuesta.toString();
		return resultadoCompleto;
	}
}
