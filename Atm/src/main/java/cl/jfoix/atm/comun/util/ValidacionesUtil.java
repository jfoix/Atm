package cl.jfoix.atm.comun.util;

import java.util.regex.Pattern;


public class ValidacionesUtil {

	
	public static boolean validarFormatoRUT(String rut){
		return Pattern.compile("^\\d{1,8}[-][0-9kK]{1}$").matcher(rut).matches();
	}
	
	public static boolean validarRut(int rut, char dv) {
        
		int m = 0, s = 1;
        
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        
        return dv == (char) (s != 0 ? s + 47 : 75);
    }
}
