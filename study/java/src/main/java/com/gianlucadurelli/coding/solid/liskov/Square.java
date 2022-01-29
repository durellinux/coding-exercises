package com.gianlucadurelli.coding.solid.liskov;

public class Square extends Rectangle {

  public Square(int l) {
    super(l, l);
  }

  @Override
  public void setA(int a) {
    this.a = a;
    this. b = a;
  }

  @Override
  public void setB(int b) {
    this.b = b;
    this.a = b;
  }
}
