package com.example.demo.controller;

import com.example.demo.model.MyEntity;
import com.example.demo.service.MyEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/myEntity")
public class MyEntityController {

    private final MyEntityService service;

    public MyEntityController(MyEntityService service) {
        this.service = service;
    }

    @GetMapping("/myEntity/all")
    public ResponseEntity<List<MyEntity>> getAll() {
        List<MyEntity> entityList = service.findAll();
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/myEntity/{id}")
    public ResponseEntity<MyEntity> getById(@PathVariable Long id) {
        MyEntity entity = service.findById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping("/myEntity")
    public ResponseEntity<MyEntity> save(@RequestBody MyEntity myEntity) {
        MyEntity entity = service.save(myEntity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/myEntity")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MyEntity myEntity) {
        service.update(id, myEntity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/myEntity")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
