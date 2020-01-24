package dev.mateuszkowalczyk.ffm.data.database;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);

    List<T> getAll();
    List<T> getAll(boolean refresh);
    void add(T t);
    void update(T t);
    void delete (T t);

}
