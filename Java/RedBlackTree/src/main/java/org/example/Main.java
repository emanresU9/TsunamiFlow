package org.example;

import org.example.TreeNode;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        testStringData();
        testPrintTree();
        testDelete();
    }

    public static void testStringData(){
        String[] names = {"Chris","John","Jared","Bean","Abe", "Cameron"};
        Tree<String> nameTree = new Tree<>();
        for (String name: names) {
            nameTree.add(name);
        }
        System.out.println(nameTree.displayTree(6));
    }

    public static void testPrintTree(){
        Tree<Integer> nodeTree = new Tree<>();
        nodeTree.add(7);nodeTree.add(3);nodeTree.add(11);
        nodeTree.add(1);nodeTree.add(5);nodeTree.add(9);
        nodeTree.add(13);nodeTree.add(0);nodeTree.add(8);
        nodeTree.add(10);nodeTree.add(14);nodeTree.add(2);
        nodeTree.add(4);nodeTree.add(6);nodeTree.add(12);

        String testResult = nodeTree.displayTree(4);
        System.out.println(testResult);
    }
    public static void testSearch() {
        Tree<Integer> nodeTree = new Tree<>();
        nodeTree.add(7);nodeTree.add(3);nodeTree.add(11);
        nodeTree.add(1);nodeTree.add(5);nodeTree.add(9);
        nodeTree.add(13);nodeTree.add(0);nodeTree.add(8);
        nodeTree.add(10);nodeTree.add(14);

        System.out.println(nodeTree.displayTree(4));
        int num1=5,num2=3,num3=20;
        System.out.println("\n");
        System.out.printf("Search number: %s , result: %s\n", num1, nodeTree.search(num1));
        System.out.printf("Search number: %s , result: %s\n", num2, nodeTree.search(num2));
        System.out.printf("Search number: %s , result: %s\n", num3, nodeTree.search(num3));
    }

    public static void testDelete() {
        Tree<Integer> nodeTree = new Tree<>();
        nodeTree.add(7);nodeTree.add(3);nodeTree.add(11);
        nodeTree.add(1);nodeTree.add(5);nodeTree.add(9);
        nodeTree.add(13);nodeTree.add(0);nodeTree.add(8);
        nodeTree.add(10);nodeTree.add(14);nodeTree.add(2);
        nodeTree.add(4);nodeTree.add(6);nodeTree.add(12);

        int testnum1 = 7, testnum2 = 3, testnum3 = 9;
        int testnum4 = 5, testnum5 = 11, testnum6 = 1, testnum7 = 0;
        int[] testNumbers = {testnum1,testnum2,testnum3,testnum4,testnum5,testnum6,testnum7};

        for (int num: testNumbers) {
            System.out.printf("testDelete: %s  test: %s\n", num, nodeTree.delete(num).getItem() == num ? "passed" : "failed" );
        }
        System.out.println(nodeTree.displayTree());
    }
}