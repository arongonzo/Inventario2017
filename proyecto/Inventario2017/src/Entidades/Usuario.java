
package Entidades;

public class Usuario {
    int idusuario;
    String username;
    String clave;
    String nombre;
    String apellido;
    int id_rol;
    String rolname;
    boolean estado;

    public Usuario() {
    }

    public Usuario(int idusuario, String username, String clave, String nombre, String apellido, int id_rol, String rolname) {
        this.idusuario = idusuario;
        this.username = username;
        this.clave = clave;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_rol = id_rol;
        this.rolname = rolname;
    }

    
    public int getIdusuario() {
        return idusuario;
    }
    
    public void setIdUsuario(int idusuario) {
        this.idusuario = idusuario;
           
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getid_rol() {
        return id_rol;
    }

    public void setid_Rol(int id_rol) {
        this.id_rol = id_rol;
    }
    
    public String getRolname() {
        return rolname;
    }

    public void setRolname(String rolname) {
        this.rolname = rolname;
    }
    
   
    
}
