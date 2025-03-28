package org.example.interfaces;

public interface RedBlackTreeADT<T>{
    public void add(T item);
    public Object delete(T item);
    public Object search(T item);
    public boolean isEmpty();
    public int getSize();
}
