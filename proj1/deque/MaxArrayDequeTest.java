package deque;
import org.junit.Test;
import java.util.Comparator;
import static org.junit.Assert.*;

class TestIntComparator implements Comparator<Integer> {
    // Returns positive int if foo > bar; 0 if foo == bar; and a negative int if foo < bar.
    @Override
    public int compare(Integer foo, Integer bar) {
        return foo - bar;
    }
}

class TestStringComparator implements Comparator<String> {
    @Override
    public int compare(String foo, String bar) {
        return foo.compareTo(bar);
    }
}

public class MaxArrayDequeTest {
    @Test
    public void MaxADTest() {
        TestIntComparator tc = new TestIntComparator();
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(tc);
        assertTrue("Initialized LLD should be empty.", mad1.isEmpty());
        mad1.addFirst(420);
        mad1.addFirst(777);
        assertEquals("mad1 should now have two items.", 2, mad1.size());
        int currMax = mad1.max();
        assertEquals("Max value of mad1 should be 777.", 777, currMax);
        TestStringComparator tc2 = new TestStringComparator();
        MaxArrayDeque<String> mad2 = new MaxArrayDeque<>(tc2);
        assertTrue("Initialized LLD should be empty.", mad2.isEmpty());
        mad2.addFirst("Hello");
        mad2.addFirst("Oh!");
        mad2.addLast("my dude.");
        assertEquals("mad2 should have three items.", 3, mad2.size());
        String longestStr = mad2.max();
        assertEquals("mad2's longest string should be 'my dude.'", "my dude.", longestStr);
    }
}