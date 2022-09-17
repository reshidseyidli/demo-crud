package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyEntityRequestDto {

    public static int count = 0;

    public MyEntityRequestDto() {
        count++;
    }

    private int currency;
    private String code;
    private BigDecimal rate;
    private String date;
    private BigDecimal conversationAmount;
}
