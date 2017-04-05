package com.expertsoft.core.model.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Dao <T> {
    @NotNull T get(Long key);
    void update(@NotNull T item);
    void create(@NotNull T item);
    @NotNull List<T> findAll();
}
