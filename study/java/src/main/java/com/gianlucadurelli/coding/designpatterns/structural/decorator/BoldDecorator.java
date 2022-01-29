package com.gianlucadurelli.coding.designpatterns.structural.decorator;

public class BoldDecorator extends AbstractTextDecorator {

  public BoldDecorator() {
  }

  public BoldDecorator(
      ITextDecorator wrappee) {
    super(wrappee);
  }

  @Override
  public String decorate(String text) {
    String inner = super.decorate(text);
    return "<b>" + inner + "</b>";
  }
}
