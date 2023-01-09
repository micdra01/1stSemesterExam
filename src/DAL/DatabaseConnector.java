package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class DatabaseConnector {
    private SQLServerDataSource dataSource;

    /**
     * todo set a class so we can hide login info
     */
    public DatabaseConnector()
    {
        //sets the information for database
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("Fjernbetjening");
        dataSource.setUser("CSe22A_18");
        dataSource.setPassword("CSe22A_18");
        dataSource.setTrustServerCertificate(true);
        dataSource.setPortNumber(1433);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * Test main method to test if the connection
     * is successfully established.
     */
    public static void main(String[] args) {
        DatabaseConnector dbConnector = new DatabaseConnector();

        try (Connection conn = dbConnector.getConnection()) {
            System.out.println("Connection works!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
