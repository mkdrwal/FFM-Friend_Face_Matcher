package dev.mateuszkowalczyk.ffm.data.database.utils;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;
import dev.mateuszkowalczyk.ffm.utils.ResourceLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseCreator {
    public Connection create(String path) {
        var connection = this.createDatabase(path);
        this.createTables(connection);

        return connection;
    }

    private Connection createDatabase(String path) {
        Connection connection = null;
        path = "jdbc:sqlite:" + path;

        try {
            connection = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    private void createTables(Connection connection) {
        TableCreator creator = new TableCreator(connection);

        creator.create(Photo.class);
        creator.create(Face.class);
    }
}
