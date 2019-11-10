package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.stackqueue;

public class Animal {
    private String name;
    private boolean isDog;

    private Animal(String name, boolean isDog) {
        this.name = name;
        this.isDog = isDog;
    }

    public static Animal Dog(String name) {
        return new Animal(name, true);
    }

    public static Animal Cat(String name) {
        return new Animal(name, false);
    }

    public String getName() {
        return name;
    }

    public boolean isDog() {
        return isDog;
    }
}
