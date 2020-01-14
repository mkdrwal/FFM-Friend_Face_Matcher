package dev.mateuszkowalczyk.ffm.data.database.photo;

import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class PhotoDAO implements Dao<Photo> {
    private DatabaseService databaseService = DatabaseService.getInstance();

    @Override
    public Optional<Photo> get(long id) {
        return Optional.empty();
    }

    @Override
    public void save(Photo photo) {

        String sql = "INSERT INTO photos(path) values (?)";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, photo.getPath());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Photo photo) {

    }

    @Override
    public void delete(Photo photo) {

    }
}
