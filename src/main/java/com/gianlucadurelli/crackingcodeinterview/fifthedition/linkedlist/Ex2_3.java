package com.gianlucadurelli.crackingcodeinterview.fifthedition.linkedlist;

public class Ex2_3<T> {

    public void removeElement(Node<T> element) {
        Node<T> next = element.getNext();

        element.setElement(next.getElement());
        element.setNext(next.getNext());
    }
}
