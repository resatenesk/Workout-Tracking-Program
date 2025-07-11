package antrenmantakipcom.DataAccess.Concrete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static final String host = "localhost";
    public static final int port = 3306;
    private static final String URL = "jdbc:mysql://localhost:3306/WorkoutTrackingDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Mysql kullanıcı adı
    private static final String PASSWORD = ""; // Mysql şifren

    private static Connection connection;

    public static Connection connect() throws SQLException {
        try {

            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                //System.out.println("Veritabanına bağlanıldı.");
            }

        } catch (SQLException e) {
            System.out.println("hata");
        }
        return connection;
    }

}
