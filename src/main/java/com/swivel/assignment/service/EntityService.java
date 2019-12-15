package com.swivel.assignment.service;

import java.util.List;

public interface EntityService<T> {
    public T findEntity(String fileName, String searchTerm, String searchValue);

    public List<T> getEntityList(String fileName);
}
