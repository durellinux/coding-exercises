package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.stackqueue;

import java.util.*;

public class AnimalShelter<T extends Animal> {
    private List<T> animals;

    public AnimalShelter() {
        animals = new LinkedList<>();
    }

    public void enqueue(T animal) {
        animals.add(animal);
    }

    public T dequeueAny() {
        if (animals.isEmpty()) {
            return null;
        }

        T animal = animals.get(0);
        animals.remove(0);
        return animal;
    }

    public T dequeueDog() {
        ListIterator<T> iterator = animals.listIterator();
        while(iterator.hasNext()) {
            T tmp = iterator.next();
            if (tmp.isDog()) {
                T animal = tmp;
                iterator.remove();
                return animal;
            }
        }

        return null;
    }

    public T dequeueCat() {
        ListIterator<T> iterator = animals.listIterator();
        while(iterator.hasNext()) {
            T tmp = iterator.next();
            if (!tmp.isDog()) {
                T animal = tmp;
                iterator.remove();
                return animal;
            }
        }

        return null;
    }
}
