package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionEjemplo {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
			Statement st=con.createStatement();
			st.execute("USE NORTHWIND");
			ResultSet rs=st.executeQuery("SELECT * FROM categories");
			ResultSetMetaData rsmd=rs.getMetaData();
			
			System.out.println(rsmd.getColumnCount());
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.println(rsmd.getColumnName(i));
				System.out.println(rsmd.getColumnType(i));
				System.out.println(rsmd.getColumnTypeName(i));

			}
			
			while(rs.next()) {
				System.out.println(rs.getObject(1) + " " + rs.getString(2) + " " + rs.getString(3));
			}
			
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}