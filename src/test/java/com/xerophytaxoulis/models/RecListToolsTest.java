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

   @Test
    void reverseEmptyList() {
       RecList<Integer> emptyList = new RecList.Nil<>();
       assertEquals(emptyList, RecList.reverse(emptyList));
   }

    @Test
    void reverseNoneEmptyList() {
        RecList<Integer> expected = new RecList.Cons<>(2,
                new RecList.Cons<>(1,
                        new RecList.Nil<>()));

        RecList<Integer> result = RecList.reverse(givenList);

        assertEquals(expected, result);
   }

   @Test
    void appendList() {
       RecList<Integer> expected =
               new RecList.Cons<>(1,
                   new RecList.Cons<>(2,
                           new RecList.Cons<>(1,
                                   new RecList.Cons<>(2,
                                           new RecList.Nil<>()))));

       RecList<Integer> result = RecList.append(givenList, givenList);

       assertEquals(expected, result);
       assertEquals(givenList, RecList.append(givenList, new RecList.Nil<>()));
   }

    @Test
    void addElement() {
        RecList<Integer> expected =
                new RecList.Cons<>(1,
                        new RecList.Cons<>(2,
                                new RecList.Cons<>(3,
                                            new RecList.Nil<>())));

        RecList<Integer> result = RecList.add(givenList, 3);

        assertEquals(expected, result);
   }

   @Test
    void removeElementFromList() {
       RecList<Integer> expected = new RecList.Cons<>(2, new RecList.Nil<>());
       // find element to remove
       RecList<Integer> resultWithRemovedElem = RecList.remove(givenList, 1);
       // find no element to remove
       RecList<Integer> resultWithoutRemovedElem =  RecList.remove(givenList, 3);

       assertEquals(expected, resultWithRemovedElem);
       assertEquals(givenList, resultWithoutRemovedElem);
   }

   @Test
    void lengthOfList() {
       assertEquals(2, RecList.length(givenList));
       assertEquals(0, RecList.length(new RecList.Nil<Integer>()));
   }

   @Test
    void listContainsElement() {
        assertTrue(RecList.contains(givenList, 2));
        assertFalse(RecList.contains(givenList, 3));
        // empty list doesn't contain anything
        assertFalse(RecList.contains(new RecList.Nil<Integer>(), 1));
   }
}