package turtleraine.sandbox.com.lifequest.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionHelper {

    public static <T> List<T> makeList(T ... elements) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, elements);

        return list;
    }
}
