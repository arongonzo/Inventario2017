package clases;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class Datos {
    
    
    
   private boolean validarusuario(String usuario, String clave)
   {
        if(usuario.equals("sulu") && clave.equals("123"))
        {
            return true;
        } 
        else 
        {
            return false;
        }
   }
   public static boolean validaruser(String usuario, String clave)
   {
       boolean blnEstado = false;
       Datos clsdatos = new Datos();
       blnEstado = clsdatos.validarusuario(usuario, clave);
       return blnEstado;
   }
   
   public static long DatetoMilisecond(Date string_date)
   {
        long milliseconds =0;
        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            milliseconds = string_date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return milliseconds;
   }
   
}

