package deque;
import java.util.Iterator;
public class LinkedListDeque<Item> implements Deque<Item>,  Iterable<Item>{
    private class Node {
        public Node prev;
        public Item item;
        public Node next;

        public Node(Node prev_, Item item_, Node next_) {
            item = item_;
            next = next_;
            prev = prev_;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel.prev = sentinel;
        size = 0;
    }


    private void firstNode(Item item) {
        Node first = new Node(sentinel, item, sentinel);
        sentinel.prev = first;
        sentinel.next = first;
        size = 1;
    }
    @Override
    public void addFirst(Item item) {
        if (isEmpty()) {
            firstNode(item);
        } else {
            Node oldNode = sentinel.next;
            sentinel.next = new Node(sentinel, item, oldNode);
            oldNode.prev = sentinel.next;
            size++;
        }
    }

    @Override
    public void addLast(Item item) {
        if (isEmpty()) {
            firstNode(item);
        } else {
            Node oldNode = sentinel.prev;
            Node last = new Node(oldNode, item, sentinel);
            sentinel.prev = last;
            oldNode.next = last;
            size++;
        }
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        Node pointer = sentinel.next;
        while (pointer != sentinel) {
            System.out.println(pointer.item + " ");
            pointer = pointer.next;
        }
        System.out.println();
    }
    @Override
    public Item removeFirst(){
        if(isEmpty()){
            return null;
        }
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return item;
    }
    @Override
    public Item removeLast() {
        if(isEmpty()){
            return null;
        }
        Item item = sentinel.prev.item;
        Node last = sentinel.prev.prev;
        sentinel.prev = last;
        last.next = sentinel;
        size--;
        return item;


    }
    @Override
    public Item get(int index){
        Node pointer = sentinel.next;
        for(;index > 0;index--){
            if(pointer == sentinel){
                return null;
            }
            pointer = pointer.next;
        }
        return pointer.item;
    }


    public boolean equals(Object o) {

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

    public Iterator<Item> iterator() {
        return new LDequeIterator();
    }
    private class LDequeIterator implements Iterator<Item> {
        private Node iterableNode;
        LDequeIterator() {
            iterableNode = sentinel.next;
        }
        public boolean hasNext() {
            return iterableNode != sentinel;
        }

        public Item next() {
            Item returnItem = iterableNode.item;
            iterableNode = iterableNode.next;
            return returnItem;
        }
    }

}
