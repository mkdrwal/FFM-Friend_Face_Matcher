package dev.mateuszkowalczyk.ffm.image;

import dev.mateuszkowalczyk.ffm.data.database.face.Face;
import dev.mateuszkowalczyk.ffm.data.database.face.FaceDAO;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;
import dev.mateuszkowalczyk.ffm.data.database.person.PersonDAO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.face.LBPHFaceRecognizer;

import java.util.ArrayList;
import java.util.List;

public class FaceRecognition {
    private PersonDAO personDAO = PersonDAO.getInstance();
    private FaceDAO faceDAO = FaceDAO.getInstance();

    public void recognize(Face faceToRecognize) {
        List<Face> faceList = faceDAO.getAll(true);
        List<Person> personList = personDAO.getAll(true);
        ArrayList<Mat> facesImages = new ArrayList<>();
        ArrayList<Integer> faceImagesIndex = new ArrayList<>();
        Person person;

        if (faceList.size() > 0) {
            faceList.forEach(face -> {
                var faceImage = face.getFaceToProcess();

                facesImages.add(faceImage);
                faceImagesIndex.add(faceList.indexOf(face));
            });

            LBPHFaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
            MatOfInt matOfInt = new MatOfInt();
            matOfInt.fromList(faceImagesIndex);
            faceRecognizer.train(facesImages, matOfInt);

            var result = faceRecognizer.predict_label(faceToRecognize.getFaceToProcess());

            if (result == 0) {
                person = createPerson();
                faceToRecognize.setPersonId(person.getId());
            } else {
                faceToRecognize.setPersonId(
                        faceList.get(faceImagesIndex.indexOf(result)).getPersonId()
                );
            }

        } else {
            person = this.createPerson();

            faceToRecognize.setPersonId(person.getId());
        }

    }

    private Person createPerson() {
        Person person = new Person();
        this.personDAO.add(person);

        return person;
    }

}
