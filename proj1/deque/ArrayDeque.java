package deque;

import java.util.Iterator;

public class ArrayDeque<Item> implements Deque<Item>, Iterable<Item> {
    private int size;
    private Item[] items;
    private int nextFirst;
    private int nextLast;
    final private int doubledSize = 2;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    private int firstIndex() {
        return (nextFirst + 1) % items.length;
    }

    private int lastIndex() {
        return ((nextLast - 1) + items.length) % items.length;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void addFirst(Item item) {
        // Check for availability in array.
        if (check_full()) {
            resize(doubledSize * size());
        }

        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }


    @Override
    public void addLast(Item item) {
        // Check whether array is full.
        if (check_full()) {
            resize(2 * size);
        }

        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1) % items.length;
    }


    @Override
    public Item removeFirst() {
        Item removed = items[firstIndex()];

        if (removed == null) {
            return null;
        }

        items[firstIndex()] = null;
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        checkSize();

        return removed;
    }


    @Override
    public Item removeLast() {
        Item removed = items[lastIndex()];

        if (removed == null) {
            return null;
        }

        items[lastIndex()] = null;
        nextLast = ((nextLast - 1) + items.length) % items.length;
        size--;
        checkSize();
        return removed;
    }

    private boolean check_full(){
        return size() == items.length;
    }

    @Override
    public Item get(int index) {
        Item item = items[(firstIndex()+ index )% items.length];
        return item;
    }


    @Override
    public void printDeque(){
        for (int i = 0; i < size(); i++){
            System.out.println(get(i) + " ");
        }
        System.out.println();
    }
    /** Check usage ratio and resize if < 0.25, but do not shrink smaller than length of 8. */
    private void checkSize() {
        if ((items.length > 8 && ((float) size / items.length) < 0.25)){
            resize(items.length / 2);
        }
    }


    private void resize(int newSize) {
        Item[] temp = (Item[]) new Object[newSize];
        int rateOfSize = newSize / 4;
        int first = firstIndex();
        int last = lastIndex();
        int firstCopy = items.length - first;
        // For circular array, first will be at higher index than last.
        if (first >= last) {
            // First, copy from first of deque to end of array.
            System.arraycopy(items, first, temp, rateOfSize, firstCopy);
            // Calculate where to continue the copy operation in the new array.
            int nextStart = rateOfSize + items.length - first;
            // Then, copy from beginning of original array to last of deque.
            System.arraycopy(items, 0, temp, nextStart, last + 1);
        } else {
            System.arraycopy(items, first, temp, rateOfSize, size);
        }
        items = temp;
        nextFirst = (rateOfSize - 1 + items.length) % items.length;
        nextLast = (nextFirst + size + 1) % items.length;
    }

    public Iterator<Item> iterator() {
        return new ADequeIterator();
    }

    private class ADequeIterator<Item> implements Iterator<Item> {
        private int position;

        ADequeIterator() {
            position = 0;
        }

        public boolean hasNext() {
            return position < size;
        }

        public Item next() {
            Item returnItem = (Item) get(position);
            position += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        // For efficiency, check equality of reference.
        if (this == o) {
            return true;
        }

        if (o instanceof Deque) {
            Deque<Item> otherDeque = (Deque<Item>) o;
            if (otherDeque.size() != this.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                Item myItem = get(i);
                Item otherItemDeque = otherDeque.get(i);
                if (!myItem.equals(otherItemDeque)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

/* draft
package deque;
    public class ArrayDeque<Item> implements Deque<Item>  {
        private Item[] items;
        private int nextLast;
        private int nextFirst;
        private  int size;
        final private int doubledSize = 2;
        private  int x = items.length;
        private int firstIndex(){
            return (nextFirst + 1) % items.length;
        }

        private int lastIndex(){
            return (nextLast - 1 + items.length) % items.length;
        }
        public ArrayDeque(){
            items =  (Item[]) new Object[8];
            size =0;
            nextLast = 5;
            nextFirst =4;
        }
        //    public ArrayDeque(Item item){
//        items =  (Item[]) new Object[100];
//        items[0] = item;
//        size++;
//    }
        public void addFirst(Item item){
            if (check_full()){
                resize();
            }

            items[nextFirst] = item;
            size++;
            nextFirst = (nextFirst-1 + items.length) % items.length;
        }
        public void addLast(Item item){
            if (check_full()){
                resize();
            }
            items[nextLast] = item;
            size++;
            nextLast = (nextLast + 1) % items.length ;
        }
        public int size(){
            return size;
        }
        private void  resize(){
            Item[] temp = (Item[]) new Object[doubledSize * size()];
            int  first = firstIndex();
            int rateOfSize = 4;
            int last = lastIndex();
            int firstCopy = items.length - first;
            if (first >= last){
                System.arraycopy(items , first, temp, rateOfSize,firstCopy );
                int thePosition = rateOfSize+firstCopy;
                System.arraycopy(items , 0, temp, thePosition,last +1 );
            }
            else {
                System.arraycopy(items , first, temp, rateOfSize,items.length );

            }
            items = temp;
            nextFirst = (rateOfSize-1+ items.length) % items.length;
            nextLast = (nextFirst + size() + 1)% items.length;
        }
        private boolean check_full(){
            return size() == items.length;
        }
        public Item removeFirst(){
            int index =  firstIndex();
            Item item = items[index];
            if (item == null){
                return null;
            }
            size--;
            items[index] = null;
            nextFirst = (nextFirst + 1) % items.length;

            return item;
        }
        public Item removeLast(){
            int index =  lastIndex();
            Item item = items[index];
            if (item == null){
                return null;
            }
            size--;
            items[index] = null;
            nextLast = (nextLast - 1 + items.length) % items.length;
            return item;
        }
        public Item get(int index){
            Item item = items[(firstIndex()+ index )% items.length];
            return item;
        }

        public void printDeque(){
            for (int i = 0; i< size(); i++){
                System.out.println(get(i) + " ");
            }
            System.out.println();
        }

        public boolean equals(Object o){
            if (this ==  o){
                return true;
            }
            if (o instanceof Deque) {
                Deque<Item> theObject = (Deque<Item>) o;
                if (theObject.size()!= this.size()){
                    return false;
                }
                for(int i = 0;i<this.size();i++){
                    Item thisItem = get(i);
                    Item otherItem = theObject.get(i);
                    if(thisItem!=otherItem){
                        return false;
                    }
                }
                return true;
            }
            return  false;
        }

        ;

    }



 */

