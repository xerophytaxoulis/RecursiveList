package com.xerophytaxoulis.models;

import java.util.*;

public class RecursiveList<T> extends AbstractSequentialList<T> {

    private RecList<T> list;

    public RecursiveList() {
        this.list = new RecList.Nil<>();
    }

    public RecursiveList(RecList<T> list) {
        this.list = list;
    }

    @SafeVarargs
    public static<T> RecursiveList<T> of(T... elements) {
       RecList<T> res = new RecList.Nil<T>();
       for (T elem : elements) {
           res = RecList.add(res, elem);
       }
       return new RecursiveList<>(res);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public int size() {
        return RecList.length(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecursiveList<?> that = (RecursiveList<?>) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    private class ListItr implements ListIterator<T> {
        private int cursor;
        private RecList<T> nextSlice;
        private RecList<T> prevSlice;

        ListItr(int index) {
            this.cursor = index;
            this.nextSlice = RecList.sublist(
                    RecursiveList.this.list, index, RecursiveList.this.size());
            this.prevSlice = RecList.reverse(
                    RecList.sublist(RecursiveList.this.list, 0, index));
        }

        @Override
        public boolean hasNext() {
            return cursor < RecursiveList.this.size();
        }

        @Override
        public T next() {
            cursor++;
            T head = RecList.head(nextSlice);
            prevSlice = new RecList.Cons<>(head, prevSlice);
            nextSlice = RecList.tail(nextSlice);
            return head;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            cursor--;
            T head = RecList.head(prevSlice);
            prevSlice = RecList.tail(prevSlice);
            nextSlice = new RecList.Cons<>(head, nextSlice);
            return head;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            cursor--;
            RecList.removeAt(RecursiveList.this.list, cursor);
            prevSlice = RecList.tail(prevSlice);
        }

        @Override
        public void set(T t) {
            RecList.setAt(RecursiveList.this.list, t, cursor - 1);
            prevSlice = new RecList.Cons<>(t, RecList.tail(prevSlice));
        }

        @Override
        public void add(T t) {
            RecList.addAt(RecursiveList.this.list, t, cursor - 1);
            prevSlice = new RecList.Cons<>(t, prevSlice);
            cursor++;
        }
    }

    @Override
    public String toString() {
        String asString = toStr(list);
        if (!asString.isEmpty()) {
            asString = asString.substring(0, asString.length() - 1);
        }
        return "[" + asString + "]";
    }

    private static <T> String toStr(RecList<T> targetList) {
        return  switch (targetList) {
            case RecList.Nil ignored -> "";
            case RecList.Cons<T>(T head,RecList<T> tail)
                    -> head.toString() + "," + toStr(tail);
        };
    }
}
