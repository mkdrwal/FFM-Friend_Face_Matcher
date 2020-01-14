package dev.mateuszkowalczyk.ffm.data.database.utils;

import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
    private TableMapper tableMapper = new TableMapper();
    private final Connection connection;

    public TableCreator(Connection connection) {
        this.connection = connection;
    }

    public void create(Class object) {
        String sql = "CREATE TABLE IF NOT EXISTS " + this.tableMapper.getName(object);

        Field[] fields = object.getDeclaredFields();

        sql += this.addFields(fields);

        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String addFields(Field[] fields) {
        String sql = " ( \n ";
        List<String> listOfFields = new ArrayList<>();

        for (Field field :fields) {
            if (field.isAnnotationPresent(Column.class)) {
                listOfFields.add(
                    field.getName() +
                    " " +
                    this.getType(field.getAnnotation(Column.class)) +
                    (field.isAnnotationPresent(PrimaryKey.class) ? " PRIMARY KEY " : "")
                );
            }
        }

        sql += String.join(", \n ", listOfFields);

        return sql + ") ";
    }

    private String getType(Column annotation) {
        switch (annotation.type()) {
            case INT:
                return "integer";
            case STRING:
            default:
                return "text";
        }
    }


}
