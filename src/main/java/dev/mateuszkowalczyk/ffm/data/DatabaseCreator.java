package dev.mateuszkowalczyk.ffm.data;

import dev.mateuszkowalczyk.ffm.data.database.image.Photo;
import dev.mateuszkowalczyk.ffm.data.database.utils.TableCreator;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseCreator {
    public Connection create() {
        var connection = this.createDatabase();
        this.createTables(connection);

        return connection;
    }

    private Connection createDatabase() {
        Connection connection = null;
        String path = "jdbc:sqlite:" + ResourceLoader.getInstance().getPath() + "app.db";

        try {
            connection = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    private void createTables(Connection connection) {
        TableCreator creator = new TableCreator(connection);

        creator.create("photos", new Photo());
    }
}
