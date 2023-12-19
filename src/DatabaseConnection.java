import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12671421",
                    "sql12671421", "jL5AgNE5zP");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
