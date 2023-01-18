package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnector {
    private static final String PROP_FILE = "data/database.settings";
    private SQLServerDataSource dataSource;

    /**
     * Test main method to test if the connection
     * is successfully established.
     */
    public static void main(String[] args) throws IOException {
        DatabaseConnector dbConnector = new DatabaseConnector();

        try (Connection conn = dbConnector.getConnection()) {
            System.out.println("Connection works!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Maps database credentials from prop file.
     * @throws IOException
     */
    public DatabaseConnector() throws IOException {

        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(new File(PROP_FILE)));

        String server = databaseProperties.getProperty("Server");
        String database = databaseProperties.getProperty("Database");
        String user = databaseProperties.getProperty("User");
        String password = databaseProperties.getProperty("Password");

        dataSource = new SQLServerDataSource();
        dataSource.setServerName(server);
        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
