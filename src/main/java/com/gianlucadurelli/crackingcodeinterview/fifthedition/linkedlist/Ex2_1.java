package com.gianlucadurelli.crackingcodeinterview.fifthedition.linkedlist;


import java.util.*;

public class Ex2_1<T> {

    public void removeDuplicatesWithSet(LinkedList<T> list) {

        if (list.size() < 2) {
            return;
        }

        Set<T> support = new HashSet<>();

        ListIterator<T> iterator = list.listIterator();
        support.add(list.get(0));
        while(iterator.hasNext()) {
            T data = iterator.next();
            if (support.contains(data)) {
                iterator.remove();
            } else {
                support.add(data);
            }
        }
    }

    public void removeInPlace(Node<T> list) {
        if (list == null) {
            return;
        }

        Node<T> it1 = list;
        while(it1 != null && it1.hasNext()) {
            T curValue = it1.getElement();
            Node<T> it2 = it1;
            while(it2.hasNext()) {
                Node<T> next = it2.getNext();
                T otherValue = next.getElement();
                if (otherValue.equals(curValue)) {
                    it2.setNext(next.getNext());
                } else {
                    it2 = next;
                }
            }
            it1 = it1.getNext();
        }
    }
}
