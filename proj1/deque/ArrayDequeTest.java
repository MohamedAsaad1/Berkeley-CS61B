package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class ArrayDequeTest {

    @Test
    /* Construct an empty and a one-item Array Deque. */
    public void constructorTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(5);
        assertFalse(ad1.isEmpty());
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        assertTrue(ad2.isEmpty());
    }

    @Test
    /* Add to deque at front and back. Check whether empty.
     */
    public void addFrontAddLastTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(5);
        ad1.addFirst(4);
        ad1.addLast(6);
        assertFalse(ad1.isEmpty());
    }

    @Test
    /* Add to deque to resize it. Check size.*/
    public void addResizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(4);
        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        ad1.addFirst(0);
        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        ad1.addFirst(0);
        ad1.addFirst(0);
        assertEquals(10, ad1.size());
    }

    @Test
    /* Add to front and back of deque to resize it. Check size. */
    public void frontBackResizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(5);
        ad1.addFirst(4);
        ad1.addLast(6);
        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        ad1.addFirst(0);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9);
        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        ad1.addFirst(0);
        ad1.addFirst(0);
        ad1.addLast(13);
        ad1.addLast(22);
        assertEquals(17, ad1.size());
    }

    @Test
    /* Adds items to the back to cause circular repositioning and resize. */
    public void addLastResizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9);
        assertEquals(10, ad1.size());
    }

    @Test
    /* Adds and removes items from empty, 1-item, and 2+ item deques.
     * Removes items to cause resize. */
    public void addRemoveTest() {
        ArrayDeque<Integer> adi = new ArrayDeque<>();
        assertNull(adi.removeFirst());
        assertNull(adi.removeLast());

        adi.addLast(0);
        int removed = adi.removeFirst();
        assertEquals(0, removed);
        assertNull(adi.removeFirst());
        assertNull(adi.removeLast());
        adi.addLast(1);
        removed = adi.removeLast();
        assertEquals(1, removed);
        assertTrue(adi.isEmpty());
        adi.addFirst(1);
        removed = adi.removeFirst();
        assertTrue(adi.isEmpty());
        assertEquals(1, removed);

        for (int i = 0; i < 25; i++) {
            if (i % 2 == 0) {
                adi.addFirst(i);
            } else {
                adi.addLast(i);
            }
        }
        assertEquals(25, adi.size());

        for (int i = 0; i < 10; i++) {
            adi.removeFirst();
        }

        for (int i = 0; i < 1000; i++) {
            int randVal = StdRandom.uniform(0, 2);
            if (randVal == 0) {
                adi.removeLast();
                adi.removeLast();
            } else if ( randVal == 1) {
                adi.addLast(randVal);
            }
        }
    }

    @Test
    /* Creates a deque with circularity and gets items. */
    public void getPrintTest() {
        ArrayDeque<Integer> adi = new ArrayDeque<>();
        adi.addLast(6);
        adi.addLast(7);
        adi.addFirst(5);
        adi.addFirst(4);
        adi.addFirst(3);
        adi.addFirst(2);
        adi.addFirst(1);
        adi.addFirst(0);
        // Array should look like: [2, 3, 4, 5, 6, 7, 0, 1]
        // Front = 6, Back = 5
        int getFront = adi.get(0);
        int getBack = adi.get(7);
        int getSecond = adi.get(1);
        assertEquals(0, getFront);
        assertEquals(7, getBack);
        assertEquals(1, getSecond);
        String testPrint = "0 1 2 3 4 5 6 7\n";
        System.out.println(testPrint);
        adi.printDeque();
    }

//    @Test
//    public void randomizedTest() {
//        LinkedListDeque<Integer> L = new LinkedListDeque<>();
//        ArrayDeque<Integer> A = new ArrayDeque<>();
//        int size = 0;
//
//        int N = 5000;
//        for (int i = 0; i < N; i += 1) {
//            int operationNumber = StdRandom.uniform(0, 5);
//            if (operationNumber == 0) {
//                // addLast
//                int randVal = StdRandom.uniform(0, 100);
//                L.addLast(randVal);
//                A.addLast(randVal);
//                size += 1;
//                assertEquals(size, L.size());
//            } else if (operationNumber == 1) {
//                // addFirst and size
//                int randVal = StdRandom.uniform(0, 100);
//                L.addFirst(randVal);
//                A.addFirst(randVal);
//                size += 1;
//                assertEquals(size, A.size());
//            } else if (operationNumber == 2) {
//                // get if size is non-zero
//                if (size > 1) {
//                    int randIndex = StdRandom.uniform(0, L.size() - 1);
//                    int getL = L.get(randIndex);
//                    int getA = A.get(randIndex);
//                    assertEquals(getL, getA);
//                }
//            } else if (operationNumber == 3) {
//                // removeLast if size is non-zero
//                L.removeLast();
//                A.removeLast();
//                if (size > 0) {
//                    size -= 1;
//                }
//                assertEquals(size, L.size());
//            } else if (operationNumber == 4) {
//                L.removeFirst();
//                A.removeFirst();
//                if (size > 0) {
//                    size -= 1;
//                }
//                assertEquals(size, L.size());
//            }
//        }
//    }

    @Test
    public void iterTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        int[] testInts = {0, 1, 2, 3, 4};
        for (int i = 0; i < 5; i++) {
            ad1.addLast(i);
        }

        Iterator<Integer> iterObject = ad1.iterator();
        int i = 0;
        while (iterObject.hasNext()) {
            int j = iterObject.next();
            assertEquals("Values should be equal.", testInts[i], j);
            i += 1;
        }

        for (int k : ad1) {
            System.out.println(k);
        }
    }

//    @Test
//    public void equalsTest() {
//        ArrayDeque<String> ad1 = new ArrayDeque<>();
//        ad1.addLast("b");
//        ad1.addLast("c");
//        ad1.addLast("d");
//        ad1.addLast("e");
//
//        ArrayDeque<String> ad2 = new ArrayDeque<>();
//        ad2.addLast("b");
//        ad2.addLast("c");
//        ad2.addLast("d");
//
//        assertFalse(ad1.equals(ad2));
//
//        ad2.addLast("e");
//        assertTrue(ad1.equals(ad2));
//
//        LinkedListDeque<String> lld2 = new LinkedListDeque<>();
//        lld2.addLast("b");
//        lld2.addLast("c");
//        lld2.addLast("d");
//        lld2.addLast("e");
//
//        assertTrue(ad1.equals(lld2));
//    }
}