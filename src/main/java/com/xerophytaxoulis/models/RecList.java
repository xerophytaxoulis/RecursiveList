package com.xerophytaxoulis.models;

sealed interface RecList<T> {
    record Nil<T>() implements  RecList<T> {}
    record Cons<T> (T value, RecList<T> tail) implements RecList<T> {}

    static<T> T head(RecList<T> targetList) {
        return switch (targetList) {
            case Nil<T> () -> throw new RuntimeException("head of empty list");
            case Cons<T> (T head, RecList<T> ignored) -> head;
        };
    }

    static<T> RecList<T> tail(RecList<T> list) {
        return switch (list) {
            case Nil<T> () -> new Nil<>();
            case Cons<T> (T ignored, RecList<T> tail) -> tail;
        };
    }
}
