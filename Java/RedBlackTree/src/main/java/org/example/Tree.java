package org.example;

import org.example.interfaces.RedBlackTreeADT;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> implements RedBlackTreeADT<T>{
    private TreeNode<T> root; 
    private int size;
    private int modCount;

    public Tree(){
        root = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public void add(T item) {
        if (!(item instanceof java.lang.Comparable))
            throw new IllegalArgumentException();
        TreeNode<T> node_to_add = new TreeNode<>(item);
        if (root == null) {
            node_to_add.setBlack();
            root = node_to_add;
        }
        else
            add_recursive(root, node_to_add);
        size++;
        modCount++;
    }
    private void add_recursive(TreeNode<T> parent, TreeNode<T> node_to_add) {
        TreeNode<T> next = null;
        if (node_to_add.compareTo(parent) < 0) {
            if (parent.getLeft() != null)
                add_recursive(parent.getLeft(),node_to_add);
            else {
                parent.setLeft(node_to_add);
                node_to_add.setParent(parent);
            }
        }
        else {
            if (parent.getRight() != null)
                add_recursive(parent.getRight(),node_to_add);
            else {
                parent.setRight(node_to_add);
                node_to_add.setParent(parent);
            }
        }
    }

    @Override
    public TreeNode<T> delete(T item) {
        TreeNode<T> togoNode, replacementNode, current;
        if ((togoNode = search(item)) == null)
            throw new IllegalArgumentException("Deletion error: could not find " + item.toString() + " in tree");
        //Take out replacement node from tree
        if (togoNode.getRight() != null) {
            current = togoNode.getRight();
            while (current.getLeft() != null) {
                current = current.getLeft();
            }
            replacementNode = current;
            if (replacementNode != togoNode.getRight()) {
                if (replacementNode.getRight() != null) {
                    replacementNode.getParent().setLeft(replacementNode.getRight());
                    replacementNode.getRight().setParent(replacementNode.getParent());
                } else
                    replacementNode.getParent().setLeft(null);
            }
        }
        else if (togoNode.getLeft() != null) {
            current = togoNode.getLeft();
            while (current.getRight() != null) {
                current = current.getRight();
            }
            replacementNode = current;
            if (replacementNode.getLeft() != null) {
                replacementNode.getParent().setRight(replacementNode.getLeft());
                replacementNode.getLeft().setParent(replacementNode.getParent());
            }
            else
                replacementNode.getParent().setRight(null);
        }
        else
            replacementNode = null;

        if (replacementNode != null && (replacementNode == togoNode.getLeft() || replacementNode == togoNode.getRight())) {
            if (replacementNode.compareTo(togoNode) < 0)
                togoNode.setLeft(null);
            else
                togoNode.setRight(null);
        }

        //Swap in replacementNode for togoNode
        if (replacementNode != null) {
            if (togoNode != root){
                if (togoNode.compareTo(togoNode.getParent()) < 0)
                    togoNode.getParent().setLeft(replacementNode);
                else
                    togoNode.getParent().setRight(replacementNode);
                replacementNode.setParent(togoNode.getParent());
            }
            else {
                root = replacementNode;
                replacementNode.setParent(null);
            }
            replacementNode.setLeft(togoNode.getLeft());
            replacementNode.setRight(togoNode.getRight());
            if (togoNode.getLeft() != null)
                togoNode.getLeft().setParent(replacementNode);
            if (togoNode.getRight() != null)
                togoNode.getRight().setParent(replacementNode);
        }
        else if (togoNode.getParent() != null){
            if (togoNode.compareTo(togoNode.getParent()) < 0)
                togoNode.getParent().setLeft(null);
            else
                togoNode.getParent().setRight(null);
        }
        togoNode.setLeft(null);
        togoNode.setRight(null);
        togoNode.setParent(null);
        size--;
        modCount++;
        return togoNode;
    }

    @Override
    public TreeNode<T> search(T item) {
        if (root == null)
            return null;
        Comparable<T> comparableItem = (Comparable<T>) item;
        return search_recursive(root, comparableItem);
    }
    private TreeNode<T> search_recursive(TreeNode<T> current, Comparable<T> comparableItem) {
        if (comparableItem.compareTo(current.getItem()) == 0)
            return current;
        else
            if (comparableItem.compareTo(current.getItem()) < 0 && current.getLeft() != null)
                return search_recursive(current.getLeft(),comparableItem);
            else if (comparableItem.compareTo(current.getItem()) >= 0 && current.getRight() != null)
                return search_recursive(current.getRight(),comparableItem);
            else
                return null;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int getSize() {
        return size;
    }

    public int getDepth() {
        if (size == 0)
            return -1;
        return get_depth_recursive(root);
    }
    private int get_depth_recursive(TreeNode<T> current) {
        if (current.getLeft() == null && current.getRight() == null)
            return 0;

        int leftDepth, rightDepth;
        if (current.getLeft() != null)
            leftDepth = get_depth_recursive(current.getLeft());
        else
            leftDepth = 0;

        if (current.getRight() != null)
            rightDepth = get_depth_recursive(current.getRight());
        else
            rightDepth = 0;

        return (Math.max(leftDepth, rightDepth)) +1;
    }

    private void shiftLeftLogic(TreeNode<T> pivot) {
        TreeNode<T> toPivot = pivot.getRight();
        if (pivot.getParent() != null) {
            if (pivot.compareTo(pivot.getParent()) < 0)
                pivot.getParent().setLeft(toPivot);
            else
                pivot.getParent().setRight(toPivot);
            toPivot.setParent(pivot.getParent());
        }
        else
            toPivot.setParent(null);
        if (toPivot.getLeft() != null) {
            toPivot.getLeft().setParent(pivot);
            pivot.setRight(toPivot.getLeft());
        }
        toPivot.setLeft(pivot);
        pivot.setParent(toPivot);
    }

    private void shiftRightLogic(TreeNode<T> pivot) {
        TreeNode<T> toPivot = pivot.getLeft();
        if (pivot.getParent() != null) {
            toPivot.setParent(pivot.getParent());
            if (pivot.compareTo(pivot.getParent()) < 0)
                pivot.getParent().setLeft(toPivot);
            else
                pivot.getParent().setRight(toPivot);
        }
        else
            toPivot.setParent(null);
        if (toPivot.getRight() != null){
            pivot.setLeft(toPivot.getRight());
            pivot.getRight().setParent(pivot);
        }
        toPivot.setRight(pivot);
        pivot.setParent(toPivot);
    }

    public String displayTree(int itemWidth) {
        if (itemWidth < 4)
            itemWidth = 4;
        int offset;
        if (isEmpty()) {
            return "<<< An Empty Tree >>>";
        }
        StringBuilder sb = new StringBuilder();
        List<TreeNode<T>> nodes = new ArrayList<>();
        List<Integer> levelList = new ArrayList<>();
        nodes.addFirst(root);
        levelList.addFirst(0);
        int printDepth = getDepth();
        int poss_nodes = (int) Math.pow(2,printDepth+1) - 1;
        int nodeCount = 0;
        int prevLevel = -1;

        //Iterate every position in tree of printHeight
        TreeNode<T> currentNode;
        int currentLevel;
        while (nodeCount < poss_nodes) {
            currentNode = nodes.removeFirst();
            currentLevel = levelList.removeFirst();
            //Go to next node position
            if (currentLevel > prevLevel) {
                sb.append("\n\n");
                prevLevel = currentLevel;
                for (int j=0; j < Math.pow(2, printDepth -currentLevel) -1; j++) {
                    sb.append(emptySpace( itemWidth ));
                }
            }
            else {
                for (int j = 0; j < Math.pow(2, printDepth -currentLevel +1) -1; j++) {
                    sb.append(emptySpace( itemWidth ));
                }
            }
            //place a centered node value or null msg
            if (currentNode == null) {
                offset = (itemWidth-4)/2;
                sb.append(String.format("%s%s%s", emptySpace(offset),"null",emptySpace(offset)));
                if ((itemWidth-4) % 2 != 0)
                    sb.append(' ');
            }
            else {
                offset = (itemWidth - currentNode.toString().length())/2;
                sb.append(String.format("%s%s%s",emptySpace(offset),currentNode.toString(),emptySpace(offset)));
                if ((itemWidth-currentNode.toString().length()) % 2 != 0)
                    sb.append(' ');
            }

            //Add children to the nodeList and thier levels to the levelList
            if (currentNode != null) {
                nodes.addLast(currentNode.getLeft());
                nodes.addLast(currentNode.getRight());
            }
            else {
                nodes.addLast(null);
                nodes.addLast(null);
            }
            levelList.addLast(currentLevel+1);
            levelList.addLast(currentLevel+1);

            nodeCount++;
        }
        return sb.toString();
    }
    public String displayTree(){
        return displayTree(4);
    }
    private String emptySpace(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

}
