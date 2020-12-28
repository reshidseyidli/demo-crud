package com.example.demo.controller;

import com.example.demo.dto.request.MyEntityRequestDto;
import com.example.demo.dto.response.MyEntityResponseDto;
import com.example.demo.model.MyEntity;
import com.example.demo.service.MyEntityService;
import com.example.demo.util.MyEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myEntity")
public class MyEntityController {

    private final MyEntityService service;
    private static final MyEntityMapper MAPPER = MyEntityMapper.INSTANCE;

    @GetMapping("/myEntity/all")
    public ResponseEntity<List<MyEntityResponseDto>> getAll() {
        List<MyEntity> entityList = service.findAll();
        List<MyEntityResponseDto> myEntityResponseDtoList = MAPPER.toMyEntityResponseDtoList(entityList);
        return new ResponseEntity<>(myEntityResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/myEntity/{id}")
    public ResponseEntity<MyEntityResponseDto> getById(@PathVariable Long id) {
        MyEntity entity = service.findById(id);
        MyEntityResponseDto entityResponseDto = MAPPER.toMyEntityResponseDto(entity);
        return new ResponseEntity<>(entityResponseDto, HttpStatus.OK);
    }

    @PostMapping("/myEntity")
    public ResponseEntity<MyEntityResponseDto> save(@RequestBody MyEntityRequestDto myEntityRequestDto) {
        MyEntity entity = MAPPER.toMyEntity(myEntityRequestDto);
        entity = service.save(entity);
        MyEntityResponseDto entityResponseDto = MAPPER.toMyEntityResponseDto(entity);
        return new ResponseEntity<>(entityResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/myEntity")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MyEntityRequestDto myEntityRequestDto) {
        MyEntity entity = MAPPER.toMyEntity(myEntityRequestDto);
        service.update(id, entity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/myEntity")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
