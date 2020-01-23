package dev.mateuszkowalczyk.ffm.data.database.photo;

import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;

import java.sql.ResultSet;
import java.sql.SQLException;

@Table(name = "photos")
public class Photo {

    @Column(type = Column.Type.INT)
    @PrimaryKey
    private long id;
    @Column
    private String path;
    @Column
    private String fileName;
    @Column
    private String cachedPath;

    public Photo() {

    }

    public Photo(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.path = resultSet.getString("path");
        this.fileName = resultSet.getString("fileName");
        this.cachedPath = resultSet.getString("cachedPath");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCachedPath() {
        return cachedPath;
    }

    public void setCachedPath(String cachedPath) {
        this.cachedPath = cachedPath;
    }
}
