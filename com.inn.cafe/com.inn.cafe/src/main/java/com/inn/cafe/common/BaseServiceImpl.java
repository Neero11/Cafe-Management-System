package com.inn.cafe.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseServiceImpl<T, ID> implements BaseService<T,ID> {

    private final JpaRepository<T, ID> repository;

    public BaseServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T findById(ID id) {
        Optional<T> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T entity) {



        return repository.save(entity);
    }



    @Override
    public T update(ID id, T entity) {
        if (repository.existsById(id)) {
            entity = repository.save(entity);
            return entity;
        }
        return null;
    }

    @Override
    public T delete(ID id) {
       T entity = repository.findById(id).orElseThrow();
       repository.deleteById(id);
       return entity;
    }
}
