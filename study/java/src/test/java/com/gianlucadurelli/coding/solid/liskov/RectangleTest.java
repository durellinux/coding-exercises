package com.gianlucadurelli.coding.solid.liskov;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangleTest {

  @Test
  public void shouldWorkWithRectangles() {
    // Given a rectangle of edges a and b
    Rectangle r = new Rectangle(1, 1);
    setEdges(r, 2, 4);

    // Then the area is a * b
    Assertions.assertThat(r.getArea()).isEqualTo(8);
  }

  @Test
  public void shouldViolateLiskovSubstitutionPrincileWithSquare() {
    // Given a square of edges a and b
    Square s = new Square(1);
    setEdges(s, 2, 4);

    // Then the area is a * b
    Assertions.assertThat(s.getArea()).isNotEqualTo(8);
  }

  private void setEdges(Rectangle r, int a, int b) {
    r.setA(a);
    r.setB(b);
  }

}
