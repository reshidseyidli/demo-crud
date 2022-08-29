package com.example.demo.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Valute {

    private Double value;
    private Integer nominal;
    private String name;

    @XmlElement(name = "Value")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @XmlElement(name = "Nominal")
    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
