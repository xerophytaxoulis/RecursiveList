package com.xerophytaxoulis.models;

public record Tuple<T>(RecList<T> firstElement, RecList<T> secondElement) { }
