package dev.mateuszkowalczyk.ffm.data;

import dev.mateuszkowalczyk.ffm.data.database.utils.DatabaseCreator;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static final DatabaseService instance = new DatabaseService();
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private Connection connection;

    private DatabaseService() {
        this.checkIfDatabaseExists();
    }

    private void checkIfDatabaseExists() {
        var db = this.resourceLoader.getResource("app.db");
        if (db == null) {
            DatabaseCreator creator = new DatabaseCreator();
            this.connection = creator.create();
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
