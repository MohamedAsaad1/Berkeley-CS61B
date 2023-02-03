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
            System.arraycopy(items , 0, temp, thePosition,firstCopy-items.length);
        }
        else {
            System.arraycopy(items , first, temp, rateOfSize,items.length );

        }
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
        nextLast = (nextLast + 1 + items.length) % items.length;
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
        return false;

    }
    public Item getRecursive(int index){
        return null;

    }
}
