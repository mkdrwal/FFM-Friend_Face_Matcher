package dev.mateuszkowalczyk.ffm.data.database.image;

import dev.mateuszkowalczyk.ffm.data.database.annotation.Column;
import dev.mateuszkowalczyk.ffm.data.database.annotation.PrimaryKey;
import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;

@Table(name = "photo")
public class Photo {

    @Column
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
