package pe.com.bbva.util;

import java.util.ArrayList;
import java.util.List;

import pe.com.bbva.core.exceptions.BOException;

public class Util {
	
	 public static String isStringNull(Object o){
			if(o==null){
				return null;
			}else if(o.toString().trim().equals("")){
				return null;
			}else{
				return o.toString();
			}
		}
	 
	 public static String isString(Object o){
			if(o==null){
				return "";
			}else
				return o.toString();
		}
	 public static String addZerosToLeft(String numero,Integer valorMax){
			
			String numFormat="";
			
			if(numero.length() < valorMax.intValue()){
				//Rellenamos con ceros a la izquierda
				for(int i=numero.length();i<valorMax.intValue();i++){
					numFormat+="0";
				}
			}
			numFormat +=numero;
			
			return numFormat;
		}
	 public static  String removeCero(String obj){
		 int val=Integer.parseInt(obj);
		
		 return String.valueOf(val);
	 }
	 
	 public static List<String> separador(String valor,char separador){
		 
		 String	str="";
		 List<String> listaValor=new ArrayList<String>();
		 
		 for(int i=0;i<valor.length();i++){
			 
			 if(valor.charAt(i)!=separador){
				 str=str+valor.charAt(i);
			 }else{
				 listaValor.add(str);
				 str="";
			 }
		 }
		 
		 return listaValor;
	 }
}
