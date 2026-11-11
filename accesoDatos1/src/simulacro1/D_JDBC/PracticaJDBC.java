import java.sql.*;
import java.math.BigDecimal;
public class PracticaJDBC {

    static void buscar(Connection cn,String texto) throws SQLException { 
        String sql = "SELECT id, nombre, nota FROM alumno WHERE nombre LIKE ?";
        
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            // Concatenar comodines % para la búsqueda parcial
            ps.setString(1, "%" + texto + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nota = rs.getString("nota");
                    System.out.println(
                        "Alumno [ID: " + rs.getInt("id") + 
                        ", Nombre: " + rs.getString("nombre") + 
                        ", Nota: " + (nota != null ? nota : "NULL") + "]");
                }
            }
        }
    }

    static long insertar(Connection cn,String nombre,BigDecimal nota) throws SQLException { 
        String sql = "INSERT INTO alumno (nombre, nota) VALUES (?, ?)";
        
        // Indicar que queremos recuperar las claves autogeneradas
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);

            if (nota != null) {
                ps.setBigDecimal(2, nota);
            } else {
                ps.setNull(2, java.sql.Types.DECIMAL);
            }

            ps.executeUpdate();
            
            // Obtener el ID generado por AUTO_INCREMENT
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return -1;
    }

    static void cambiarDosNotas(Connection cn,int id1,BigDecimal nota1,int id2,BigDecimal nota2) throws SQLException { 
        boolean autoCommitOriginal = cn.getAutoCommit();
        try {
            cn.setAutoCommit(false);

            String sql = "UPDATE alumno SET nota = ? WHERE id = ?";
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                // Modificar primero
                ps.setBigDecimal(1, nota1);
                ps.setInt(2, id1);
                if (ps.executeUpdate() == 0) throw new SQLException("No existe el alumno con ID: " + id1);

                // Modificar segundo
                ps.setBigDecimal(1, nota2);
                ps.setInt(2, id2);
                if (ps.executeUpdate() == 0) throw new SQLException("No existe el alumno con ID: " + id2);
            }
            cn.commit();

        } catch (SQLException e) {
            cn.rollback(); 
            throw e;
        } finally {
            cn.setAutoCommit(autoCommitOriginal);
        }
    }
    static void mostrarMetadatos(Connection cn) throws SQLException { 
        DatabaseMetaData dbmd = cn.getMetaData();
        System.out.println("Base de Datos: " + dbmd.getDatabaseProductName() + " " + dbmd.getDatabaseProductVersion());

        String sql = "SELECT * FROM alumno LIMIT 1";
        try (Statement st = cn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnas = rsmd.getColumnCount();
            
            System.out.println("Tabla alumno tiene " + columnas + " columnas:");
            for (int i = 1; i <= columnas; i++) {
                System.out.println(" - " + rsmd.getColumnName(i) + " (" + rsmd.getColumnTypeName(i) + ")");
            }
        }   
    }

    public static void main(String[] args) {
        try (Connection cn = BD.abrir()) {
            System.out.println("Conexión correcta");
            // Descomenta cada prueba al completar su método.
            buscar(cn,""); 
            buscar(cn,"An");
            System.out.println(insertar(cn,"Eva",null));
            cambiarDosNotas(cn,1,new BigDecimal("7.0"),2,new BigDecimal("8.0"));
            mostrarMetadatos(cn);
        } catch (Exception e) { 
            System.err.println(e.getClass().getSimpleName()+": "+e.getMessage()); 
        }

        // Añade una prueba de rollback con id 999999 y comprueba la nota previa.
        System.out.println("\nPRUEBA 1 (Rollback id 999999 inexistente):");
        try (Connection cn = BD.abrir()) {
            cambiarDosNotas(cn, 1, new BigDecimal("10.0"), 999999, new BigDecimal("5.0"));
        } catch (Exception e) {
            System.out.println("Excepcion: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}