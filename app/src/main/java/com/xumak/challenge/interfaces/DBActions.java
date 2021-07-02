package com.xumak.challenge.interfaces;

import java.util.List;
import java.util.Map;

public interface DBActions<T> {
    void add(T item);

    void add(Iterable<T> items);

    void update(T item);

    List<T> query(Map<String, String> parameters);
}
