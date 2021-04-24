package Vorlesung.Trees;

import java.util.ArrayList;

public class BinaryTreeTesting {
    public static void main(String[] args) {
        ArrayList<BinaryTree<Integer>> al = new ArrayList<>();
        BinaryTree<Integer> zero = new BinaryTree<>(null, null, null, 0);
        BinaryTree<Integer> one = new BinaryTree<>(null, null, null, 1);
        BinaryTree<Integer> two = new BinaryTree<>(null, null, null, 2);
        BinaryTree<Integer> three = new BinaryTree<>(null, null, null, 3);
        BinaryTree<Integer> four = new BinaryTree<>(null, null, null, 4);
        BinaryTree<Integer> five = new BinaryTree<>(null, null, null, 5);
        BinaryTree<Integer> six = new BinaryTree<>(null, null, null, 6);
        BinaryTree<Integer> seven = new BinaryTree<>(null, null, null, 7);

        al.add(zero);
        al.add(one);
        al.add(two);
        al.add(three);
        al.add(four);
        al.add(five);
        al.add(six);
        al.add(seven);

        zero.setLeftChild(one);
        zero.setRightChild(two);
        one.setLeftChild(three);
        one.setRightChild(four);
        two.setLeftChild(five);
        two.setRightChild(six);
        five.setLeftChild(seven);


        zero.traversePreorder(zero);
        System.out.println();
        zero.traversePreorderIterative(zero);
        System.out.println();
        zero.traversePreorderIterativeEasy(zero);
//        for (BinaryTree<Integer> x : al) {
//            System.err.println("Traversing " + x + ": ");
//            x.traversePreorder(x);
//            System.err.println("--------------- Traversing finished -----------");
//            System.out.println();
//        }
    }
}
