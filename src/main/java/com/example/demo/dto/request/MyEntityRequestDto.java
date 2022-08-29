package com.example.demo.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MyEntityRequestDto {

    private String mezenne;
    private LocalDate tarix;
    private double deyer;
}
