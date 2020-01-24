package dev.mateuszkowalczyk.ffm.data;

import dev.mateuszkowalczyk.ffm.app.cache.CacheService;
import dev.mateuszkowalczyk.ffm.data.database.utils.DatabaseCreator;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private CacheService cacheService = CacheService.getInstance();
    private static final DatabaseService instance = new DatabaseService();
    private Connection connection;

    private DatabaseService() {
        this.checkIfDatabaseExists();
    }

    private void checkIfDatabaseExists() {
        String path = this.cacheService.getPath("app.db");
        var file = new File(path);

        if (!file.exists()) {
            DatabaseCreator creator = new DatabaseCreator();
            this.connection = creator.create(path);
        } else {
            this.connect();
        }
    }

    public static DatabaseService getInstance() {
        return instance;
    }

    public void connect() {
        if (this.connection == null) {
            String path = ResourceLoader.getInstance().getPath("app.db");

            try {
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
