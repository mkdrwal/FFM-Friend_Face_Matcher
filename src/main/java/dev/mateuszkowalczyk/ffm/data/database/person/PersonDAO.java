package dev.mateuszkowalczyk.ffm.data.database.person;

import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.Dao;
import dev.mateuszkowalczyk.ffm.data.database.face.Face;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDAO implements Dao<Person> {
    private List<Person> personList = new ArrayList<>();
    private static PersonDAO instance;
    private DatabaseService databaseService = DatabaseService.getInstance();

    private PersonDAO () {}

    public static PersonDAO getInstance() {
        if (instance == null) {
            instance = new PersonDAO();
        }

        return instance;
    }

    @Override
    public Optional<Person> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Person> getAll() {
        return this.getAll(false);
    }

    @Override
    public List<Person> getAll(boolean refresh) {
        if (this.personList.size() == 0 || refresh) {
            String sql = "SELECT * FROM persons";
            try {
                PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Person person = new Person(resultSet);
                    this.personList.add(person);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return this.personList;
    }

    @Override
    public void add(Person person) {
        String sql = "INSERT INTO persons (name) values(?)";

        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.executeUpdate();

            sql = "SELECT id FROM persons ORDER BY id DESC LIMIT 1";

            ResultSet resultSet = databaseService.getConnection().prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                person.setId(resultSet.getLong("id"));
                this.personList.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(Person person) {

    }
}
