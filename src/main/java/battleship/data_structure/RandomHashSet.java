package battleship.data_structure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHashSet<E> extends HashSet<E> {

    public E getRandomElement() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, this.size());
        int i = 0;
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (i == randomIndex) {
                return iterator.next();
            }
            iterator.next();
            i++;
        }
        return null;
    }

}
