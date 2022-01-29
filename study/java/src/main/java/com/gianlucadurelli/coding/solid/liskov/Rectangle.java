package com.gianlucadurelli.coding.solid.liskov;

public class Rectangle {
  protected int a;
  protected int b;

  public Rectangle(int a, int b) {
    this.a = a;
    this.b = b;
  }

  public void setA(int a) {
    this.a = a;
  }

  public void setB(int b) {
    this.b = b;
  }

  public int getArea() {
    return a * b;
  }
}
