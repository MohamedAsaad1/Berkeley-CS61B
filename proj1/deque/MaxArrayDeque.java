package deque;

import java.util.Comparator;

public class MaxArrayDeque<Item> extends ArrayDeque<Item> {
    private final Comparator<Item> comparator;

    public MaxArrayDeque(Comparator<Item> comparator_) {
        comparator = comparator_;
    }

    public Item max(Comparator<Item> comparator) {
        if (isEmpty()) {
            return null;
        }
        int maxIndex = 0;
        for (int i = 1; i < size(); i++) {
            if (comparator.compare(get(i), get(maxIndex)) > 0) {
                maxIndex = i;
            }
        }
        return get(maxIndex);
    }
    public Item max() {
        return max(comparator);
    }
}