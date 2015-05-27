package pe.com.bbva.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtil {

	public boolean findStringInArray(String cadenaABuscar, String[] cadenas) {
		boolean respuesta = false;
		if (cadenas != null) {
			for (int i = 0; i < cadenas.length; i++) {
				if (cadenas[i] != null && !cadenas[i].trim().equals("")
						&& cadenas[i].trim().equals(cadenaABuscar)) {
					respuesta = true;
					break;
				}
			}
		}
		return respuesta;
	}
	
	/**
	 * Devuelve la traza de error de una excepción
	 * @param e Exception
	 * @return cadena de texto con la traza del error
	 */
	public static String getStackTrace(Exception e){
		StringWriter sr = new StringWriter(0);
        PrintWriter pw = new PrintWriter(sr,true);
        e.printStackTrace(pw);
        return sr.toString();
	}
}
