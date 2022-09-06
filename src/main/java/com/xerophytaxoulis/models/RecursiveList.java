package com.xerophytaxoulis.models;

import java.util.*;

public class RecursiveList<T> extends AbstractSequentialList<T> {

    private RecList<T> list;

    public RecursiveList() {
        this.list = new RecList.Nil<T>();
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

        ListItr(int index) {
            this.cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < RecursiveList.this.size() - 1;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            return null;
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

        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }
}
