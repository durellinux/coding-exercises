package com.gianlucadurelli.coding.designpatterns.structural.decorator;

public class ItalicDecorator extends AbstractTextDecorator {

  public ItalicDecorator() {
  }

  public ItalicDecorator(
      ITextDecorator wrappee) {
    super(wrappee);
  }

  @Override
  public String decorate(String text) {
    String inner = super.decorate(text);
    return "<i>" + inner + "</i>";
  }
}
