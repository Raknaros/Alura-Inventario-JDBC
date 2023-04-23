package controller;


import factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, String cantidad, Integer id) throws SQLException {
		Connection con=new ConnectionFactory().recuperaConexion();
		Statement statement=con.createStatement();
		statement.execute("UPDATE PRODUCTO SET NOMBRE='"+nombre+"',DESCRIPCION='"+descripcion+"',CANTIDAD="+cantidad+" WHERE ID="+id);
		return statement.getUpdateCount();
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con=new ConnectionFactory().recuperaConexion();
		Statement statement=con.createStatement();
		statement.execute("DELETE FROM PRODUCTO WHERE ID="+id);
		return statement.getUpdateCount();
	}

	public List<Map<String,String>> listar() throws SQLException {
		Connection con=new ConnectionFactory().recuperaConexion();
		Statement statement=con.createStatement();
		boolean result=statement.execute("SELECT ID,NOMBRE,DESCRIPCION,CANTIDAD FROM producto;");
		ResultSet resultSet=statement.getResultSet();
		List<Map<String,String>> resultado=new ArrayList<>();
		while(resultSet.next()){
			Map<String,String> fila=new HashMap<>();
			fila.put("ID",String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE",String.valueOf(resultSet.getString("NOMBRE")));
			fila.put("DESCRIPCION",String.valueOf(resultSet.getString("DESCRIPCION")));
			fila.put("CANTIDAD",String.valueOf(resultSet.getInt("CANTIDAD")));
			resultado.add(fila);
		}
		con.close();
		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
		Connection con=new ConnectionFactory().recuperaConexion();
		Statement statement=con.createStatement();
		statement.execute("INSERT INTO PRODUCTO(nombre,descripcion,cantidad)"+
		"VALUES('"+producto.get("NOMBRE")+"','"+producto.get("DESCRIPCION")+"',"+producto.get("CANTIDAD")+")",Statement.RETURN_GENERATED_KEYS);
		ResultSet resulSet=statement.getGeneratedKeys();
		while(resulSet.next()){
			System.out.println(String.format("Fue insertado el producto de ID %d",resulSet.getInt(1)));

		}
	}

}
