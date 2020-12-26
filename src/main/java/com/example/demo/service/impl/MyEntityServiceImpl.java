package com.example.demo.service.impl;

import com.example.demo.model.MyEntity;
import com.example.demo.repository.MyEntityRepository;
import com.example.demo.service.MyEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyEntityServiceImpl implements MyEntityService {

    private static final Logger logger = LoggerFactory.getLogger(MyEntityServiceImpl.class);

    private final MyEntityRepository repository;

    public MyEntityServiceImpl(MyEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MyEntity> findAll() {
        logger.info("getting all...");
        return repository.findAll();
    }

    @Override
    public MyEntity findById(Long id) {
        logger.info("getting by id...");
        Optional<MyEntity> entity = repository.findById(id);
        return entity.orElse(null);
    }

    @Override
    public MyEntity save(MyEntity requestEntity) {
        logger.info("saving...");
        return repository.save(requestEntity);
    }

    @Override
    public void update(Long id, MyEntity requestEntity) {
        logger.info("updating...");
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
        logger.info("deleting...");
        Optional<MyEntity> entity = repository.findById(id);
        entity.ifPresent(repository::delete);
    }
}
