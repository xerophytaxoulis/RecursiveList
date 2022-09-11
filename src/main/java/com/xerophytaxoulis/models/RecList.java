package com.xerophytaxoulis.models;

public sealed interface RecList<T> {
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

    static<T> RecList<T> removeAll(RecList<T> targetList, T toRemove) {
        return switch (targetList) {
            case Nil<T>() -> new Nil<T>();
            case Cons<T> (T head, RecList<T> tail) ->
                head.equals(toRemove)
                        ? removeAll(tail, toRemove)
                        : new Cons<T>(head, removeAll(tail, toRemove));
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
                head.equals(elem) ? true : contains(tail, elem);
        };
    }

    static<T> RecList<T> sublist(RecList<T> targetList, int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, length(targetList));
        if (fromIndex == toIndex) {
            return new Nil<>();
        } else {
            return subl(targetList, fromIndex, toIndex, 0);
        }
    }

    private static<T> RecList<T> subl(RecList<T> list, int fromIdx, int toIdx, int currIdx) {
        return switch (list) {
            case Nil ignored -> new Nil<>();
            case Cons<T> (T head, RecList<T> tail) -> {
                if (toIdx <= currIdx) {
                    yield new Nil<>();
                } else if (fromIdx <= currIdx && currIdx < toIdx) {
                    yield new Cons<>(head, subl(tail, fromIdx, toIdx, ++currIdx));
                } else {
                    yield subl(tail, fromIdx, toIdx, ++currIdx);
                }
            }
        };
    }

    private static void subListRangeCheck(int fromIndex, int toIndex, int length) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex " + fromIndex);
        }
        if (toIndex > length) {
            throw new IndexOutOfBoundsException("toIndex " + toIndex);
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
        }
    }

    static <T> RecList<T> remove(RecList<T> targetList, T toRemove) {
        return switch (targetList) {
            case Nil ignored -> targetList;
            case Cons<T> (T head, RecList<T> tail)
                -> head.equals(toRemove)
                    ? tail
                    : new Cons<T>(head, remove(tail, toRemove));
        };
    }

    static <T> RecList<T> removeAt(RecList<T> targetList, int index) {
        return remAtHelper(targetList, index, 0);
    }

    private static <T> RecList<T> remAtHelper(RecList<T> targetList, int idx, int runningIdx) {
        return switch (targetList) {
            case Nil ignored -> throw new IndexOutOfBoundsException();
            case Cons<T> (T head, RecList<T> tail) ->
               idx == runningIdx
                       ? tail
                       : new Cons<>(head, remAtHelper(tail, idx, ++runningIdx));
        };
    }

    static <T> RecList<T> setAt(RecList<T> targetList, T elem, int index) {
       return setAtHelper(targetList, elem, index, 0);
    }

    private static <T> RecList<T> setAtHelper(RecList<T> targetList, T elem, int idx, int runningIdx) {
        return switch (targetList) {
            case Nil ignored -> throw new IndexOutOfBoundsException();
            case Cons<T> (T head, RecList<T> tail) ->
                    idx == runningIdx
                            ? new Cons<>(elem, tail)
                            : new Cons<>(head, setAtHelper(tail, elem, idx, ++runningIdx));
        };
    }

    static <T> RecList<T> addAt(RecList<T> targetList, T elem, int index) {
       return addAtHelper(targetList, elem, index, 0);
    }


    private static <T> RecList<T> addAtHelper(RecList<T> targetList, T elem, int idx, int runningIdx) {
        return switch (targetList) {
            case Nil ignored -> throw new IndexOutOfBoundsException();
            case Cons<T> (T head, RecList<T> tail) ->
                    idx == runningIdx
                            ? new Cons<>(elem, targetList)
                            : new Cons<>(head, addAtHelper(tail, elem, idx, ++runningIdx));
        };
    }

    static <T> boolean equals(RecList<T> list1, RecList<T> list2) {
        Tuple<T> tuple = new Tuple<>(list1, list2);
        return switch (tuple) {
            case Tuple<T> (Cons<T>(T head1, RecList<T> tail1), Cons<T>(T head2, RecList<T> tail2))
                -> head1.equals(head2) && equals(tail1, tail2);
            case Tuple<T> (Nil<T>(), Nil<T>()) -> true;
            case default -> false;
        };
    }
}
