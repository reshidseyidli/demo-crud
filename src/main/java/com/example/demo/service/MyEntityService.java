package com.example.demo.service;

import com.example.demo.model.MyEntity;

import java.util.List;

public interface MyEntityService {

    List<MyEntity> findAll();

    MyEntity findById(Long id);

    MyEntity save(MyEntity requestEntity);

    void update(Long id, MyEntity requestEntity);

    void delete(Long id);
}
