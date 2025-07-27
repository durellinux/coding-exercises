package com.gianlucadurelli.coding.solid.liskov;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RectangleTest {

  @Test
  @Disabled
  public void computeArea() {
    // Given a rectangle of edges a and b
    Rectangle r = new Rectangle(1, 1);
    Rectangle s = new Square(1);

    // Then area is a*b
    assertArea(r);
    assertArea(s);
  }

  private void assertArea(Rectangle r) {
    r.setA(2);
    r.setB(4);
    int area = r.getArea();

    Assertions.assertThat(area).isEqualTo(8);
  }


}
