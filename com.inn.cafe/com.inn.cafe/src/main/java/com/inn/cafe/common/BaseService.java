package com.inn.cafe.common;
import java.util.List;

public interface BaseService<T, ID> {
    T findById(ID id);

    List<T> findAll();

    T save(T entity);

    T update(ID id, T entity);

    T delete(ID id);


}
