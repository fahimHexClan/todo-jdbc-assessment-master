package lk.ijse.todo.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection databaseConnection = null;
    private Connection connection;

    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/JdbcExam",
                "root",
                "Ijse@1234"
        );
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (databaseConnection == null) {
            databaseConnection = new DbConnection();
        }
        return databaseConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
