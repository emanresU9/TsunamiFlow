package org.example;

public final class TreeNode<T> implements Comparable<TreeNode<T>>{
    private final T item;
    private boolean black;
    private TreeNode<T> left, right, parent;
    public TreeNode(T item) {
        if (item instanceof java.lang.Comparable) {
            this.item = item;
            black = false;
            left = right = parent = null;
        }
        else
            throw new IllegalArgumentException("Items must be comparable");
    }

    public void setBlack() {black = true;}
    public void setRed() {black = false;}
    public boolean isBlack(){return black;}

    public void setLeft(TreeNode<T> left) {this.left = left;}
    public void setRight(TreeNode<T> right) {this.right = right;}
    public void setParent(TreeNode<T> parent) {this.parent = parent;}

    public T getItem(){return item;}
    public TreeNode<T> getLeft() {return left;}
    public TreeNode<T> getRight() {return right;}
    public TreeNode<T> getParent() {return parent;}

    public int compareTo(TreeNode<T> other){
        Comparable<T> comparableValue = (Comparable<T>) item;
        return comparableValue.compareTo(other.getItem());
    }

    @Override
    public String toString() {
        return item.toString();
    }
}
