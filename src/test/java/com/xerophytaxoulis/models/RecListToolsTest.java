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
       RecList<Integer> resultWithRemovedElem = RecList.removeAll(givenList, 1);
       // find no element to remove
       RecList<Integer> resultWithoutRemovedElem =  RecList.removeAll(givenList, 3);

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

   @Test
    void validSublistOfEmptyList() {
        RecList<Integer> given = new RecList.Nil<>();
        RecList<Integer> resultOfEmptySublistOfEmptyList
                = RecList.sublist(given, 0, 0);
        assertEquals(given, resultOfEmptySublistOfEmptyList);
   }

   @Test
    void validSublistOfNoneEmptyList() {
        RecList<Integer> given = RecList.add(givenList, 3);

        RecList<Integer> expectedFirstTwo = givenList;
        RecList<Integer> resultFirstTwo = RecList.sublist(given, 0, 2);
        assertEquals(expectedFirstTwo, resultFirstTwo);

        RecList<Integer> expectedMiddleOne = new RecList.Cons<>(2,
                new RecList.Nil<>());
        RecList<Integer> resultMiddleOne = RecList.sublist(given, 1, 2);
        assertEquals(expectedMiddleOne, resultMiddleOne);

        RecList<Integer> expectedLastTwo = RecList.removeAll(given, 1);
        RecList<Integer> resultLastTwo = RecList.sublist(given, 1, 3);
        assertEquals(expectedLastTwo, resultLastTwo);
   }

    @Test
    void invalidSublistOfList() {
        IndexOutOfBoundsException thrownInvalidLowerIndex
                = assertThrows(IndexOutOfBoundsException.class, ()
                -> RecList.sublist(givenList, -1, 1));
        assertEquals("fromIndex -1", thrownInvalidLowerIndex.getMessage());

        IndexOutOfBoundsException thrownInvalidUpperIndex
                = assertThrows(IndexOutOfBoundsException.class, ()
                -> RecList.sublist(givenList, 0, 3));
        assertEquals("toIndex 3", thrownInvalidUpperIndex.getMessage());

        IllegalArgumentException thrownInvalidIndexCombination
                = assertThrows(IllegalArgumentException.class, ()
                -> RecList.sublist(givenList, 2, 1));
        assertEquals("fromIndex(2) > toIndex(1)",
                thrownInvalidIndexCombination.getMessage());
    }

    @Test
    void removeElementFromNoneEmptyListContainingTheElement() {
       RecList<Integer> given = RecList.add(givenList, 1);
       RecList<Integer> expectedAfterRemovingOne = new RecList.Cons<>(2,
               new RecList.Cons<>(1,
                       new RecList.Nil<>()));
       RecList<Integer> expectedAfterRemovingTwo = new RecList.Cons<>(1,
               new RecList.Cons<>(1,
                       new RecList.Nil<>()));

       RecList<Integer> resultAfterRemovingOne = RecList.remove(given, 1);
       RecList<Integer> resultAfterRemovingTwo = RecList.remove(given, 2);

       assertEquals(expectedAfterRemovingOne, resultAfterRemovingOne);
       assertEquals(expectedAfterRemovingTwo, resultAfterRemovingTwo);
    }

    @Test
    void removeElementFromNoneEmptyListNotContainingTheElement() {
        RecList<Integer> expected = new RecList.Cons<>(1,
                new RecList.Cons<>(2,
                        new RecList.Nil<>()));
        RecList<Integer> result = RecList.remove(givenList, 3);
        assertEquals(expected, result);
    }

    @Test
    void removeElementFromEmptyList() {
        RecList<Integer> expected = new RecList.Nil<>();
        RecList<Integer> result = RecList.remove(new RecList.Nil<Integer>(), 1);
        assertEquals(expected, result);
    }

    @Test
    void removeAtFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> RecList.removeAt(new RecList.Nil<>(), 3));
    }

    @Test
    void removeAtFromNoneEmptyList() {
        RecList<Integer> expectedRemoveFirst = new RecList.Cons<>(
                2, new RecList.Nil<>());
        RecList<Integer> expectedRemoveLast = new RecList.Cons<>(
                1, new RecList.Nil<>());

        assertEquals(expectedRemoveFirst, RecList.removeAt(givenList, 0));
        assertEquals(expectedRemoveLast, RecList.removeAt(givenList, 1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> RecList.removeAt(givenList, 3));
    }

    @Test
    void setAtEmptyList() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> RecList.removeAt(new RecList.Nil<>(), 3));
    }

    @Test
    void setAtNoneEmptyList() {
        RecList<Integer> expected = new RecList.Cons<>(1,
                                        new RecList.Cons<>(3,
                                                new RecList.Nil<>()));
        RecList<Integer> result = RecList.setAt(givenList, 3, 1);
        assertEquals(expected, result);
    }

    @Test
    void addAtEmptyList() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> RecList.removeAt(new RecList.Nil<>(), 3));
    }

    @Test
    void noneEqualListEquality() {
        RecList<Integer> notEqualToGivenList = new RecList.Cons<>(1,
                        new RecList.Nil<>());
        assertFalse(RecList.equals(givenList,notEqualToGivenList));
        assertFalse(RecList.equals(givenList, RecList.setAt(givenList, 3, 1)));
    }

    @Test
    void equalListEquality() {

        RecList<Integer> equalToGivenList = new RecList.Cons<>(1,
                new RecList.Cons<>(2,
                        new RecList.Nil<>()));

        assertTrue(RecList.equals(givenList, equalToGivenList));
        assertTrue(RecList.equals(new RecList.Nil<>(), new RecList.Nil<>()));
    }
}