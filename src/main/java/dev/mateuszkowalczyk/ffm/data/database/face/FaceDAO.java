package dev.mateuszkowalczyk.ffm.data.database.face;

import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FaceDAO implements Dao<Face> {
    private static FaceDAO instance;
    private DatabaseService databaseService = DatabaseService.getInstance();

    private FaceDAO() {
    }

    public static FaceDAO getInstance() {
        if (instance == null) {
            instance = new FaceDAO();
        }

        return instance;
    }

    @Override
    public Optional<Face> get(long id) {
        return Optional.empty();
    }

    @Override
    public void save(Face face) {
        String sql = "INSERT INTO face (name, path, photoId) values (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, face.getName());
            preparedStatement.setString(2, face.getPath());
            preparedStatement.setLong(3, face.getPhotoId());
            preparedStatement.executeUpdate();

            sql = "SELECT id FROM face ORDER BY id DESC LIMIT 1";

            ResultSet resultSet = this.databaseService.getConnection().prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                face.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Face face) {

    }

    @Override
    public void delete(Face face) {

    }
}
