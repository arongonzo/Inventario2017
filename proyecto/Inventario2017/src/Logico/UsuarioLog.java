/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logico;

import Conecciones.UsuarioDao;
import Entidades.Usuario;
import java.util.List;

public class UsuarioLog {
    UsuarioDao usuarios = new UsuarioDao();

    public boolean AgregarUsuario(Usuario Usr) {
        return usuarios.AgregarUsuario(Usr);
    }

    public boolean UpdateUsuario(Usuario Usr) {
        return usuarios.UpdateUsuario(Usr);
    }

    public boolean DeleteUsuario(Usuario Usr) {
        return usuarios.DeleteUsuario(Usr);
    }

    public List<Usuario> listado(String filtro) {
      return usuarios.listado(filtro);
    }
}
