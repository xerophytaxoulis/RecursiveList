package com.xerophytaxoulis.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecListToolsTest {

    private RecList<Integer> givenList;

    @BeforeEach
    void setUp() {
        givenList = new RecList.Cons<>(1,
                new RecList.Cons<>(2,
                        new RecList.Nil<>()));
    }

    @Test
    void headOfNoneEmptyList() {
        Integer result = RecList.head(givenList);
        assertEquals(1, result);
    }

   @Test
   void headOfEmptyList() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> RecList.head(new RecList.Nil<>()));
        assertEquals("head of empty list", thrown.getMessage());
   }

   @Test
    void tailOfNoneEmptyList() {
       RecList<Integer> expected = new RecList.Cons<>(2,
               new RecList.Nil<>());
       RecList<Integer> result = RecList.tail(givenList);

       assertEquals(expected, result);
   }

   @Test
    void tailOfEmptyList() {
        RecList<Integer> emptyList = new RecList.Nil<>();
        assertEquals(emptyList, RecList.tail(emptyList));
   }
}