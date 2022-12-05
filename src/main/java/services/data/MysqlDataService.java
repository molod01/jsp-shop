package services.data;

import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class MysqlDataService implements DataService {
    private final String connectionString =
            "jdbc:mysql://localhost:3306/firstjavadb" +
                    "?useUnicode=true&characterEncoding=UTF-8" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPass = "pass192";
    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        connectionString, dbUser, dbPass
                );
            } catch (Exception ex) {
                System.out.println(
                        "MysqlDataService::getConnection " +
                                ex.getMessage());
            }
        }
        return connection;
    }
}
