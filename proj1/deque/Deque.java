package deque;

public interface Deque<Item> {
    public void addFirst(Item item);
    public void addLast(Item item);
    default boolean isEmpty(){
        return size() == 0;
    }
    public int size();
//    public void printDeque();
//    public T removeFirst();
//    public T removeLast();
//    public T get(int index);
//    public boolean equals(Object o);
//    public T getRecursive(int index);
}
