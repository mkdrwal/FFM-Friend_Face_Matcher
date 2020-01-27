package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.app.cache.ThumbnailCacheService;
import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;
import dev.mateuszkowalczyk.ffm.data.database.photo.PhotoDAO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class WorkspaceWrapper implements Runnable {
    private PhotoDAO photoDAO = PhotoDAO.getInstance();
    private ThumbnailCacheService thumbnailCacheService = ThumbnailCacheService.getInstance();
    private WorkspaceService workspaceService = WorkspaceService.getInstance();

    @Override
    public void run() {
        this.setFromDatabase();
        new DirectoryScanner().run();
    }

    private void setFromDatabase() {
        List<Photo> photoList = this.photoDAO.getAll();

        photoList.forEach(photo -> {
            var element = this.createImageViewElement(photo);

            workspaceService.addImage(element);
        });
    }

    public ImageView createImageViewElement(Photo photo) {
        Image image = new Image("file:" + photo.getCachedPath());

        return  new ImageView(image);
    }


}
