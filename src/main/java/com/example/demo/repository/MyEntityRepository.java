package com.example.demo.repository;

import com.example.demo.model.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {

    MyEntity findByTarixAndValyuta(LocalDate tarix, String valyuta);
}
