package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Node<T> {
    private T element;
    private Node<T> next;

    public Node(T element) {
        this.element = element;
        next = null;
    }

    public static <T> Node<T> fromList(List<T> collection) {
        if (collection.isEmpty()) {
            return null;
        }

        Node<T> first = null;
        Node<T> current = null;

        ListIterator<T> iterator = collection.listIterator();
        while(iterator.hasNext()) {
            T data = iterator.next();
            if (current == null) {
                first = new Node<>(data);
                current = first;
            } else {
                current.next = new Node<>(data);
                current = current.next;
            }
        }

        return first;
    }

    public static <T> List<T> toList(Node<T> node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();

        Node<T> curNode = node;
        list.add(curNode.getElement());
        while (curNode.hasNext()) {
            curNode = curNode.next;
            list.add(curNode.getElement());
        }

        return list;
    }

    public static <T> List<Node<T>> toNodeList(Node<T> node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<Node<T>> list = new ArrayList<>();

        Node<T> curNode = node;
        list.add(curNode);
        while (curNode.hasNext()) {
            curNode = curNode.next;
            list.add(curNode);
        }

        return list;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public boolean hasNext() {
        return next!=null;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

}
