package dev.mateuszkowalczyk.ffm.data.database.face;

import dev.mateuszkowalczyk.ffm.app.cache.FacesCacheService;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;
import org.opencv.core.Mat;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Column(type = Column.Type.INT)
    private long photoId;
    @Column(type = Column.Type.INT)
    private long personId;
    private Mat faceToProcess;

    public Face() {
        this.name = UUID.randomUUID().toString();
    }

    public Face(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.path = resultSet.getString("path");
        this.photoId = resultSet.getInt("photoId");
        this.personId = resultSet.getInt("personId");
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
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

    public Mat getFaceToProcess() {
        if (this.faceToProcess == null) {
            FacesCacheService facesCacheService = new FacesCacheService();
            this.faceToProcess = facesCacheService.readFaceToProcess(this);
        }

        return faceToProcess;
    }

    public void setFaceToProcess(Mat faceToProcess) {
        this.faceToProcess = faceToProcess;
    }
}
