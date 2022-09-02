package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyEntityRequestDto {

    private String code;
    private String date;
    private BigDecimal conversationAmount;
}
