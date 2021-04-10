package com.gianlucadurelli.coding.designpatterns.structural.decorator;

public class PlainText extends AbstractTextDecorator {

  public PlainText() { }

  public PlainText(
      ITextDecorator wrappee) {
    super(wrappee);
  }

}
