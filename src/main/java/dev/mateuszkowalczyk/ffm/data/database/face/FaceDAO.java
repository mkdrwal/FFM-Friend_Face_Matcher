package dev.mateuszkowalczyk.ffm.data.database.face;

import dev.mateuszkowalczyk.ffm.data.database.Dao;

import java.util.Optional;

public class FaceDAO implements Dao<Face> {

    @Override
    public Optional<Face> get(long id) {
        return Optional.empty();
    }

    @Override
    public void save(Face face) {

    }

    @Override
    public void update(Face face) {

    }

    @Override
    public void delete(Face face) {

    }
}
