package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.linkedlist;

public class Ex2_2<T> {

    public T getKFromEnd(Node<T> node, int k) {
        if (node == null) {
            return null;
        }

        Node<T> it1 = node;
        Node<T> it2 = node;

        int counter = k;

        while (it2.hasNext()) {
            if (counter > 0) {
                counter = counter - 1;
                it2 = it2.getNext();
            } else {
                it1 = it1.getNext();
                it2 = it2.getNext();
            }
        }

        if (counter > 0) {
            return null;
        } else {
            return it1.getElement();
        }
    }

}
