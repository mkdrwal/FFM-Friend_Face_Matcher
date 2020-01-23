package dev.mateuszkowalczyk.ffm.data.database.face;

import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;

import java.util.UUID;

@Table
public class Face {
    @Column(type = Column.Type.INT)
    @PrimaryKey
    private long id;

    @Column
    private String name;

    @Column
    private String path;

    public Face() {
        this.name = UUID.randomUUID().toString();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
