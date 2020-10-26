package com.gianlucadurelli.coding.designpatterns.structural.decorator;


import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Decorator {


  @Test
  public void decoratorExample() {
    PlainText plainText = new PlainText();
    BoldDecorator boldDecorator = new BoldDecorator(plainText);
    ItalicDecorator italicDecorator = new ItalicDecorator(boldDecorator);
    PlainText plainText2 = new PlainText(italicDecorator);

    String result = plainText2.decorate("example");

    Assertions.assertThat(result).isEqualTo("<i><b>example</b></i>");
  }
}
