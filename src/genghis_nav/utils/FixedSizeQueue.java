package genghis_nav.utils;
import java.util.LinkedList;

public class FixedSizeQueue<T> extends LinkedList<T> {
    private final int maxSize;

    public FixedSizeQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T element) {
        boolean added = super.add(element);
        if (size() > maxSize) {
            // Remove the oldest element if the size exceeds the limit
            super.removeFirst();
        }
        return added;
    }
}