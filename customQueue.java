/**
 * The class represents the usage of our custom queue.
 * 
 * @author Yusuf Ali Bin Rayney Azmi
 * @author Choo Jun Ian
 */

public class customQueue<E> {
    public static final int INITIAL_CAPACITY = 16;    
    private E[] data = (E[]) new Object[INITIAL_CAPACITY];
    private int size = 0;

    /**
     * Adds an element into the queue
     * @param e the element to be added
     */
    public void add(E e){
        data[size++] = e; 
    }

    /**
     * Removes the element that is located in index '0'
     */
    public void removeFirst(){
        data[0] = null;
        for(int i = 0 ; i < size; i++)
        data[i] = data[i+1];

        size--;
    }  

    /**
     * Gets the data at the specified index in the queue
     * @param index position of the data in the queue
     * @return the data stored at the index in the queue
     */
    public E get(int index){
        // checkIndex(index);
        return data[index];
    }

    /**
     * Get the size of the queue
     * @return the size of the queue
     */
    public int getSize(){
        return size;
    }   
}