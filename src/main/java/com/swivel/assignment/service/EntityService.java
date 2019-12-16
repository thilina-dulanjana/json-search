package com.swivel.assignment.service;

import java.util.List;

public interface EntityService<T> {
    List<T> findEntity(String fileName, String searchTerm, String searchValue);

    List<T> getEntityList(String fileName);
}
