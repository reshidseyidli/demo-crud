package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MyEntityRequestDto {

    private String currency;
    private LocalDate date;
    private BigDecimal conversationAmount;
}
