package pe.com.bbva.core.util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FechaUtil {

	//private  Date date = new Date();
	public static  final String  YYYYMMDD_HHMMSS = "yyyyMMdd-hhmmss";
	public static  final String  YYYYMMDDHHMMSS = "yyyyMMddhhmmss";
	public static  final String  DDMMYYYY = "dd/MM/yyyy";
	public static  final String  YYYY = "yyyy";
	public static  final String  HHMMSS="hhmmss";
	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	public static String getFechaActualString(){
		DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
		return dateFormat.format(new Date());
	}
	public static Date getFechaActualDate(){
		return new Date();
	}
	
	public static String formatFecha(Date fecha,
									 String format){
		Locale currentLocale = new Locale("pe","ES");
		DateFormat dateFormat = new SimpleDateFormat(format,
	    				 				 			 currentLocale);
		return dateFormat.format(fecha);
	}
	
   public static String formatFechaActual(String format){
	Locale currentLocale = new Locale("pe","ES");
	DateFormat dateFormat = new SimpleDateFormat(format,
					 			 currentLocale);
	return dateFormat.format(new Date());
	}
	
	/**
	 * compara dos fechas 
	 * si la primera fecha es menor retorna 1
	 * si las dos fechas son iguales retorna 0
	 * si la segunda fecha es menor retorna -1
	 * @param firstDate
	 * @param endDate
	 * @return
	 */	
	public static int compareDate(Date firstDate, 
								  Date endDate){
		//quitar la hora minutos y segundos
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(firstDate);
		Calendar calendarFirst = new GregorianCalendar(calendar.get(Calendar.YEAR),
													   calendar.get(Calendar.MONTH),
													   calendar.get(Calendar.DATE));
		calendar.setTime(endDate);
		Calendar calendarEnd = new GregorianCalendar(calendar.get(Calendar.YEAR),
													 calendar.get(Calendar.MONTH),
													 calendar.get(Calendar.DATE));
		
		if(calendarFirst.getTime().getTime() < calendarEnd.getTime().getTime()){
			return 1;
		}else if(calendarFirst.getTime().getTime() == calendarEnd.getTime().getTime()){
			return 0;
		}else{
			return -1;
		}
	}
	
	public static Date convertToDate(String fechaParam,String format){
		Date fecha = null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			fecha = dateFormat.parse(fechaParam);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return fecha;
	}
	public static String getHoraActualString(){
		String hora= formatFechaActual("HH:mm");
		return hora;
	}
	/**
	 * Convierte una cadena fecha con formato yyyy-MM-dd a una cadena fecha  en formato dd/MM/yyyy.
	 * @param fecha, la fecha covertir si es vacio o null se cosiderara la fecha actual
	 * @param format
	 * @return
	 */
	public static String formatStringFecha(String fecha,String format){
		Date fechax=null;
		if(fecha==null || fecha.length()==0){
			fechax= new Date();
		}else{
			 fechax= FechaUtil.convertToDate(fecha, "yyyy-MM-dd");
		}

		Locale currentLocale = new Locale("pe","ES");
		DateFormat dateFormat = new SimpleDateFormat(format,
						 			 currentLocale);
		return dateFormat.format(fechax);
	}
	
	  public static boolean validateTime24Hours(String time){
		   if(time==null || time.equals("")) return false;
		   Pattern pattern;
		   Matcher matcher;
		   pattern = Pattern.compile(TIME24HOURS_PATTERN);
		   matcher = pattern.matcher(time);
		  return matcher.matches();
	  }
	  
	public static String formatFechaCarta(Date fechaActual) {
		Format formatter;
		String stringFecha = "";
		formatter = new SimpleDateFormat("dd");
		stringFecha = stringFecha + formatter.format(fechaActual)+" de ";
		formatter = new SimpleDateFormat("MMMM");
		stringFecha = stringFecha + formatter.format(fechaActual)+ " del ";
		formatter = new SimpleDateFormat("yyyy");
		stringFecha = stringFecha + formatter.format(fechaActual);

		return stringFecha;
	}
	  
}
