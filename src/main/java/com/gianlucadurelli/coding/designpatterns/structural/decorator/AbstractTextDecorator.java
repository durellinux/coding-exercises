package com.gianlucadurelli.coding.designpatterns.structural.decorator;

import java.util.Objects;

public abstract class AbstractTextDecorator implements ITextDecorator{

  private ITextDecorator wrappee;

  public AbstractTextDecorator() { }

  public AbstractTextDecorator(
      ITextDecorator wrappee) {
    this.wrappee = wrappee;
  }

  @Override
  public String decorate(String text) {
    if (Objects.nonNull(wrappee)) {
      return wrappee.decorate(text);
    }

    return text;
  }
}
