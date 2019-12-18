package com.swivel.assignment.service;

import com.swivel.assignment.exception.UnsupportedSearchTermException;

import java.util.List;

public interface EntityService<T> {
    List<T> findEntity(String fileName, String searchTerm, String searchValue) throws UnsupportedSearchTermException;

    List<T> getEntityList(String fileName);
}
