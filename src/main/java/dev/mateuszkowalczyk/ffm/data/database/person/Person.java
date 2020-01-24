package dev.mateuszkowalczyk.ffm.data.database.person;

import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Person {
    @PrimaryKey
    @Column(type = Column.Type.INT)
    private long id;

    @Column
    private String name;

    public Person() {
        this.name = UUID.randomUUID().toString();
    }

    public Person(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getLong("id");
        this.name = resultSet.getString("name");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
