import java.sql.*;

public class BD {
    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/simulacro_ad",
            System.getenv("DB_USER"),System.getenv("DB_PASSWORD"));
    }
}