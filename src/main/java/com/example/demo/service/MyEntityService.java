package com.example.demo.service;

import com.example.demo.dto.request.MyEntityRequestDto;
import com.example.demo.dto.response.MyEntityResponseDto;
import com.example.demo.model.MyEntity;

import java.util.List;

public interface MyEntityService {

    List<MyEntity> findAll();

    MyEntity findById(Long id);

    MyEntity save(MyEntity requestEntity);

    void update(Long id, MyEntity requestEntity);

    void delete(Long id);

    MyEntityResponseDto getValyuta(MyEntityRequestDto request);
}
