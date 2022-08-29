package com.example.demo.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Valute {

    private BigDecimal value;
    private String code;
    private String name;

    @XmlElement(name = "Value")
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
