package dev.mateuszkowalczyk.ffm.data;

import dev.mateuszkowalczyk.ffm.app.cache.CacheService;
import dev.mateuszkowalczyk.ffm.data.database.utils.DatabaseCreator;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {
    private final String path;
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private CacheService cacheService = CacheService.getInstance();
    private static final DatabaseService instance = new DatabaseService();
    private Connection connection;

    private DatabaseService() {
        this.path = this.cacheService.getPath("app.db");
        this.checkIfDatabaseExists();
    }

    private void checkIfDatabaseExists() {
        var file = new File(this.path);

        if (!file.exists()) {
            DatabaseCreator creator = new DatabaseCreator();
            this.connection = creator.create(this.path);

            try {
                ResultSet resultSet = this.connection.prepareStatement("SELECT name FROM sqlite_master  WHERE  type ='table' AND name NOT LIKE 'sqlite_%';").executeQuery();

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            this.connect();
        }
    }

    public static DatabaseService getInstance() {
        return instance;
    }

    public void connect() {
        if (this.connection == null) {

            try {
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.path);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try {
                ResultSet resultSet = this.connection.prepareStatement("SELECT name FROM sqlite_master  WHERE  type ='table' AND name NOT LIKE 'sqlite_%';").executeQuery();

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
