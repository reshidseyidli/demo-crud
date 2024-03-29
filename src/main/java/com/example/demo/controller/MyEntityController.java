package com.example.demo.controller;

import com.example.demo.dto.request.MyEntityRequestDto;
import com.example.demo.dto.response.MyEntityResponseDto;
import com.example.demo.model.MyEntity;
import com.example.demo.service.MyEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myEntity")
public class MyEntityController {

    private final MyEntityService service;

    @GetMapping()
    public ResponseEntity<List<MyEntity>> getAll() {
        List<MyEntity> entityList = service.findAll();
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyEntity> getById(@PathVariable Long id) {
        MyEntity entity = service.findById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MyEntity> save(@RequestBody MyEntityRequestDto myEntityRequestDto) {
        service.save(myEntityRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MyEntity myEntity) {
        service.update(id, myEntity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/valyuta")
    public ResponseEntity<MyEntityResponseDto> getValyuta(@RequestBody MyEntityRequestDto request) throws IOException {
        MyEntityResponseDto response = service.getValyuta(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
