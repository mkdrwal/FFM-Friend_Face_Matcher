package dev.mateuszkowalczyk.ffm.data.database.face;

import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.Dao;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FaceDAO implements Dao<Face> {
    private static FaceDAO instance;
    private DatabaseService databaseService = DatabaseService.getInstance();
    private List<Face> faceList = new ArrayList<>();

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
    public List<Face> getAll() {
        return this.getAll(false);
    }

    @Override
    public List<Face> getAll(boolean refresh) {
        if (this.faceList.size() == 0 || refresh) {
            this.faceList.clear();

            String sql = "SELECT * FROM face";

            try {
                ResultSet resultSet = this.databaseService.getConnection().prepareStatement(sql).executeQuery();

                while (resultSet.next()) {
                    Face face = new Face(resultSet);
                    this.faceList.add(face);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return this.faceList;
    }

    @Override
    public void add(Face face) {
        String sql = "INSERT INTO face (name, path, photoId, personId) values (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, face.getName());
            preparedStatement.setString(2, face.getPath());
            preparedStatement.setLong(3, face.getPhotoId());
            preparedStatement.setLong(4, face.getPersonId());
            preparedStatement.executeUpdate();

            sql = "SELECT id FROM face ORDER BY id DESC LIMIT 1";

            ResultSet resultSet = this.databaseService.getConnection().prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                face.setId(resultSet.getInt("id"));
            }

            this.faceList.add(face);

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

    public Face getFirstFace(Person person) {
        String sql = "SELECT * FROM face WHERE face.personId = :personId LIMIT 1";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, person.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            Face face = null;
            while(resultSet.next()) {
                face = new Face(resultSet);

                return face;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
