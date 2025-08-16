package persistencia;
import factory.ConnectionFactory;
import model.Producto;

import java.sql.*;

public class ProductoDAO {
    final private Connection con;
    public ProductoDAO(Connection con){
        this.con=con;
    }
    public void guardarProducto(Producto producto) throws SQLException {
        try(con){
            con.setAutoCommit(false);
            final PreparedStatement statement=con.prepareStatement("INSERT INTO PRODUCTO(nombre,descripcion,cantidad)"+"VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            try(statement){
                ejecutaRegistro(producto, statement);
                con.commit();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("ROLLBACK de la transacción");
                con.rollback();
            }
        }
    }
    private static void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.execute();
        final ResultSet resulSet= statement.getGeneratedKeys();
        try(resulSet){
            while(resulSet.next()){
                producto.setId(resulSet.getInt(1));
                System.out.println(String.format("Fue insertado el producto %s",producto));
            }
        }
    }
}
