package dev.mateuszkowalczyk.ffm.data.database.photo;

import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;

@Table(name = "photos")
public class Photo {

    @Column(type = Column.Type.INT)
    @PrimaryKey
    private long id;

    @Column
    private String path;

    @Column
    private String cachedPath;

    public long getId() {
        return id;
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
