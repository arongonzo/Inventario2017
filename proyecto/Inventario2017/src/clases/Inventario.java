package clases;

import Formularios.frmLogin;

public class Inventario {

    public static String global_llaveusuario; 
    
    public static void main(String[] args) {
        frmLogin clsMilogin = new frmLogin();
        clsMilogin.setLocationRelativeTo(null);/*tamaño total*/
        
        clsMilogin.setVisible(true);
    }
    
}
