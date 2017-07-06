package clases;

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
}

