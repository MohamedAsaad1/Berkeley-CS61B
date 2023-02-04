ackage randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestBuggyAList {

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeB = B.size();
                assertEquals(sizeL, sizeB);
            } else if (operationNumber == 2) {
                // getLast if size is non-zero
                if (L.size() > 0) {
                    int lastL = L.getLast();
                    int lastB = B.getLast();
                    assertEquals(lastL, lastB);
                }
            } else if (operationNumber == 3) {
                // removeLast if size is non-zero
                if (L.size() > 0) {
                    int lastL = L.removeLast();
                    int lastB = B.removeLast();
                    assertEquals(lastL, lastB);
                }
            }
        }
    }

    @Test
    public void testThreeAddThreeRemove() {
        // Create two AList objects to compare.
        AListNoResizing<Integer> noResize = new AListNoResizing<>();
        BuggyAList<Integer> buggyList = new BuggyAList<>();

        // Add to them.
        for (int i = 4; i < 7; i++) {
            noResize.addLast(i);
            buggyList.addLast(i);
        }

        // Compare sizes.
        assertEquals(noResize.size(), buggyList.size());

        // Remove the last element three times and compare each time.
        int a = noResize.removeLast();
        int b = buggyList.removeLast();
        assertEquals(a, b);
        assertEquals(noResize.removeLast(), buggyList.removeLast());
        assertEquals(noResize.removeLast(), buggyList.removeLast());
    }
}