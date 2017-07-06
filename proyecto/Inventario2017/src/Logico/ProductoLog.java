
package Logico;
import Conecciones.ProductoDao;
import Entidades.Producto;
import java.util.List;

public class ProductoLog {
    ProductoDao productos = new ProductoDao();

    public boolean AgregarProducto(Producto Prd) {
        return productos.AgregarProducto(Prd);
    }

    public boolean UpdateProducto(Producto Prd) {
        return productos.UpdateProducto(Prd);
    }

    public boolean DeleteProducto(Producto Prd) {
        return productos.DeleteProducto(Prd);
    }

    public List<Producto> listado(int id_producto, String nsn, String descripcion, String nParte) {
      return productos.listado(id_producto, nsn, descripcion, nParte);
    } 
}
