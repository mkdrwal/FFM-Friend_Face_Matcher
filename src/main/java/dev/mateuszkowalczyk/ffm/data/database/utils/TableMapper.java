package dev.mateuszkowalczyk.ffm.data.database.utils;

import dev.mateuszkowalczyk.ffm.data.database.annotation.Table;

public class TableMapper {

    public String getName(Class object) {
        if (object.isAnnotationPresent(Table.class)) {
            Table annotation = (Table) object.getAnnotation(Table.class);
            String name = annotation.name();
            if (name.length() > 0) {
                return name;
            }
        }

        return object.getSimpleName().toLowerCase();
    }
}
