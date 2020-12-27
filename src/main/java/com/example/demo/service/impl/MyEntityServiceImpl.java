package com.example.demo.service.impl;

import com.example.demo.exception.MyEntityNotFound;
import com.example.demo.model.MyEntity;
import com.example.demo.repository.MyEntityRepository;
import com.example.demo.service.MyEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyEntityServiceImpl implements MyEntityService {

    private final MyEntityRepository repository;

    @Override
    public List<MyEntity> findAll() {
        log.info("getting all...");
        return repository.findAll();
    }

    @Override
    public MyEntity findById(Long id) {
        log.info("getting by id...");
        Optional<MyEntity> entity = repository.findById(id);
        return entity.orElseThrow(MyEntityNotFound::new);
    }

    @Override
    public MyEntity save(MyEntity requestEntity) {
        log.info("saving...");
        return repository.save(requestEntity);
    }

    @Override
    public void update(Long id, MyEntity requestEntity) {
        log.info("updating...");
        Optional<MyEntity> dbEntity = repository.findById(id);
        if (dbEntity.isPresent()) {
            MyEntity entity = dbEntity.get();
            entity.setId(requestEntity.getId());
            entity.setName(requestEntity.getName());
            entity.setSurname(requestEntity.getSurname());
            repository.save(requestEntity);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("deleting...");
        Optional<MyEntity> entity = repository.findById(id);
        entity.ifPresent(repository::delete);
    }
}
