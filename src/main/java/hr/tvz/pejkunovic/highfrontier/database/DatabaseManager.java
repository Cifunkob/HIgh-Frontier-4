package hr.tvz.pejkunovic.highfrontier.database;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private DatabaseManager() {}

    private static Connection connectToDatabase() throws SQLException {
        try {
            Properties configuration = new Properties();
            InputStream inputStream = DatabaseManager.class.getClassLoader()
                    .getResourceAsStream("application.properties");

            if (inputStream == null) {
                throw new RuntimeException("application.properties not found");
            }

            configuration.load(inputStream);

            String databaseURL = configuration.getProperty("spring.datasource.url");
            String databaseUsername = configuration.getProperty("spring.datasource.username");
            String databasePassword = configuration.getProperty("spring.datasource.password");

            if (databaseURL == null) {
                throw new SQLException("URL is not defined in configuration file");
            }

            return DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        } catch (IOException e) {
            throw new SQLException("Error while loading database", e);
        }
    }
}



