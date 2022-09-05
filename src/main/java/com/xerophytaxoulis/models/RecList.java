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

    static<T> RecList<T> tail(RecList<T> targetList) {
        return switch (targetList) {
            case Nil<T> () -> new Nil<>();
            case Cons<T> (T ignored, RecList<T> tail) -> tail;
        };
    }

    static<T> RecList<T> reverse(RecList<T> targetList) {
       return rev(targetList, new Nil<T>());
    }

    private static<T> RecList<T> rev(RecList<T> targetList, RecList<T> auxiliaryList) {
       return switch (targetList) {
           case Nil ignored -> auxiliaryList;
           case Cons<T> (T head, RecList<T> tail)
               -> rev(tail, new Cons<>(head, auxiliaryList));
       };
    }

    static<T> RecList<T> append(RecList<T> targetList, RecList<T> listToAppend) {
        return switch (targetList) {
            case Nil ignored -> listToAppend;
            case Cons<T> (T head, RecList<T> tail)
                    -> new Cons<>(head, append(tail, listToAppend));
        };
    }

    static<T> RecList<T> add(RecList<T> targetList, T toAdd) {
        return append(targetList, new Cons<>(toAdd, new Nil<T>()));
    }

    static<T> RecList<T> remove(RecList<T> targetList, T toRemove) {
        return switch (targetList) {
            case Nil<T>() -> new Nil<T>();
            case Cons<T> (T head, RecList<T> tail) ->
                head.equals(toRemove)
                        ? remove(tail, toRemove)
                        : new Cons<T>(head, remove(tail, toRemove));
        };
    }

    static<T> Integer length(RecList<T> targetList) {
        Integer length = 0;
        return switch (targetList) {
            case Nil<T> () -> length;
            case Cons<T> (T ignored, RecList<T> tail) ->
                1 + length(tail);
        };
    }

    static<T> Boolean contains(RecList<T> targetList, T elem) {
        return switch (targetList) {
            case Nil ignored -> false;
            case Cons<T> (T head, RecList<T> tail) ->
                head.equals(elem) || contains(tail, elem);
        };
    }
}
