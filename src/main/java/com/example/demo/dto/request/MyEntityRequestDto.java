package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MyEntityRequestDto {

    private String valyuta;
    private LocalDate tarix;
    private BigDecimal mebleg;
}
