package pe.com.bbva.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatUtil {
	
	private static DecimalFormatSymbols newSymbols = new DecimalFormatSymbols();

	/**
	 * formatea una cantidad 48512,66 al formato  48,512.66
	 * @param monto
	 */
	public static String formatImporte(String monto){
		String entero = "";
		String decimal="";
		String nuevo="";
		if(!ValidateUtil.validarImporte(monto)){
			if(monto.trim().equals("")){
				return "00.00";
			}
			if(monto.indexOf(".")!=-1){
				entero = monto.substring(0,monto.indexOf("."));
				decimal = monto.substring(monto.indexOf("."));
			}else{
				entero = monto;
				decimal=".00";
			}
			int count=0;
			for(int i = entero.length()-1;i>=0;i--){
				nuevo=entero.charAt(i)+nuevo;
				count++;
				if(count%3==0 &&
				   i!=0){
					nuevo=","+nuevo;
				}
			}
		}else{
			nuevo = monto;
		}
		return nuevo+decimal;
	}
	
	/**
	 * Redondear a dos decimales
	 * @param d
	 * @return
	 */
	public static String roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("###,###.##");
    	return (twoDForm.format(d));
	}
	public static String roundTwoDecimalsPunto(double d) {
		String valorNum="";
		try{
	    	DecimalFormat twoDForm = new DecimalFormat("###,##0.00");
	    	newSymbols.setDecimalSeparator('.');
	    	newSymbols.setGroupingSeparator(',');
	    	twoDForm.setDecimalFormatSymbols(newSymbols);
	    	valorNum=twoDForm.format(d);
		}catch(Exception ex){
			valorNum="0.00";
		}
    	return valorNum;
	}
	/**
     * Redondea u double a un numero especifico de decimales
     *
     * @param valor a ser redondeado.
     * @param numero de decimales para redondear.
     * @return valor redondeado.
     */
    public static double round(double val, int places) {
		long factor = (long)Math.pow(10,places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double)tmp / factor;
    }

    /**
     *  Redondea u float a un numero especifico de decimales
     *
     * @param valor a ser redondeado.
     * @param numero de decimales para redondear.
     * @return valor redondeado.
     */
    public static float round(float val, int places) {
    	return (float)round((double)val, places);
    }

    public static int getCharUTF(byte x){
    	return  x<0?x+256:x;
    }
    
	public static String FormatNumeroSinComa(String valor) {
		String d="0";
		 if (valor!=null){
			d=valor.replace(",", "");			 
		 }else{
			 d="0";
		 }    	
    	return d;
	}
	
	/**
	 * Conversion usado para convertir numeros con notacion cientifica 
	 * a números decimales.
	 * @param valor
	 * @return
	 */
	public static String conversion(double valor)
  	{
      Locale.setDefault(Locale.US);
      DecimalFormat num = new DecimalFormat("#,###.##");
      return num.format(valor);
  	}

    
	public static void main(String[] args) {
		//logger.info(roundTwoDecimalsPunto(9999978.888));
		double x = 9999978.888;
		float y = 9.87654f;
		double z;
		float w;

		z = round(x,2);
		//logger.info(z);
		z = round(x,5);
		//logger.info(z);

		//logger.info();

		w = round(y,3);
		//logger.info(w);
		w = round(y,0);
		//logger.info(w);

		//logger.info(formatImporte("00.00"));
	}
}
