package com.gianlucadurelli.crackingcodeinterview.fifthedition.stackqueue;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AnimalShelterTest {

    // TODO: Rename test with exercise name
    @Test
    public void shelterOperation() {
        AnimalShelter<Animal> shelter = new AnimalShelter<>();

        shelter.enqueue(Animal.Cat("c0"));
        shelter.enqueue(Animal.Cat("c1"));
        shelter.enqueue(Animal.Dog("d0"));
        shelter.enqueue(Animal.Cat("c2"));
        shelter.enqueue(Animal.Dog("d1"));
        shelter.enqueue(Animal.Cat("c3"));
        shelter.enqueue(Animal.Dog("d2"));
        shelter.enqueue(Animal.Dog("d3"));
        shelter.enqueue(Animal.Cat("c4"));

        Assertions.assertThat(shelter.dequeueAny().getName()).isEqualTo("c0");
        Assertions.assertThat(shelter.dequeueDog().getName()).isEqualTo("d0");
        Assertions.assertThat(shelter.dequeueCat().getName()).isEqualTo("c1");
        Assertions.assertThat(shelter.dequeueCat().getName()).isEqualTo("c2");
        Assertions.assertThat(shelter.dequeueCat().getName()).isEqualTo("c3");
        Assertions.assertThat(shelter.dequeueAny().getName()).isEqualTo("d1");
        Assertions.assertThat(shelter.dequeueCat().getName()).isEqualTo("c4");
        Assertions.assertThat(shelter.dequeueDog().getName()).isEqualTo("d2");
        Assertions.assertThat(shelter.dequeueAny().getName()).isEqualTo("d3");
        Assertions.assertThat(shelter.dequeueDog()).isNull();
        Assertions.assertThat(shelter.dequeueCat()).isNull();
        Assertions.assertThat(shelter.dequeueAny()).isNull();
    }

}