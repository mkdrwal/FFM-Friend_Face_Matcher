package dev.mateuszkowalczyk.ffm.app;

import dev.mateuszkowalczyk.ffm.app.cache.ThumbnailCacheService;
import dev.mateuszkowalczyk.ffm.data.database.photo.Photo;
import dev.mateuszkowalczyk.ffm.data.database.photo.PhotoDAO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class WorkspaceWrapper implements Runnable {
    private static WorkspaceWrapper instance = new WorkspaceWrapper();
    private PhotoDAO photoDAO = PhotoDAO.getInstance();
    private ThumbnailCacheService thumbnailCacheService = ThumbnailCacheService.getInstance();
    private WorkspaceService workspaceService = WorkspaceService.getInstance();
    private Thread directoryScannerThread;

    private WorkspaceWrapper () {}

    @Override
    public void run() {
        this.setFromDatabase();

        if (this.directoryScannerThread == null || !this.directoryScannerThread.isAlive()) {
            this.directoryScannerThread = new Thread(new DirectoryScanner());
            this.directoryScannerThread.start();
        }
    }

    public static WorkspaceWrapper getInstance() {
        return instance;
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
