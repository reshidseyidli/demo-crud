package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "my_entity", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String currency;
    private String code;
    private BigDecimal rate;
    private LocalDate date;
}
