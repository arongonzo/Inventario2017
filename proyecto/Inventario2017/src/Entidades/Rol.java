/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author sam gonzo
 */
public class Rol {
    int id_rol;
    String nombre_rol;
    
    public Rol() {
    }
    
    public Rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    public int getIdrol() {
        return id_rol;
    }
    
    public void setIdUsuario(int id_rol) {
        this.id_rol = id_rol;
           
    }

    public String getRolName() {
        return nombre_rol;
    }

    public void setUserNameRolName(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }
}


